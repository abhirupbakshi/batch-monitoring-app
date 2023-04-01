package example.app.services;

import example.app.constants.DataBasePartitionNames;
import example.app.entities.compartments.Faculty;
import example.app.exceptions.DatabaseOperationException;

import java.util.LinkedList;

/**
 * <h1>Class Faculties</h1>
 * This class is for managing the faculty list and perform different operations
 * like addition, deletion, updating, searching, etc. It essentially creates an abstraction of the
 * underlying implementation of interacting with the file database.
 */
public class Faculties extends EntityList<Faculty> {
    /**
     * This method adds an array of faculties (varargs) to the database. If the course already exists, it throws an
     * DatabaseOperationException.
     * @param object the faculties to be added
     * @throws DatabaseOperationException in case there's any problem adding the faculty
     */
    public void add(Faculty... object) throws DatabaseOperationException {
        super.addEntry(DataBasePartitionNames.FACULTY.value(), object);
    }

    /**
     * This method removes an array of faculties (varargs) from the database.
     * In case it encounters a problem with reading the config file or in writing operation, it will throw an DatabaseOperationException
     * @param object the faculties to be removed
     * @throws DatabaseOperationException in case there's any problem reading the config file or in writing to the database file
     */
    public void remove(Faculty... object) throws DatabaseOperationException {
        super.removeEntry(DataBasePartitionNames.FACULTY.value(), object);
    }

    /**
     * This method returns a linked list of the faculties. In case the root directory or file does not exist, it creates it.
     * In case the path cannot be retrieved from the config file, or if there's a problem in reading
     * the database file or creating it, it throws an DatabaseOperationException.
     * @return a linked list of faculties
     * @throws DatabaseOperationException in case there's any problem reading the config file or the database file
     * or creating the directory or the file
     */
    public LinkedList<Faculty> getAll() throws DatabaseOperationException {
        return super.getAll(DataBasePartitionNames.FACULTY.value());
    }

    /**
     * This method takes a linked list of faculties and returns a string containing the information of all the faculties
     * in a tabular format
     * @param facultyList a linked list of faculties
     * @return a string containing the information of all the faculties in a tabular format
     */
    public String createTabularFormat(LinkedList<Faculty> facultyList) {
        StringBuilder list = new StringBuilder();
        String formatString = "%-8s %-14s %-14s %-14s %-14s\n";
        int i = 0;

        for(Faculty faculty : facultyList) {
            list.append(String.format(formatString,
                    i+1,
                    faculty.getFacultyCreationDate(),
                    faculty.getName(),
                    faculty.getCode(),
                    faculty.getFacultyUser().getFirstName() + " " + faculty.getFacultyUser().getLastName()
            ));

            i++;
        }

        return String.format(formatString +
                        "------------------------------------" +
                        "---------------------------------\n",
                "Sl No.", "Creation Date", "Name", "Faculty Code", "Assigned User") + list;
    }

    /**
     * This method is an overridden toString method that gets all the faculty list and returns it as a string
     * containing the information of all the faculties in a tabular format
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
