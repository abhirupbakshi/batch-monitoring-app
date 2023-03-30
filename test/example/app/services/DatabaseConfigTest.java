package example.app.services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

class DatabaseConfigTest {
    @BeforeAll
    static void createBackup() throws IOException {
        Path source = Paths.get("database-path.config");
        Path target = Paths.get("database-path.config.backup");

        try {
            Files.copy(source, target);
        }
        catch (NoSuchFileException e) {
            DatabaseConfig.createNew();
        }
    }

    @BeforeEach
    void setUp() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("database-path.config"));

        writer.write("""
                # Each key-value pair is separated by a '='
                # The "root" key is the path to the Database
                # Example: foo="path to it's file" or foo = "path to it's file"
                # Lines starting with # are ignored
                """);

        writer.flush();
        writer.close();
    }

    @Test
    void testingCreateNew() throws IOException {
        DatabaseConfig.createNew();

        BufferedReader reader = new BufferedReader(new FileReader("database-path.config"));
        StringBuilder lines = new StringBuilder();

        while (reader.ready()) {
            lines.append(reader.readLine()).append("\n");
        }
        reader.close();

        assertEquals("""
                # Each key-value pair is separated by a '='
                # The "root" key is the path to the Database
                # Example: foo="path to it's file" or foo = "path to it's file"
                # Lines starting with # are ignored
                """, lines.toString());
    }

    @Test
    void  testingReadData() throws IOException {
        assertEquals("", DatabaseConfig.readAll());

        BufferedWriter writer = new BufferedWriter(new FileWriter("database-path.config", true));

        writer.write("Test String1\n");
        writer.flush();
        assertEquals("Test String1\n", DatabaseConfig.readAll());

        writer.write("""
                            Test String2
       Test String3
                Test # Data
        """);
        writer.flush();
        assertEquals(
                """
                Test String1
                Test String2
                Test String3
                Test # Data
                """, DatabaseConfig.readAll());

        writer.close();
    }

    @Test
    void testingGetAllEntry() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("database-path.config", true));

        writer.write("key1=value1\n");
        writer.write("key2 =value2\n");
        writer.write("key3 = value3\n");
        writer.write("key4=                                 value4\n");
        writer.flush();
        writer.close();

        assertEquals("value1", DatabaseConfig.getAllEntry().get("key1"));
        assertEquals("value2", DatabaseConfig.getAllEntry().get("key2"));
        assertEquals("value3", DatabaseConfig.getAllEntry().get("key3"));
        assertEquals("value4", DatabaseConfig.getAllEntry().get("key4"));
    }

    @Test
    void testingGetValue() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("database-path.config", true));

        writer.write("key1=value1\n");
        writer.write("key2 =value2\n");
        writer.write("key3 = value3\n");
        writer.write("key4=                                 value4\n");
        writer.flush();
        writer.close();

        assertEquals("value1", DatabaseConfig.getPath("key1"));
        assertEquals("value2", DatabaseConfig.getPath("key2"));
        assertEquals("value3", DatabaseConfig.getPath("key3"));
        assertEquals("value4", DatabaseConfig.getPath("key4"));
        assertNull(DatabaseConfig.getPath("key5"));
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
}