package example.app.services;

import example.app.exceptions.DatabaseOperationException;
import example.app.interfaces.DatabaseInterface;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * <h1>Class FileDatabase</h1>
 * This class is used to interact with the files which is used as a database. It's a generic
 * class, so it can be used to do the operations with any type of object.
 * @param <T> the type of the object
 */
public class FileDatabase<T> implements DatabaseInterface<T> {
    /**
     * Takes a key and returns its full path (root/foo) from the database-path.config file
     * If the key is root, it just returns path to root.
     * If root or the key is not present in the database-path.config file, it returns null
     * @param key the key
     * @return the full path associated with the key from the database-path.config file or null
     * @throws IOException if the file could not be read
     */
    String getFullPath(String key) throws IOException {
        HashMap<String, String> entries = DatabaseConfig.getAllEntry();

        if(!entries.containsKey("root") || !entries.containsKey(key))
            return null;

        if(key.equals("root"))
            return entries.get("root");

        return entries.get("root") + "/" + entries.get(key);
    }

    /**
     * This method reads the database partition (whose name was supplied as parameter), and
     * returns a linked list of the objects or null in case the file does not exist. In case
     * the path cannot be retrieved from the config file, or if there's a problem in reading
     * the database file, it throws an DatabaseOperationException.
     * @param fileName name of the partition
     * @return a linked list of objects or null in case the file does not exist
     * @throws DatabaseOperationException in case there's any problem reading the config file or the database file
     */
    @Override
    public LinkedList<T> getAll(String fileName) throws DatabaseOperationException {
        String path = null;

        try {
            path = this.getFullPath(fileName);
            if(path == null)
                throw new NullPointerException();
        }
        catch (NullPointerException | IOException exception) {
            throw new DatabaseOperationException("Cannot get the path of the database file", exception);
        }

        if(!new File(path).exists())
            return null;

        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path))) {
            return (LinkedList<T>) inputStream.readObject();
        }
        catch (IOException | ClassNotFoundException exception) {
            throw new DatabaseOperationException("Error while reading file database", exception);
        }
    }

    /**
     * This method takes a variable number of arguments of a type of object and adds them into the specified database file.
     * In case it encounters a problem with reading the config file or adding operation, it will throw an DatabaseOperationException
     * @param fileName name of the partition
     * @param object the objects to be added
     * @throws DatabaseOperationException in case there's any problem reading the config file or writing to the database file
     */
    @SafeVarargs
    @Override
    public final void addEntry(String fileName, T... object) throws DatabaseOperationException {
        LinkedList<T> list = this.getAll(fileName);

        if(list == null)
            list = new LinkedList<>();

        list.addAll(Arrays.asList(object));

        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(this.getFullPath(fileName)))) {
            outputStream.writeObject(list);
            outputStream.flush();
        }
        catch (IOException exception) {
            throw new DatabaseOperationException("Problem with creating entry in file database", exception);
        }
    }

    /**
     * This method takes a variable number of arguments of a type of object and removes them from the specified database file.
     * In case it encounters a problem with reading the config file or in writing operation, it will throw an DatabaseOperationException
     * @param fileName name of the partition
     * @param object the objects to be removed
     * @throws DatabaseOperationException in case there's any problem reading the config file or in writing to the database file
     */
    @SafeVarargs
    @Override
    public final void removeEntry(String fileName, T... object) throws DatabaseOperationException {
        LinkedList<T> list = this.getAll(fileName);
        LinkedList<T> newList = new LinkedList<>();

        if(list == null) return;

        // Will take O(N^3)
        for(T listEntry : list) {
            boolean isPresent = false;

            for(T value : object) {
                if(listEntry.equals(value)) {
                    isPresent = true;
                    break;
                }
            }

            if(!isPresent)
                newList.add(listEntry);
        }

        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(this.getFullPath(fileName)))) {
            outputStream.writeObject(newList);
            outputStream.flush();
        }
        catch (IOException exception) {
            throw new DatabaseOperationException("Problem with creating entry in file database", exception);
        }
    }

    /**
     * @param fileName name of the partition
     * @param object the object to search for
     * @return true if the object is present at least once in the database, false otherwise
     * @throws DatabaseOperationException in case there's any problem reading the config file or the database file
     */
    @Override
    public boolean isPresent(String fileName, T object) throws DatabaseOperationException {
        return this.getAll(fileName).contains(object);
    }
}
