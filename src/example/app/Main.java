package example.app;


import example.app.constants.DataBasePartitionNames;
import example.app.entities.compartments.Batch;
import example.app.entities.compartments.Course;
import example.app.entities.compartments.Faculty;
import example.app.entities.users.FacultyUser;
import example.app.exceptions.EmptyArgumentException;
import example.app.exceptions.InvalidDateException;
import example.app.exceptions.InvalidIdException;
import example.app.exceptions.NullArgumentException;
import example.app.services.Batches;
import example.app.services.Courses;
import example.app.services.EntityList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws Exception {
        EntityList<Batch> entityList = new EntityList<>();

//        new Batches().add(
//                new Batch(
//                        "batch1",
//                        new Course(
//                                "course1",
//                                "description1",
//                                "c1"
//                        ),
//                        "description1",
//                        LocalDate.of(2020, 1, 1),
//                        LocalDate.of(2021, 1, 1)
//                ),
//                new Batch(
//                        "batch2",
//                        new Course(
//                                "course2",
//                                "description2",
//                                "c2"
//                        ),
//                        "description2",
//                        LocalDate.of(2020, 1, 1),
//                        LocalDate.of(2021, 1, 1)
//                )
//        );

//        System.out.println(new Batches());

        Batch batch = new Batch(
                "batch1",
                new Course(
                        "course1",
                        "description1",
                        "c1"
                ),
                "description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2021, 1, 1)
        );
        batch.addFaculties(new Faculty(
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
            )
        );

        Faculty faculty = new Faculty(
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
        faculty.addBatches(
                batch
        );
        System.out.println(
                faculty
        );

    }
}
