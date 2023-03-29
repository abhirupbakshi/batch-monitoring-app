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
    void testingAssignedFacultiesGetterAndAddFaculties() throws NullArgumentException, EmptyArgumentException, InvalidDateException {
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
}