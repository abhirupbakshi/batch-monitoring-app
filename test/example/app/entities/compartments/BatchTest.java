package example.app.entities.compartments;

import example.app.entities.users.FacultyUser;
import example.app.exceptions.EmptyArgumentException;
import example.app.exceptions.InvalidDateException;
import example.app.exceptions.InvalidIdException;
import example.app.exceptions.NullArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BatchTest {
    @Test
    @DisplayName("Testing constructors")
    void testingWithCorrectParameters() {
        assertDoesNotThrow(() -> {
            Batch batch = new Batch(
                "Test",
                new Course("Test Course", "Test Description", "Test Faculty"),
                "Test Description",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 2, 1)
            );
        });

        assertDoesNotThrow(() -> {
            Batch batch = new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    LocalDate.of(2020, 1, 1),
                    LocalDate.of(2020, 1, 2)
            );
        });

        assertDoesNotThrow(() -> {
            Batch batch = new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    LocalDate.of(2020, 1, 1),
                    1
            );
        });

        assertDoesNotThrow(() -> {
            Batch batch = new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    LocalDate.of(2020, 1, 1),
                    10
            );
        });

        assertDoesNotThrow(() -> {
            Batch batch = new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-01",
                    "2020-02-01"
            );
        });

        assertDoesNotThrow(() -> {
            Batch batch = new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-01",
                    "2020-01-02"
            );
        });

        assertDoesNotThrow(() -> {
            Batch batch = new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-01",
                    1
            );
        });

        assertDoesNotThrow(() -> {
            Batch batch = new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-01",
                    10
            );
        });
    }

    @Test
    @DisplayName("Testing constructors")
    void testingWithNullName() {
        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    null,
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    LocalDate.of(2020, 1, 1),
                    LocalDate.of(2020, 2, 1)
            );
        });

        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    null,
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    LocalDate.of(2020, 1, 1),
                    30
            );
        });

        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    null,
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-01",
                    "2020-02-01"
            );
        });

        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    null,
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-01",
                    30
            );
        });
    }

    @Test
    @DisplayName("Testing constructors")
    void testingWithEmptyName() {
        assertThrows(EmptyArgumentException.class, () -> {
            new Batch(
                    "",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    LocalDate.of(2020, 1, 1),
                    LocalDate.of(2020, 2, 1)
            );
        });

        assertThrows(EmptyArgumentException.class, () -> {
            new Batch(
                    "",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    LocalDate.of(2020, 1, 1),
                    30
            );
        });

        assertThrows(EmptyArgumentException.class, () -> {
            new Batch(
                    "",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-01",
                    "2020-02-01"
            );
        });

        assertThrows(EmptyArgumentException.class, () -> {
            new Batch(
                    "",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-01",
                    30
            );
        });
    }

    @Test
    @DisplayName("Testing constructors")
    void testingWithNullCourse() {
        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    "Test",
                    null,
                    "Test Description",
                    LocalDate.of(2020, 1, 1),
                    LocalDate.of(2020, 2, 1)
            );
        });

        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    "Test",
                    null,
                    "Test Description",
                    LocalDate.of(2020, 1, 1),
                    30
            );
        });

        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    "Test",
                    null,
                    "Test Description",
                    "2020-01-01",
                    "2020-02-01"
            );
        });

        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    "Test",
                    null,
                    "Test Description",
                    "2020-01-01",
                    30
            );
        });
    }

    @Test
    @DisplayName("Testing constructors")
    void testingWithNullDescription() {
        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    null,
                    LocalDate.of(2020, 1, 1),
                    LocalDate.of(2020, 2, 1)
            );
        });

        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    null,
                    LocalDate.of(2020, 1, 1),
                    30
            );
        });

        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    null,
                    "2020-01-01",
                    "2020-02-01"
            );
        });

        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    null,
                    "2020-01-01",
                    30
            );
        });
    }

    @Test
    @DisplayName("Testing constructors")
    void testingWithEmptyDescription() {
        assertDoesNotThrow(() -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "",
                    LocalDate.of(2020, 1, 1),
                    LocalDate.of(2020, 2, 1)
            );
        });

        assertDoesNotThrow(() -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "",
                    LocalDate.of(2020, 1, 1),
                    30
            );
        });

        assertDoesNotThrow(() -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "",
                    "2020-01-01",
                    "2020-02-01"
            );
        });

        assertDoesNotThrow(() -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "",
                    "2020-01-01",
                    30
            );
        });
    }

    @Test
    @DisplayName("Testing constructors")
    void testingWithNullStartDate() {
        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    null,
                    LocalDate.of(2020, 2, 1)
            );
        });

        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    (LocalDate) null,
                    30
            );
        });

        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    null,
                    "2020-02-01"
            );
        });

        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    (LocalDate) null,
                    30
            );
        });
    }

    @Test
    @DisplayName("Testing constructors")
    void testingWithNullEndDate() {
        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    LocalDate.of(2020, 1, 1),
                    null
            );
        });

        assertThrows(NullArgumentException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-01",
                    null
            );
        });
    }

    @Test
    @DisplayName("Testing constructors")
    void testingWithIncorrectlyFormattedStartDate() {
        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-1",
                    "2020-02-01"
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020/01/01",
                    "2020-02-01"
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "01-01-2020",
                    "2020-02-01"
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "",
                    "2020-02-01"
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-1",
                    10
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020/01/01",
                    10
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "01-01-2020",
                    10
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "",
                    10
            );
        });
    }

    @Test
    @DisplayName("Testing constructors")
    void testingWithIncorrectlyFormattedEndDate() {
        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-01",
                    "2020-02-1"
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-01",
                    "2020/02/01"
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-01",
                    "01-02-2020"
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-01",
                    ""
            );
        });
    }

    @Test
    @DisplayName("Testing constructors")
    void testingWithStartDateThatComesAfterEndDate() {
        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    LocalDate.of(2020, 1, 2),
                    LocalDate.of(2020, 1, 1)
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    LocalDate.of(2020, 2, 1),
                    LocalDate.of(2020, 1, 1)
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    LocalDate.of(2022, 1, 1),
                    LocalDate.of(2020, 1, 1)
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-02",
                    "2020-01-01"
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-02-01",
                    "2020-01-01"
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2023-01-01",
                    "2020-01-01"
            );
        });
    }

    @Test
    @DisplayName("Testing constructors")
    void testingWithStartDateThatEqualsAfterEndDate() {
        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    LocalDate.of(2020, 1, 1),
                    LocalDate.of(2020, 1, 1)
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    LocalDate.of(2020, 2, 1),
                    LocalDate.of(2020, 2, 1)
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    LocalDate.of(2022, 4, 1),
                    LocalDate.of(2022, 4, 1)
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2320-01-01",
                    "2320-01-01"
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-02-01",
                    "2020-02-01"
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2023-01-10",
                    "2023-01-10"
            );
        });
    }

    @Test
    @DisplayName("Testing constructors")
    void testingWithZeroOrNegativeDurationDays() {
        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    LocalDate.of(2020, 1, 1),
                    0
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    LocalDate.of(2020, 1, 1),
                    -1
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    LocalDate.of(2020, 1, 1),
                    -10
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-01",
                    0
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-01",
                    -1
            );
        });

        assertThrows(InvalidDateException.class, () -> {
            new Batch(
                    "Test",
                    new Course("Test Course", "Test Description", "Test Faculty"),
                    "Test Description",
                    "2020-01-01",
                    -10
            );
        });
    }

    @Test
    @DisplayName("Testing getter methods")
    void testingIdGetter() throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        Batch batch = new Batch(
                "Test",
                new Course("Test Course", "Test Description", "Test Faculty"),
                "Test Description",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2021, 1, 1)
        );

        assertInstanceOf(UUID.class, batch.getId());
        assertDoesNotThrow(() -> {
            UUID.fromString(batch.getId().toString());
        });
    }

    @Test
    @DisplayName("Testing getter methods")
    void testingNameGetter() throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        Batch batch = new Batch(
                "Test1",
                new Course("Test Course", "Test Description", "Test Faculty"),
                "Test Description",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2021, 1, 1)
        );

        assertInstanceOf(String.class, batch.getName());
        assertEquals("Test1", batch.getName());

        batch = new Batch(
                "Test2",
                new Course("Test Course", "Test Description", "Test Faculty"),
                "Test Description",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2021, 1, 1)
        );

        assertInstanceOf(String.class, batch.getName());
        assertEquals("Test2", batch.getName());
    }

    @Test
    @DisplayName("Testing getter methods")
    void testingCourseGetter() throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        Course course = new Course("Test Course", "Test Description", "Test Faculty");
        Batch batch = new Batch(
                "Test1",
                course,
                "Test Description",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2021, 1, 1)
        );

        assertInstanceOf(Course.class, batch.getCourse());
        assertEquals(course, batch.getCourse());
        assertEquals(course.getName(), batch.getCourse().getName());
        assertEquals(course.getCode(), batch.getCourse().getCode());

        course = new Course("Test", "Description", "Faculty");
        batch = new Batch(
                "Test1",
                course,
                "Test Description",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2021, 1, 1)
        );

        assertInstanceOf(Course.class, batch.getCourse());
        assertEquals(course, batch.getCourse());
        assertEquals(course.getName(), batch.getCourse().getName());
        assertEquals(course.getCode(), batch.getCourse().getCode());
    }

    @Test
    @DisplayName("Testing getter methods")
    void testingDescriptionGetter() throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        Course course = new Course("Test Course", "Test Description", "Test Faculty");
        Batch batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2021, 1, 1)
        );

        assertInstanceOf(String.class, batch.getDescription());
        assertEquals("Test Description1", batch.getDescription());

        course = new Course("Test Course", "Test Description", "Test Faculty");
        batch = new Batch(
                "Test1",
                course,
                "Test Description2",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2021, 1, 1)
        );

        assertInstanceOf(String.class, batch.getDescription());
        assertEquals("Test Description2", batch.getDescription());
    }

    @Test
    @DisplayName("Testing getter methods")
    void testingStartDateGetter() throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        Course course = new Course("Test Course", "Test Description", "Test Faculty");
        Batch batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2021, 1, 1)
        );

        assertInstanceOf(LocalDate.class, batch.getStartDate());
        assertEquals(LocalDate.of(2020, 1, 1), batch.getStartDate());

        course = new Course("Test Course", "Test Description", "Test Faculty");
        batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                "2020-02-01",
                "2021-01-01"
        );

        assertInstanceOf(LocalDate.class, batch.getStartDate());
        assertEquals(LocalDate.of(2020, 2, 1), batch.getStartDate());
    }

    @Test
    @DisplayName("Testing getter methods")
    void testingEndDateGetter() throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        Course course = new Course("Test Course", "Test Description", "Test Faculty");
        Batch batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2021, 1, 1)
        );

        assertInstanceOf(LocalDate.class, batch.getEndDate());
        assertEquals(LocalDate.of(2021, 1, 1), batch.getEndDate());

        course = new Course("Test Course", "Test Description", "Test Faculty");
        batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                "2020-02-01",
                "2021-03-01"
        );

        assertInstanceOf(LocalDate.class, batch.getEndDate());
        assertEquals(LocalDate.of(2021, 3, 1), batch.getEndDate());

        course = new Course("Test Course", "Test Description", "Test Faculty");
        batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                "2020-03-01",
                20
        );

        assertEquals(LocalDate.of(2020, 3, 1).plusDays(20), batch.getEndDate());
    }

    @Test
    @DisplayName("Testing getter methods")
    void testingDurationDaysGetter() throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        Course course = new Course("Test Course", "Test Description", "Test Faculty");
        Batch batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 3)
        );

        assertEquals(2, batch.getDurationDays());

        course = new Course("Test Course", "Test Description", "Test Faculty");
        batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                "2020-03-01",
                "2020-04-01"
        );

        assertEquals(31, batch.getDurationDays());

        course = new Course("Test Course", "Test Description", "Test Faculty");
        batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                "2020-03-01",
                20
        );

        assertEquals(20, batch.getDurationDays());

        course = new Course("Test Course", "Test Description", "Test Faculty");
        batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                "2020-03-01",
                1
        );

        assertEquals(1, batch.getDurationDays());
    }

    @Test
    @DisplayName("Testing getter methods")
    void testingBatchCreationDateGetter() throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        Course course = new Course("Test Course", "Test Description", "Test Faculty");
        Batch batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 3)
        );

        assertInstanceOf(LocalDate.class, batch.getBatchCreationDate());
        assertEquals(LocalDate.now(), batch.getBatchCreationDate());

        course = new Course("Test Course", "Test Description", "Test Faculty");
        batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                "2020-03-01",
                "2020-04-01"
        );

        assertInstanceOf(LocalDate.class, batch.getBatchCreationDate());
        assertEquals(LocalDate.now(), batch.getBatchCreationDate());

        course = new Course("Test Course", "Test Description", "Test Faculty");
        batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                "2020-03-01",
                20
        );

        assertInstanceOf(LocalDate.class, batch.getBatchCreationDate());
        assertEquals(LocalDate.now(), batch.getBatchCreationDate());

        course = new Course("Test Course", "Test Description", "Test Faculty");
        batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 3, 1),
                1
        );

        assertInstanceOf(LocalDate.class, batch.getBatchCreationDate());
        assertEquals(LocalDate.now(), batch.getBatchCreationDate());
    }

    @Test
    @DisplayName("Testing getter methods")
    void testingAssignedFacultiesGetter() throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        Course course = new Course("Test Course", "Test Description", "Test Faculty");
        Batch batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 3)
        );

        assertDoesNotThrow(() -> batch.addFaculties());

        assertDoesNotThrow(() -> {
            batch.addFaculties (
                new Faculty (
                        "Test Faculty 1",
                        "Test Faculty 1 Description",
                        "TF1",
                        new FacultyUser(
                                "Test Faculty 1 User 1",
                                "Password1",
                                "Faculty User 1",
                                "Faculty User 1 Last Name",
                                "fu1@example.com"
                        )
                )
            );
        });

        for (UUID key : batch.getAssignedFaculties().keySet()) {
            Faculty faculty = batch.getAssignedFaculties().get(key);

            assertEquals("Test Faculty 1", faculty.getName());
            assertEquals("Test Faculty 1 Description", faculty.getDescription());
            assertEquals("TF1", faculty.getCode());
            assertInstanceOf(FacultyUser.class, faculty.getFacultyUser());
            assertEquals("Test Faculty 1 User 1", faculty.getFacultyUser().getUsername());
            assertEquals("Faculty User 1", faculty.getFacultyUser().getFirstName());
            assertEquals("Faculty User 1 Last Name", faculty.getFacultyUser().getLastName());
            assertEquals("fu1@example.com", faculty.getFacultyUser().getEmail());
        }
    }

    @Test
    @DisplayName("Testing setter methods")
    void testingNameSetter() throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        Course course = new Course("Test Course", "Test Description", "Test Faculty");
        Batch batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 3)
        );

        batch.setName("Test2");
        assertEquals("Test2", batch.getName());

        course = new Course("Test Course", "Test Description", "Test Faculty");
        batch = new Batch(
                "Test4",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 3)
        );

        Batch finalBatch = batch;
        Batch finalBatch1 = batch;
        assertThrows(EmptyArgumentException.class, () -> finalBatch1.setName(""));
        assertThrows(NullArgumentException.class, () -> finalBatch.setName(null));
    }

    @Test
    @DisplayName("Testing setter methods")
    void testingCourseSetter() throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        Course course = new Course("Test Course", "Test Description", "Test Code");
        Batch batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 3)
        );

        batch.setCourse(
                new Course(
                        "Test Course1",
                        "Test Description1",
                        "Test Code1"
                )
        );

        assertEquals("Test Course1", batch.getCourse().getName());
        assertEquals("Test Description1", batch.getCourse().getDescription());
        assertEquals("Test Code1", batch.getCourse().getCode());

        assertThrows(NullArgumentException.class, () -> batch.setCourse(null));
    }

    @Test
    @DisplayName("Testing setter methods")
    void testingDescriptionSetter() throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        Course course = new Course("Test Course", "Test Description", "Test Code");
        Batch batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 3)
        );

        batch.setDescription("Test Description2");

        assertEquals("Test Description2", batch.getDescription());
        assertThrows(NullArgumentException.class, () -> batch.setDescription(null));
    }

    @Test
    @DisplayName("Testing setter methods")
    void testingStartDateSetter() throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        Course course = new Course("Test Course", "Test Description", "Test Code");
        Batch batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 4, 1)
        );

        batch.setStartDate(LocalDate.of(2020, 1, 2));
        assertEquals(LocalDate.of(2020, 1, 2), batch.getStartDate());

        batch.setStartDate(LocalDate.of(2020, 1, 2).toString());
        assertEquals(LocalDate.of(2020, 1, 2), batch.getStartDate());

        assertThrows(NullArgumentException.class, () -> batch.setStartDate((LocalDate) null));
        assertThrows(InvalidDateException.class, () -> batch.setStartDate("2020/01/01"));
        assertThrows(InvalidDateException.class, () -> batch.setStartDate("2021-01-01"));
        assertThrows(InvalidDateException.class, () -> batch.setStartDate(LocalDate.of(2025, 4, 3)));
    }

    @Test
    @DisplayName("Testing setter methods")
    void testingEndDateSetter() throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        Course course = new Course("Test Course", "Test Description", "Test Code");
        Batch batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 4, 1)
        );

        batch.setEndDate(LocalDate.of(2020, 5, 2));
        assertEquals(LocalDate.of(2020, 5, 2), batch.getEndDate());

        batch.setEndDate(LocalDate.of(2020, 5, 2).toString());
        assertEquals(LocalDate.of(2020, 5, 2), batch.getEndDate());

        assertThrows(NullArgumentException.class, () -> batch.setEndDate((LocalDate) null));
        assertThrows(InvalidDateException.class, () -> batch.setEndDate("2022/01/01"));
        assertThrows(InvalidDateException.class, () -> batch.setEndDate("2019-01-01"));
        assertThrows(InvalidDateException.class, () -> batch.setEndDate((LocalDate.of(2019, 5, 2))));
    }

    @Test
    @DisplayName("Testing setter methods")
    void testingDurationDaysSetter() throws NullArgumentException, EmptyArgumentException, InvalidDateException {
        Course course = new Course("Test Course", "Test Description", "Test Code");
        Batch batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                20
        );

        assertThrows(InvalidDateException.class, () -> batch.setDurationDays(0));
        assertThrows(InvalidDateException.class, () -> batch.setDurationDays(-1));

        batch.setDurationDays(21);
        assertEquals(21, batch.getDurationDays());
    }

    @Test
    @DisplayName("Testing addFaculties method")
    void testingAddFaculties() throws NullArgumentException, EmptyArgumentException, InvalidDateException, InvalidIdException {
        Course course = new Course("Test Course", "Test Description", "Test Code");
        Batch batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                20
        );

        batch.addFaculties(
            new Faculty(
                "Test Faculty 1",
                "Test Faculty 1 Description",
                "TF1",
                new FacultyUser(
                    "Test Faculty User 1",
                    "1234",
                    "Test Faculty User 1 First Name",
                    "Test Faculty User 1 Last Name",
                    "fu1@example.com"
                )
            )
        );

        assertEquals(1, batch.getAssignedFaculties().size());

        for(UUID key : batch.getAssignedFaculties().keySet()) {
            assertEquals("Test Faculty 1", batch.getAssignedFaculties().get(key).getName());
            assertEquals("Test Faculty 1 Description", batch.getAssignedFaculties().get(key).getDescription());
            assertEquals("TF1", batch.getAssignedFaculties().get(key).getCode());
            assertEquals("Test Faculty User 1", batch.getAssignedFaculties().get(key).getFacultyUser().getUsername());
            assertTrue(batch.getAssignedFaculties().get(key).getFacultyUser().passwordEquals("1234"));
            assertEquals("Test Faculty User 1 First Name", batch.getAssignedFaculties().get(key).getFacultyUser().getFirstName());
            assertEquals("Test Faculty User 1 Last Name", batch.getAssignedFaculties().get(key).getFacultyUser().getLastName());
            assertEquals("fu1@example.com", batch.getAssignedFaculties().get(key).getFacultyUser().getEmail());
        }
    }

    @Test
    @DisplayName("Testing removeFaculties method")
    void testingRemoveFaculties() throws NullArgumentException, EmptyArgumentException, InvalidDateException, InvalidIdException {
        Course course = new Course("Test Course", "Test Description", "Test Code");
        Batch batch = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                20
        );

        Faculty faculty = new Faculty (
            "Test Faculty 1",
            "Test Faculty 1 Description",
            "TF1",
            new FacultyUser(
                    "Test Faculty User 1",
                    "1234",
                    "Test Faculty User 1 First Name",
                    "Test Faculty User 1 Last Name",
                    "fu1@example.com"
            )
        );

        batch.addFaculties(faculty);
        assertEquals(1, batch.getAssignedFaculties().size());

        batch.removeFaculties(faculty);
        assertEquals(0, batch.getAssignedFaculties().size());
    }

    @Test
    void testingEquals() throws InvalidDateException, NullArgumentException, EmptyArgumentException {
        Course course = new Course("Test Course", "Test Description", "Test Code");
        Batch batch1 = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 3)
        );
        Batch batch2 = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 3)
        );
        Batch batch3 = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 4)
        );

        assertEquals(batch1, batch2);
        assertNotEquals(batch1, batch3);
    }

    @Test
    void testingHashCode() throws InvalidDateException, NullArgumentException, EmptyArgumentException {
        Course course = new Course("Test Course", "Test Description", "Test Code");
        Batch batch1 = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 3)
        );
        Batch batch2 = new Batch(
                "Test1",
                course,
                "Test Description1",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 3)
        );

        assertEquals(batch1.hashCode(), batch2.hashCode());
    }
}