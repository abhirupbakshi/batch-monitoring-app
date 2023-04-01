package example.app.services;

import example.app.entities.compartments.Course;
import example.app.exceptions.DatabaseOperationException;
import example.app.exceptions.EmptyArgumentException;
import example.app.exceptions.NullArgumentException;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CoursesTest {
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
        writer.write("course" + " = " + course + "\n");
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
    void testingAdd() throws DatabaseOperationException, IOException, ClassNotFoundException, NullArgumentException, EmptyArgumentException {
        Courses courses = new Courses();

        courses.add();
        assertEquals( 0, ((LinkedList<Courses>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).size() );

        courses.add(
                new Course(
                        "course1",
                        "description1",
                        "c1"
                ),
                new Course(
                        "course2",
                        "description2",
                        "c2"
                )
        );
        assertEquals( 2, ((LinkedList<Course>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).size() );

        assertEquals( "course1", ((LinkedList<Course>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).get(0).getName() );
        assertEquals( "description1", ((LinkedList<Course>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).get(0).getDescription() );
        assertEquals( "c1", ((LinkedList<Course>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).get(0).getCode() );

        assertEquals( "course2", ((LinkedList<Course>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).get(1).getName() );
        assertEquals( "description2", ((LinkedList<Course>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).get(1).getDescription() );
        assertEquals( "c2", ((LinkedList<Course>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).get(1).getCode() );
    }

    @Test
    void testingGetAll() throws DatabaseOperationException, IOException, ClassNotFoundException, NullArgumentException, EmptyArgumentException {
        Courses courses = new Courses();

        courses.add();
        assertEquals( 0, ((LinkedList<Courses>) new ObjectInputStream(new FileInputStream(database + "/" + course)).readObject()).size() );

        courses.add(
                new Course(
                        "course1",
                        "description1",
                        "c1"
                ),
                new Course(
                        "course2",
                        "description2",
                        "c2"
                )
        );
        assertEquals( 2, courses.getAll().size() );

        assertEquals( "course1", courses.getAll().get(0).getName() );
        assertEquals( "description1", courses.getAll().get(0).getDescription() );
        assertEquals( "c1", courses.getAll().get(0).getCode() );

        assertEquals( "course2", courses.getAll().get(1).getName() );
        assertEquals( "description2", courses.getAll().get(1).getDescription() );
        assertEquals( "c2", courses.getAll().get(1).getCode() );
    }

    @Test
    void testingRemove() throws Exception {
        Courses courses = new Courses();
        Course course1 = new Course(
                "course1",
                "description1",
                "c1"
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