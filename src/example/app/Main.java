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
import example.app.services.EntityList;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws Exception {
        EntityList<Batch> entityList = new EntityList<>();

        // Add faculty
        entityList.addEntry(
                DataBasePartitionNames.BATCH.value(),
                new Batch(
                        "Batch 1",
                        new Course("Course 1", "Course description 1", "c1"),
                        "Description 1",
                        LocalDate.of(2020, 1, 1),
                        LocalDate.of(2021, 1, 1)
                )
        );


        System.out.println();
    }
}
