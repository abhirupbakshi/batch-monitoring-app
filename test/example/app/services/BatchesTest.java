package example.app.services;

import example.app.entities.compartments.Batch;
import example.app.entities.compartments.Course;
import example.app.exceptions.DatabaseOperationException;
import example.app.exceptions.EmptyArgumentException;
import example.app.exceptions.InvalidDateException;
import example.app.exceptions.NullArgumentException;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class BatchesTest {
    static String database = "test-database";
    static String batch = "batch.ser";

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
        writer.write("batch" + " = " + batch + "\n");
        writer.flush();
        writer.close();
    }

    @BeforeEach
    @AfterEach
    void deleteDataBaseEntries() throws IOException {
        new ObjectOutputStream(new FileOutputStream(database + "/" + batch))
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
    void testingAdd() throws Exception {
        Batches batches = new Batches();

        batches.add();
        assertEquals( 0, ((LinkedList<Courses>) new ObjectInputStream(new FileInputStream(database + "/" + batch)).readObject()).size() );

        batches.add(
            new Batch(
                    "batch1",
                    new Course(
                            "course1",
                            "description1",
                            "c1"
                    ),
                    "description1",
                    LocalDate.of(2020, 1, 1),
                    LocalDate.of(2021, 1, 1)
            ),
            new Batch(
                    "batch2",
                    new Course(
                            "course2",
                            "description2",
                            "c2"
                    ),
                    "description2",
                    LocalDate.of(2020, 1, 1),
                    LocalDate.of(2021, 1, 1)
            )
        );

        assertEquals( 2, ((LinkedList<Batch>) new ObjectInputStream(new FileInputStream(database + "/" + batch)).readObject()).size() );

        assertEquals( "batch1", ((LinkedList<Batch>) new ObjectInputStream(new FileInputStream(database + "/" + batch)).readObject()).get(0).getName() );
        assertEquals( "description1", ((LinkedList<Batch>) new ObjectInputStream(new FileInputStream(database + "/" + batch)).readObject()).get(0).getDescription() );
        assertEquals( new Course(
                "course1",
                "description1",
                "c1"
        ), ((LinkedList<Batch>) new ObjectInputStream(new FileInputStream(database + "/" + batch)).readObject()).get(0).getCourse() );

        assertEquals( "batch2", ((LinkedList<Batch>) new ObjectInputStream(new FileInputStream(database + "/" + batch)).readObject()).get(1).getName() );
        assertEquals( "description2", ((LinkedList<Batch>) new ObjectInputStream(new FileInputStream(database + "/" + batch)).readObject()).get(1).getDescription() );
        assertEquals( new Course(
                "course2",
                "description2",
                "c2"
        ), ((LinkedList<Batch>) new ObjectInputStream(new FileInputStream(database + "/" + batch)).readObject()).get(1).getCourse() );
    }

    @Test
    void testingRemove() throws Exception {
        Batches batches = new Batches();

        batches.add(
                new Batch(
                        "batch1",
                        new Course(
                                "course1",
                                "description1",
                                "c1"
                        ),
                        "description1",
                        LocalDate.of(2020, 1, 1),
                        LocalDate.of(2021, 1, 1)
                )
        );

        assertEquals(1, batches.getAll().size());
        batches.remove(batches.getAll().get(0));
        assertEquals(0, batches.getAll().size());
    }

    @Test
    void testingGetAll() throws DatabaseOperationException, IOException, ClassNotFoundException, NullArgumentException, EmptyArgumentException, InvalidDateException {
        Batches batches = new Batches();

        batches.add();
        assertEquals( 0, ((LinkedList<Batch>) new ObjectInputStream(new FileInputStream(database + "/" + batch)).readObject()).size() );

        batches.add(
                new Batch(
                        "batch1",
                        new Course(
                                "course1",
                                "description1",
                                "c1"
                        ),
                        "description1",
                        LocalDate.of(2020, 1, 1),
                        LocalDate.of(2021, 1, 1)
                ),
                new Batch(
                        "batch2",
                        new Course(
                                "course2",
                                "description2",
                                "c2"
                        ),
                        "description2",
                        LocalDate.of(2020, 1, 1),
                        LocalDate.of(2021, 1, 1)
                )
        );

        assertEquals( 2, batches.getAll().size() );

        assertEquals( "batch1", batches.getAll().get(0).getName() );
        assertEquals( "description1", batches.getAll().get(0).getDescription() );
        assertEquals( new Course(
                "course1",
                "description1",
                "c1"
        ), batches.getAll().get(0).getCourse() );

        assertEquals( "batch2", batches.getAll().get(1).getName() );
        assertEquals( "description2", batches.getAll().get(1).getDescription() );
        assertEquals( new Course(
                "course2",
                "description2",
                "c2"
        ), batches.getAll().get(1).getCourse() );
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