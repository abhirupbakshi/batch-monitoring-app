package example.app.services;

import example.app.constants.DataBasePartitionNames;
import example.app.entities.compartments.Course;
import example.app.exceptions.DatabaseOperationException;

import java.util.LinkedList;

/**
 * <h1>Class Courses</h1>
 * This class is for managing the course list and perform different operations
 * like addition, deletion, updating, searching, etc. It essentially creates an abstraction of the
 * underlying implementation of interacting with the file database.
 */
public final class Courses extends EntityList<Course> {
    /**
     * This method adds an array of courses (varargs) to the database. If the course already exists, it throws an
     * DatabaseOperationException.
     * @param object the courses to be added
     * @throws DatabaseOperationException in case there's any problem adding the course
     */
    public void add(Course... object) throws DatabaseOperationException {
        super.addEntry(DataBasePartitionNames.COURSE.value(), object);
    }

    /**
     * This method removes an array of courses (varargs) from the database.
     * In case it encounters a problem with reading the config file or in writing operation, it will throw an DatabaseOperationException
     * @param object the objects to be removed
     * @throws DatabaseOperationException in case there's any problem reading the config file or in writing to the database file
     */
    public void remove(Course... object) throws DatabaseOperationException {
        super.removeEntry(DataBasePartitionNames.COURSE.value(), object);
    }

    /**
     * This method returns a linked list of the courses. In case the root directory or file does not exist, it creates it.
     * In case the path cannot be retrieved from the config file, or if there's a problem in reading
     * the database file or creating it, it throws an DatabaseOperationException.
     * @return a linked list of courses
     * @throws DatabaseOperationException in case there's any problem reading the config file or the database file
     * or creating the directory or the file
     */
    public LinkedList<Course> getAll() throws DatabaseOperationException {
        return super.getAll(DataBasePartitionNames.COURSE.value());
    }

    /**
     * This method is an overridden toString method that gets all the course list and returns it as a string
     * containing the information of all the courses in a tabular format
     * @return a string containing the information of all the courses
     */
    @Override
    public String toString() {
        StringBuilder list = new StringBuilder();
        try {
            int i = 0;
            for(Course course : this.getAll()) {
                list.append(String.format("%-8s %-14s %-14s\n", i+1, course.getName(), course.getCode()));
                i++;
            }
        } catch (DatabaseOperationException exception) {
            throw new RuntimeException(exception);
        }

        return String.format("%-8s %-14s %-14s\n------------------------------\n", "Sl No.", "Name", "Code") + list;
    }
}
