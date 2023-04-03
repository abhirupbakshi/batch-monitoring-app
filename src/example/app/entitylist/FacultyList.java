package example.app.entitylist;

import example.app.entities.compartments.Faculty;
import example.app.filedatabase.Exceptions.CannotCreateFileException;
import example.app.filedatabase.Exceptions.FileDatabaseConfigFileException;
import example.app.filedatabase.Exceptions.FileDatabaseInternalException;

import java.io.IOException;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.UUID;

/**
 * <h1>Class FacultyList</h1>
 * This class represents a list of faculties. It extends the EntityList class. It creates an abstraction for doing
 * FileDatabase related operations that is related to faculties. It also provides some other methods that is
 * related to a faculty list but not necessarily to a particular faculty.
 */
public class FacultyList extends EntityList<Faculty> {
    private static final String PARTITION_NAME = "faculties";

    /**
     * This method gets all the faculties from the list of ids and creates a tabular format for displaying.
     * @param facultyIds the ids of the faculties
     * @return String representation of the table of faculties
     * @throws CannotCreateFileException If the partition directory or the data directory could not be created
     * @throws FileDatabaseConfigFileException If the syntax in the database.config is wrong
     * @throws FileDatabaseInternalException If there's a mismatch between the record.ser file and the data directory entries
     * @throws IOException If the database.config could not be read or if there are any errors reading the resource or record.ser file
     * @throws ClassNotFoundException If the class is not found during deserialization of the record.ser file
     */
    public String getTable(TreeSet<UUID> facultyIds)
            throws CannotCreateFileException, FileDatabaseConfigFileException,
            FileDatabaseInternalException, IOException, ClassNotFoundException {
        StringBuilder table = new StringBuilder();
        String formatString = "%-6s | %-24s | %-7s | %-18s | %-12s\n";
        int i = 0;

        for(UUID id : facultyIds) {
            Faculty faculty = this.get(id);

            if(faculty == null) continue;

            table.append(String.format(formatString,
                    i+1,
                    faculty.getCreationDate(),
                    faculty.getCode(),
                    faculty.getFacultyUser().getFirstName() + " " + faculty.getFacultyUser().getLastName(),
                    faculty.getFacultyUser().getEmail()
            ));

            i++;
        }

        return String.format(formatString +
                        "-------------------------------------------" +
                        "-------------------------------------------\n",
                "\033[1mSl No.\033[22m",
                "\033[1mCreation Date\033[22m           ",
                "\033[1mCode\033[22m   ",
                "\033[1mFaculty User Name\033[22m ",
                "\033[1mFaculty User Email\033[22m              ") + table;
    }

    public FacultyList(String partitionName) {
        super(partitionName);
    }

    public FacultyList() {
        this(PARTITION_NAME);
    }
}
