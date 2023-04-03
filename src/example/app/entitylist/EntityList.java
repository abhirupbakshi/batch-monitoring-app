package example.app.entitylist;

import com.sun.source.tree.Tree;
import example.app.entitylist.exceptions.DuplicatePresentException;
import example.app.filedatabase.Exceptions.CannotCreateFileException;
import example.app.filedatabase.Exceptions.FileDatabaseConfigFileException;
import example.app.filedatabase.Exceptions.FileDatabaseInternalException;
import example.app.filedatabase.Exceptions.PartitionOverflowException;
import example.app.filedatabase.FileDatabase;
import example.app.entities.FileDatabaseCompatible;

import java.io.IOException;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.UUID;

/**
 * <h1>Class EntityList</h1>
 * This class represents a list of entities. It provides methods for manipulating a list of entities.
 * @param <T> type of the entities / resources that will be stored in the list
 */
public class EntityList<T extends FileDatabaseCompatible> {
    private final String partitionName;

    /**
     * This method is used to add an entity to the list.
     * @param entity the entity to be added
     * @throws CannotCreateFileException
     * @throws FileDatabaseConfigFileException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws PartitionOverflowException
     * @throws FileDatabaseInternalException
     * @throws DuplicatePresentException If the resource already exists
     */
    public void add(T entity)
            throws CannotCreateFileException, FileDatabaseConfigFileException,
            IOException, ClassNotFoundException, PartitionOverflowException,
            FileDatabaseInternalException, DuplicatePresentException {
        if(FileDatabase.isResourcePresent(partitionName, entity))
            throw new DuplicatePresentException("Resource with id " + entity.getId().toString() + " already exists in the partition: " + partitionName);

        FileDatabase.addResource(partitionName, entity.getCreationDate(), entity.getId().toString(), entity);
    }

    /**
     * This method is used to remove an entity from the list.
     * @param entity the entity to be removed
     * @throws CannotCreateFileException
     * @throws FileDatabaseConfigFileException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws FileDatabaseInternalException
     */
    public void remove(T entity)
            throws CannotCreateFileException, FileDatabaseConfigFileException,
            IOException, ClassNotFoundException, FileDatabaseInternalException {
        if(FileDatabase.isResourcePresent(partitionName, entity))
            FileDatabase.removeResource(partitionName, entity.getId().toString());
    }

    /**
     * This method is used to remove all the entities from the list.
     * @throws FileDatabaseConfigFileException If the syntax in the database.config is wrong
     * @throws IOException If the database.config could not be read
     */
    public void removeAll() throws FileDatabaseConfigFileException, IOException {
        FileDatabase.removePartition(partitionName);
    }

    /**
     * This method is used to get an entity from the list.
     * @param id the id of the entity
     * @return the entity with the given id
     * @throws CannotCreateFileException
     * @throws FileDatabaseConfigFileException
     * @throws FileDatabaseInternalException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public T get(UUID id)
            throws CannotCreateFileException, FileDatabaseConfigFileException,
            FileDatabaseInternalException, IOException, ClassNotFoundException {
        return (T) FileDatabase.getResource(partitionName, id.toString());
    }

    /**
     * This method is used to edit an entity in the list.
     * @param newEntity the entity to be edited
     * @throws CannotCreateFileException
     * @throws FileDatabaseConfigFileException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws PartitionOverflowException
     */
    public void edit(T newEntity)
            throws CannotCreateFileException, FileDatabaseConfigFileException,
            IOException, ClassNotFoundException, PartitionOverflowException {
        FileDatabase.addResource(partitionName, newEntity.getCreationDate(), newEntity.getId().toString(), newEntity);
    }

    /**
     * This method returns all the ids of the entities in the partition.
     * @return the ids of the entities
     * @throws CannotCreateFileException
     * @throws FileDatabaseConfigFileException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public TreeSet<UUID> getIds()
            throws CannotCreateFileException, FileDatabaseConfigFileException,
            IOException, ClassNotFoundException {
        TreeSet<UUID> ids = new TreeSet<>();

        for(Object id : FileDatabase.getIdsOfResources(partitionName)) {
            ids.add(UUID.fromString(id.toString()));
        }

        return ids;
    }

    public EntityList(String partitionName) {
        this.partitionName = partitionName;
    }
}
