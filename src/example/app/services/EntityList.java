package example.app.services;

import example.app.exceptions.DatabaseOperationException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * <h1>Class EntityList</h1>
 * This class represents the list of entities. It inherits the methods from the FileDatabase class for interacting
 * with the database and also implements it's own methods that is specific to the entities, like checking for duplicates
 * before adding.
 */
public class EntityList<T> extends FileDatabase<T> {
    /**
     * This method creates the root directory in the database-path.config file. If the directory
     * cannot be created or if it fails to retrieve the path name from config file, it throws an
     * DatabaseOperationException.
     * @throws DatabaseOperationException in case there's any problem reading the config file or creating the directory
     */
    void createRootDir() throws DatabaseOperationException {
        File dir = null;
        try {
            dir = new File(this.getFullPath("root"));
        } catch (IOException exception) {
            throw new DatabaseOperationException("Cannot get root directory's full path", exception);
        }

        if(!dir.exists())
            if(!dir.mkdir())
                throw new DatabaseOperationException("Cannot create directory");
    }

    /**
     * This method reads the database partition (whose name was supplied as parameter), and
     * returns a linked list of the objects. In case the root directory or file does not exist, it creates it.
     * In case the path cannot be retrieved from the config file, or if there's a problem in reading
     * the database file or creating it, it throws an DatabaseOperationException.
     * @param fileName name of the partition
     * @return a linked list of objects
     * @throws DatabaseOperationException in case there's any problem reading the config file or the database file
     * or creating the directory or the file
     */
    @Override
    public LinkedList<T> getAll(String fileName) throws DatabaseOperationException {
        // If the root directory does not exist, create it
        try {
            if(!new File(this.getFullPath("root")).exists())
                this.createRootDir();
        }
        catch (IOException exception) {
            throw new DatabaseOperationException("Cannot create root directory", exception);
        }

        LinkedList<T> list = super.getAll(fileName);

        // If the file does not exist, create it
        if(list == null) {
            list = new LinkedList<>();
            try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(super.getFullPath(fileName)))) {
                outputStream.writeObject(list);
                outputStream.flush();
            }
            catch (IOException exception) {
                throw new DatabaseOperationException("Problem with creating partition in file database", exception);
            }
        }

        return list;
    }

    /**
     * This method checks if the objects already exists in the database or not or duplicates are passed as
     * parameters. If it does, it throws an DatabaseOperationException and does not add them to database.
     * If the partition file does not exist, it creates it.
     * @param fileName the name of the partition
     * @param object the objects to be added
     * @throws DatabaseOperationException if the object already exists or reading and writing from and to the database fails
     */
    @Override
    public void addEntry(String fileName, T... object) throws DatabaseOperationException {
        LinkedList<T> list = super.getAll(fileName);
        HashSet<T> set = new HashSet<>();

        // If the root directory does not exist, create it
        try {
            if(!new File(this.getFullPath("root")).exists())
                this.createRootDir();
        }
        catch (IOException exception) {
            throw new DatabaseOperationException("Cannot create root directory", exception);
        }

        // If the file does not exist, create it
        if(list == null) {
            list = new LinkedList<>();
            try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(super.getFullPath(fileName)))) {
                outputStream.writeObject(list);
                outputStream.flush();
            }
            catch (IOException exception) {
                throw new DatabaseOperationException("Problem with creating partition in file database", exception);
            }
        }

        for(T o : object) {
            if(set.contains(o))
                throw new DatabaseOperationException("Duplicate object passed as a parameter");
            set.add(o);
        }

        for(T o : set) {
            if(list.contains(o))
                throw new DatabaseOperationException("Object already exists in the database");
        }

        super.addEntry(fileName, object);
    }

    /**
     * This method takes a variable number of arguments of a type of object and removes them from the specified database file.
     * It automatically generates the full path of the database file based on the config file.
     * In case it encounters a problem with reading the config file or in writing operation, it will throw an DatabaseOperationException
     * @param fileName name of the partition, without the root directory path
     * @param object the objects to be removed
     * @throws DatabaseOperationException in case there's any problem reading the config file or in writing to the database file
     */
    @Override
    public void removeEntry(String fileName, T... object) throws DatabaseOperationException {
            super.removeEntry(fileName, object);
    }
}
