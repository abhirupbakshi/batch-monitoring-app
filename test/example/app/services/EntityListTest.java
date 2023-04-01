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

class EntityListTest {
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

    @Test
    void testingGetAll() throws DatabaseOperationException, IOException {
        EntityList<String> list = new EntityList<>();

        deleteDir(new File(database));
        assertDoesNotThrow(() -> list.getAll(partition1));
        assertDoesNotThrow(() -> list.getAll(partition2));

        new ObjectOutputStream(new FileOutputStream(database + "/" + partition1)).writeObject(new LinkedList<>(Arrays.asList("1", "2", "3")));
        new ObjectOutputStream(new FileOutputStream(database + "/" + partition2)).writeObject(new LinkedList<>(Arrays.asList("1", "2")));

        assertEquals(3, list.getAll(partition1).size());
        assertEquals(2, list.getAll(partition2).size());
    }

    @Test
    void testingAddEntry() throws DatabaseOperationException {
        EntityList<String> list = new EntityList<>();

        assertThrows(DatabaseOperationException.class, () -> list.addEntry(partition1, "1", "Two", "3", "3"));
        assertThrows(DatabaseOperationException.class, () -> list.addEntry(partition2, "2", "Three", "Three", "4"));

        deleteDir(new File(database));
        assertDoesNotThrow(() -> list.addEntry(partition1, "1", "Two", "3"));
        assertEquals(3, list.getAll(partition1).size());
        deleteDir(new File(database));

        assertEquals(0, list.getAll(partition2).size());

        assertDoesNotThrow(() -> list.addEntry(partition1, "1", "Two", "3"));
        assertDoesNotThrow(() -> list.addEntry(partition2, "2", "Three", "4"));

        assertEquals(3, list.getAll(partition1).size());
        assertEquals("Two", list.getAll(partition1).get(1));
        assertEquals("3", list.getAll(partition1).get(2));

        assertEquals(3, list.getAll(partition2).size());
        assertEquals("2", list.getAll(partition2).get(0));
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

    @AfterAll
    static void deleteTestDatabaseDir() {
        File databaseDir = new File(database);
        deleteDir(databaseDir);
    }
}