package example.app.services;

import example.app.exceptions.DatabaseOperationException;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;


class FileDatabaseTest {
    static String database = "test-database";
    static String partition1 = "test-partition1";
    static String partition2 = "test-partition2";

    @BeforeAll
    static void createDatabasePathFileBackup() throws IOException {
        Path source = Paths.get("database-path.config");
        Path target = Paths.get("database-path.config.backup");

        try {
            Files.copy(source, target);
        }
        catch (NoSuchFileException e) {
            DatabaseConfig.createNew();
        }
    }

    @BeforeAll
    static  void createDatabaseDirectory() {
        File databaseDir = new File(database);
        if(!databaseDir.exists())
            databaseDir.mkdir();
    }

    @BeforeEach
    void createConfigEntries() throws IOException {
        DatabaseConfig.createNew();
        BufferedWriter writer = new BufferedWriter(new FileWriter("database-path.config"));

        writer.write("root = " + database + "\n");
        writer.write(partition1 + " = " + partition1 + "\n");
        writer.write(partition2 + " = " + partition2 + "\n");
        writer.flush();
        writer.close();
    }

    @BeforeEach
    @AfterEach
    void deleteDataBaseEntries() throws IOException {
        new ObjectOutputStream(new FileOutputStream(database + "/" + partition1))
                .writeObject(new LinkedList<String>());
        new ObjectOutputStream(new FileOutputStream(database + "/" + partition2))
                .writeObject(new LinkedList<String>());
    }

    @Test
    void testingGetFullPath() throws IOException {
        DatabaseConfig.createNew();
        BufferedWriter writer = new BufferedWriter(new FileWriter("database-path.config", true));
        FileDatabase<String> fileDatabase = new FileDatabase<>();

        assertNull(fileDatabase.getFullPath("root"));
        assertNull(fileDatabase.getFullPath("key1"));

        writer.write("root = rootValue\n");
        writer.write("key1=value1\n");
        writer.write("key2 = value2\n");
        writer.flush();
        writer.close();

        assertEquals("rootValue", fileDatabase.getFullPath("root"));
        assertEquals("rootValue/value1", fileDatabase.getFullPath("key1"));
        assertEquals("rootValue/value2", fileDatabase.getFullPath("key2"));
    }

    @Test
    void testingGetAll() throws IOException, DatabaseOperationException {
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(database + "/" + partition1));
        writer.writeObject(new LinkedList<>(Arrays.asList(1, 2, 8, 9)));
        writer.flush();
        writer.close();

        writer = new ObjectOutputStream(new FileOutputStream(database + "/" + partition2));
        writer.writeObject(new LinkedList<>(Arrays.asList(1, 2, 855)));
        writer.flush();
        writer.close();

        FileDatabase<Integer> fileDatabase = new FileDatabase<>();
        LinkedList<Integer> list = null;

        // getting all the values from partition1
        list = fileDatabase.getAll(partition1);
        assertEquals(4, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(8, list.get(2));
        assertEquals(9, list.get(3));

        // getting all the values from partition2
        list = fileDatabase.getAll(partition2);
        assertEquals(3, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(855, list.get(2));

        // testing for an invalid key
        assertThrows(DatabaseOperationException.class, () -> fileDatabase.getAll("invalid-key"));
    }

    @Test
    void testingAddEntry() throws DatabaseOperationException {
        FileDatabase<Integer> fileDatabase = new FileDatabase<>();

        new File(database + "/" + partition1).delete();
        new File(database + "/" + partition2).delete();

        assertDoesNotThrow(() -> fileDatabase.addEntry(partition1, 0, 70, 5, 85));
        assertDoesNotThrow(() -> fileDatabase.addEntry(partition2, 10, 6451));

        LinkedList<Integer> list = fileDatabase.getAll(partition1);
        assertEquals(4, list.size());
        assertEquals(0, list.get(0));
        assertEquals(70, list.get(1));
        assertEquals(5, list.get(2));
        assertEquals(85, list.get(3));

        list = fileDatabase.getAll(partition2);
        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(6451, list.get(1));
    }

    @Test
    void testingRemoveEntry() throws DatabaseOperationException {
        new File(database + "/" + partition1).delete();
        new File(database + "/" + partition2).delete();

        FileDatabase<String> fileDatabase = new FileDatabase<>();

        fileDatabase.addEntry(partition1, "value1", "value2", "value3", "value4");
        fileDatabase.addEntry(partition2, "value1", "value2", "value3", "value4", "value5", "value6", "value7");


        fileDatabase.removeEntry(partition1, "value1");
        assertEquals(3, fileDatabase.getAll(partition1).size());
        fileDatabase.removeEntry(partition1, "value1");
        assertEquals(3, fileDatabase.getAll(partition1).size());
        fileDatabase.removeEntry(partition1, "value2");
        assertEquals(2, fileDatabase.getAll(partition1).size());
        assertEquals("value3", fileDatabase.getAll(partition1).get(0));
        assertEquals("value4", fileDatabase.getAll(partition1).get(1));
        fileDatabase.removeEntry(partition1, "value3", "value4");
        assertEquals(0, fileDatabase.getAll(partition1).size());

        assertEquals(7, fileDatabase.getAll(partition2).size());
        fileDatabase.removeEntry(partition2, "value4");
        assertEquals(6, fileDatabase.getAll(partition2).size());
        fileDatabase.removeEntry(partition2, "value6");
        assertEquals(5, fileDatabase.getAll(partition2).size());
        fileDatabase.removeEntry(partition2, "value6");
        assertEquals(5, fileDatabase.getAll(partition2).size());
        assertEquals("value5", fileDatabase.getAll(partition2).get(3));
        assertEquals("value7", fileDatabase.getAll(partition2).get(4));
        assertEquals("value1", fileDatabase.getAll(partition2).get(0));
    }

    @Test
    void testingIsPresent() throws DatabaseOperationException {
        FileDatabase<String> fileDatabase = new FileDatabase<>();

        fileDatabase.addEntry(partition1, "value1", "value2", "value3", "value4");
        fileDatabase.addEntry(partition2, "value1", "value2");

        assertTrue(fileDatabase.isPresent(partition1, "value1"));
        assertTrue(fileDatabase.isPresent(partition1, "value2"));
        assertTrue(fileDatabase.isPresent(partition1, "value3"));
        assertFalse(fileDatabase.isPresent(partition1, "value10"));


        assertFalse(fileDatabase.isPresent(partition2, "value10"));
        assertTrue(fileDatabase.isPresent(partition2, "value1"));
        assertTrue(fileDatabase.isPresent(partition2, "value2"));
    }

    @AfterAll
    static void handleBackupFile() throws IOException {
        Path backup = Paths.get("database-path.config.backup");

        try {
            new File("database-path.config").delete();
            Files.move(backup, Paths.get("database-path.config"));
        }
        catch (NoSuchFileException e) {}
        finally {
            new File("database-path.config.backup").delete();
        }
    }

    static void deleteDir(File rootFile) {
        if(!rootFile.exists()) return;

        if(rootFile.isDirectory()) {
            File[] files = rootFile.listFiles();

            for (File file : files) {
                if (file.isDirectory())
                    deleteDir(file);
                else
                    file.delete();
            }
            rootFile.delete();
        }
    }
    @AfterAll
    static void deleteTestDatabaseDir() {
        File databaseDir = new File(database);
        deleteDir(databaseDir);
    }
}