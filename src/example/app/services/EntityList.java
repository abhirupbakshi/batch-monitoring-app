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
                throw new DatabaseOperationException("Problem with creating entry in file database", exception);
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
}
