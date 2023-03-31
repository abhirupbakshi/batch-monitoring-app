package example.app.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataBasePartitionNamesTest {
    @Test
    void testingValue() {
        assertEquals("course", DataBasePartitionNames.COURSE.value());
        assertEquals("faculty", DataBasePartitionNames.FACULTY.value());
        assertEquals("batch", DataBasePartitionNames.BATCH.value());
        assertEquals("faculty-user", DataBasePartitionNames.FACULTY_USER.value());
    }
}