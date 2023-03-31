package example.app.constants;

/**
 * <h1>Enum DataBasePartitionNames</h1>
 * This enum is for storing the partition names of different entities the file database.
 */
public enum DataBasePartitionNames {
    COURSE("course"),
    FACULTY("faculty"),
    BATCH("batch"),
    FACULTY_USER("faculty-user");

    private final String name;

    private DataBasePartitionNames(String name) {
        this.name = name;
    }

    public String value() {
        return name;
    }
}
