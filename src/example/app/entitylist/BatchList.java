package example.app.entitylist;

import example.app.entities.compartments.Batch;
import example.app.filedatabase.Exceptions.CannotCreateFileException;
import example.app.filedatabase.Exceptions.FileDatabaseConfigFileException;
import example.app.filedatabase.Exceptions.FileDatabaseInternalException;

import java.io.IOException;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.UUID;

/**
 * <h1>Class BatchList</h1>
 * This class represents a list of batches. It extends the EntityList class. It creates an abstraction for doing
 * FileDatabase related operations that is related to batches. It also provides some other methods that is
 * related to a batch list but not necessarily to a particular batch.
 */
public class BatchList extends EntityList<Batch> {
    private static final String PARTITION_NAME = "batches";

    /**
     * This method gets all the batches from the list of ids and creates a tabular format for displaying.
     * @param batchIds the ids of the batches
     * @return String representation of the table of batches
     * @throws CannotCreateFileException If the partition directory or the data directory could not be created
     * @throws FileDatabaseConfigFileException If the syntax in the database.config is wrong
     * @throws FileDatabaseInternalException If there's a mismatch between the record.ser file and the data directory entries
     * @throws IOException If the database.config could not be read or if there are any errors reading the resource or record.ser file
     * @throws ClassNotFoundException If the class is not found during deserialization of the record.ser file
     */
    public String getTable(TreeSet<UUID> batchIds)
            throws CannotCreateFileException, FileDatabaseConfigFileException,
            FileDatabaseInternalException, IOException, ClassNotFoundException {
        StringBuilder table = new StringBuilder();
        String formatString = "%-6s | %-24s | %-7s | %-12s | %-24s | %-24s\n";
        int i = 0;

        for(UUID id : batchIds) {
            Batch batch = this.get(id);

            if(batch == null) continue;

            table.append(String.format(formatString,
                    i+1,
                    batch.getCreationDate(),
                    batch.getName(),
                    batch.getCourse().getCode(),
                    batch.getStartDate(),
                    batch.getEndDate()
            ));

            i++;
        }

        return String.format(formatString +
                        "-------------------------------------------------------" +
                        "----------------------------------------------------------\n",
                "\033[1mSl No.\033[22m",
                "\033[1mCreation Date\033[22m           ",
                "\033[1mCode\033[22m   ",
                "\033[1mCourse Code\033[22m ",
                "\033[1mStart Date\033[22m              ",
                "\033[1mEnd Date\033[22m") + table;
    }

    public BatchList(String partitionName) {
        super(partitionName);
    }

    public BatchList() {
        this(PARTITION_NAME);
    }
}
