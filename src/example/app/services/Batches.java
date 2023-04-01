package example.app.services;

import example.app.constants.DataBasePartitionNames;
import example.app.entities.compartments.Batch;
import example.app.exceptions.DatabaseOperationException;

import java.util.LinkedList;

/**
 * <h1>Class Batches</h1>
 * This class is for managing the batch list and perform different operations
 * like addition, deletion, updating, searching, etc. It essentially creates an abstraction of the
 * underlying implementation of interacting with the file database.
 */
public class Batches extends EntityList<Batch> {
    /**
     * This method adds an array of batches (varargs) to the database. If the course already exists, it throws an
     * DatabaseOperationException.
     * @param object the batches to be added
     * @throws DatabaseOperationException in case there's any problem adding the batch
     */
    public void add(Batch... object) throws DatabaseOperationException {
        super.addEntry(DataBasePartitionNames.BATCH.value(), object);
    }

    /**
     * This method removes an array of batches (varargs) from the database.
     * In case it encounters a problem with reading the config file or in writing operation, it will throw an DatabaseOperationException
     * @param object the batches to be removed
     * @throws DatabaseOperationException in case there's any problem reading the config file or in writing to the database file
     */
    public void remove(Batch... object) throws DatabaseOperationException {
        super.removeEntry(DataBasePartitionNames.BATCH.value(), object);
    }

    /**
     * This method returns a linked list of the batches. In case the root directory or file does not exist, it creates it.
     * In case the path cannot be retrieved from the config file, or if there's a problem in reading
     * the database file or creating it, it throws an DatabaseOperationException.
     * @return a linked list of batches
     * @throws DatabaseOperationException in case there's any problem reading the config file or the database file
     * or creating the directory or the file
     */
    public LinkedList<Batch> getAll() throws DatabaseOperationException {
        return super.getAll(DataBasePartitionNames.BATCH.value());
    }


    /**
     * This method takes a linked list of batches and returns a string containing the information of all the batches
     * in a tabular format
     * @param batchList a linked list of batches
     * @return a string containing the information of all the batches in a tabular format
     */
    public String createTabularFormat(LinkedList<Batch> batchList) {
        StringBuilder list = new StringBuilder();
        String formatString = "%-8s %-14s %-14s %-14s %-14s %-14s\n";
        int i = 0;

        for(Batch batch : batchList) {
            list.append(String.format(formatString,
                    i+1,
                    batch.getBatchCreationDate(),
                    batch.getName(),
                    batch.getCourse().getCode(),
                    batch.getStartDate(),
                    batch.getEndDate()
            ));

            i++;
        }

        return String.format(formatString +
                        "------------------------------------" +
                        "-------------------------------------------\n",
                "Sl No.", "Creation Date", "Name", "Course Code", "Start Date", "End Date") + list;
    }


    /**
     * This method is an overridden toString method that gets all the course list and returns it as a string
     * containing the information of all the courses in a tabular format
     * @return a string containing the information of all the courses
     */
    @Override
    public String toString() {
        try {
            return createTabularFormat(this.getAll());
        } catch (DatabaseOperationException exception) {
            throw new RuntimeException(exception);
        }
    }
}
