package example.app.entitylist;

import example.app.entities.users.User;
import example.app.filedatabase.Exceptions.CannotCreateFileException;
import example.app.filedatabase.Exceptions.FileDatabaseConfigFileException;
import example.app.filedatabase.Exceptions.FileDatabaseInternalException;

import java.io.IOException;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.UUID;

/**
 * <h1>Class UserList</h1>
 * This class represents a list of users. It extends the EntityList class. It creates an abstraction for doing
 * FileDatabase related operations that is related to users. It also provides some other methods that is
 * related to a user list but not necessarily to a particular user.
 */
public class UserList extends EntityList<User> {
    private static final String PARTITION_NAME = "users";

    /**
     * This method gets all the users from the list of ids and creates a tabular format for displaying.
     * @param userIds the ids of the users
     * @return String representation of the table of users
     * @throws CannotCreateFileException If the partition directory or the data directory could not be created
     * @throws FileDatabaseConfigFileException If the syntax in the database.config is wrong
     * @throws FileDatabaseInternalException If there's a mismatch between the record.ser file and the data directory entries
     * @throws IOException If the database.config could not be read or if there are any errors reading the resource or record.ser file
     * @throws ClassNotFoundException If the class is not found during deserialization of the record.ser file
     */
    public String getTable(TreeSet<UUID> userIds)
            throws CannotCreateFileException, FileDatabaseConfigFileException,
            FileDatabaseInternalException, IOException, ClassNotFoundException {
        StringBuilder table = new StringBuilder();
        String formatString = "%-6s | %-24s | %-15s | %-12s\n";
        int i = 0;

        for(UUID id : userIds) {
            User user = this.get(id);

            if(user == null) continue;

            table.append(String.format(formatString,
                    i+1,
                    user.getCreationDate(),
                    user.getFirstName() + " " + user.getLastName(),
                    user.getEmail()
            ));

            i++;
        }

        return String.format(formatString +
                        "------------------------------" +
                        "------------------------------\n",
                "\033[1mSl No.\033[22m",
                "\033[1mCreation Date\033[22m           ",
                "\033[1mName\033[22m           ",
                "\033[1mEmail\033[22m ") + table;
    }

    public UserList(String partitionName) {
        super(partitionName);
    }

    public UserList() {
        this(PARTITION_NAME);
    }
}
