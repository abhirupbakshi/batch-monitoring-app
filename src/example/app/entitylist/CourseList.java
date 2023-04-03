package example.app.entitylist;

import example.app.entities.compartments.Course;
import example.app.filedatabase.Exceptions.CannotCreateFileException;
import example.app.filedatabase.Exceptions.FileDatabaseConfigFileException;
import example.app.filedatabase.Exceptions.FileDatabaseInternalException;

import java.io.IOException;
import java.util.TreeSet;
import java.util.UUID;

/**
 * <h1>Class CourseList</h1>
 * This class represents a list of courses. It extends the EntityList class. It creates an abstraction for doing
 * FileDatabase related operations that is related to courses. It also provides some other methods that is
 * related to a course list but not necessarily to a particular course.
 */
public class CourseList extends EntityList<Course> {
    private static final String PARTITION_NAME = "courses";

    /**
     * This method gets all the courses from the list of ids and creates a tabular format for displaying.
     * @param courseIds the ids of the courses
     * @return String representation of the table of courses
     * @throws CannotCreateFileException If the partition directory or the data directory could not be created
     * @throws FileDatabaseConfigFileException If the syntax in the database.config is wrong
     * @throws FileDatabaseInternalException If there's a mismatch between the record.ser file and the data directory entries
     * @throws IOException If the database.config could not be read or if there are any errors reading the resource or record.ser file
     * @throws ClassNotFoundException If the class is not found during deserialization of the record.ser file
     */
    public String getTable(TreeSet<UUID> courseIds)
            throws CannotCreateFileException, FileDatabaseConfigFileException,
            FileDatabaseInternalException, IOException, ClassNotFoundException {
        StringBuilder table = new StringBuilder();
        String formatString = "%-6s | %-24s | %-15s | %-12s\n";
        int i = 0;

        for(UUID id : courseIds) {
            Course course = this.get(id);

            if(course == null) continue;

            table.append(String.format(formatString,
                    i+1,
                    course.getCreationDate(),
                    course.getName(),
                    course.getCode()
            ));

            i++;
        }

        return String.format(formatString +
                        "------------------------------" +
                        "------------------------------\n",
                "\033[1mSl No.\033[22m", "\033[1mCreation Date\033[22m           ",
                "\033[1mName\033[22m           ",
                "\033[1mCode\033[22m") + table;
    }

    public CourseList(String partitionName) {
        super(partitionName);
    }

    public CourseList() {
        this(PARTITION_NAME);
    }
}
