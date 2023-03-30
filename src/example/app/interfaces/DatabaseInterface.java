package example.app.interfaces;

import example.app.exceptions.DatabaseOperationException;

import java.util.LinkedList;

/**
 * <h1>Interface DatabaseInterface</h1>
 * The DatabaseInterface is used to interact with database. It is used by those classes
 * who want to interact with a database with pre defined ways.
 */
public interface DatabaseInterface<T> {
    /**
     * Takes a list of objects and adds them to the database
     * @param object the objects to be added
     * @throws DatabaseOperationException if any object cannot be added
     */
    void addEntry(String fileName, T... object) throws DatabaseOperationException;

    /**
     * Takes a list of objects and removes them from the database
     * @param object the objects to be removed
     * @throws DatabaseOperationException if any object cannot be removed
     */
    void removeEntry(String fileName, T... object) throws DatabaseOperationException;

    /**
     * @return the list of all objects
     * @throws DatabaseOperationException if object(s) cannot be retrieved
     */
    LinkedList<T> getAll(String fileName) throws DatabaseOperationException;

    /**
     * Checks if an object is present
     * @param object the object
     * @return true if the object is present, else false
     * @throws DatabaseOperationException if object cannot be checked
     */
    boolean isPresent(String fileName, T object) throws DatabaseOperationException;
}
