package example.app.services;

import example.app.entities.compartments.Faculty;
import example.app.entities.users.FacultyUser;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class FacultiesTest {
    static String database = "test-database";
    static String course = "course.ser";

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
        writer.write("faculty" + " = " + course + "\n");
        writer.flush();
        writer.close();
    }

    @BeforeEach
    @AfterEach
    void deleteDataBaseEntries() throws IOException {
        new ObjectOutputStream(new FileOutputStream(database + "/" + course))
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
        Faculties courses = new Faculties();

        courses.add();
        assertEquals( 0, ((LinkedList<Faculty>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).size() );

        courses.add(
                new Faculty(
                        "faculty1",
                        "description1",
                        "f1",
                        new FacultyUser(
                                "user1",
                                "password1",
                                "John",
                                "Doe",
                                "f1"
                        )
                ),
                new Faculty(
                        "faculty2",
                        "description2",
                        "f2",
                        new FacultyUser(
                                "user2",
                                "password2",
                                "John",
                                "Doe",
                                "f2"
                        )
                )
        );
        assertEquals( 2, ((LinkedList<Faculty>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).size() );

        assertEquals( "faculty1", ((LinkedList<Faculty>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).get(0).getName() );
        assertEquals( "description1", ((LinkedList<Faculty>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).get(0).getDescription() );
        assertEquals( "f1", ((LinkedList<Faculty>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).get(0).getCode() );
        assertEquals( new FacultyUser(
                "user1",
                "password1",
                "John",
                "Doe",
                "f1"
        ), ((LinkedList<Faculty>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).get(0).getFacultyUser() );

        assertEquals( "faculty2", ((LinkedList<Faculty>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).get(1).getName() );
        assertEquals( "description2", ((LinkedList<Faculty>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).get(1).getDescription() );
        assertEquals( "f2", ((LinkedList<Faculty>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).get(1).getCode() );
        assertEquals( new FacultyUser(
                "user2",
                "password2",
                "John",
                "Doe",
                "f2"
        ), ((LinkedList<Faculty>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).get(1).getFacultyUser() );
    }

    @Test
    void testingGetAll() throws Exception {
        Faculties courses = new Faculties();

        courses.add();
        assertEquals( 0, ((LinkedList<Faculty>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).size() );

        courses.add(
                new Faculty(
                        "faculty1",
                        "description1",
                        "f1",
                        new FacultyUser(
                                "user1",
                                "password1",
                                "John",
                                "Doe",
                                "f1"
                        )
                ),
                new Faculty(
                        "faculty2",
                        "description2",
                        "f2",
                        new FacultyUser(
                                "user2",
                                "password2",
                                "John",
                                "Doe",
                                "f2"
                        )
                )
        );

        assertEquals( 2, courses.getAll().size() );

        assertEquals( "faculty1", courses.getAll().get(0).getName() );
        assertEquals( "description1", courses.getAll().get(0).getDescription() );
        assertEquals( "f1", courses.getAll().get(0).getCode() );
        assertEquals( new FacultyUser(
                "user1",
                "password1",
                "John",
                "Doe",
                "f1"
        ), courses.getAll().get(0).getFacultyUser() );

        assertEquals( "faculty2", courses.getAll().get(1).getName() );
        assertEquals( "description2", courses.getAll().get(1).getDescription() );
        assertEquals( "f2", courses.getAll().get(1).getCode() );
        assertEquals( new FacultyUser(
                "user2",
                "password2",
                "John",
                "Doe",
                "f2"
        ), courses.getAll().get(1).getFacultyUser() );
    }

    @Test
    void testingRemove() throws Exception {
        Faculties courses = new Faculties();
        Faculty course1 = new Faculty(
                "faculty1",
                "description1",
                "f1",
                new FacultyUser(
                        "user1",
                        "password1",
                        "John",
                        "Doe",
                        "f1"
                )
        );

        courses.add(course1);
        assertEquals(1, courses.getAll().size());
        courses.remove(course1);
        assertEquals(0, courses.getAll().size());
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