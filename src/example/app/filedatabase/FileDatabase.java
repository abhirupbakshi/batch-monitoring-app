package example.app.filedatabase;

import example.app.filedatabase.Exceptions.*;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * <h1>Class FileDatabase</h1>
 * This class is responsible for interacting with the file based database.
 * The structure of the databases as follows:
 * <ul>
 *     <li>Root: The top most directory for the file database, configured in database.config</li>
 *     <li>Partition: Each root directory has sub directories, called partitions</li>
 *     <li>Record File: Each partition has a file called record.ser where all the records for the resources are is stored.
 *     It uses TreeMap to store record in a sorted order</li>
 *     <li>Data Directory: Each partition has a directory called data, which stores all the resources. Each resource is an individual file</li>
 * </ul>
 */
public final class FileDatabase {
    private static int PARTITION_RESOURCES_COUNT = 1_000_000;

    /**
     * This method is used to check if the root directory is present or not, specified by the database.config file.
     * @throws IOException if the database.config could not be read
     * @throws NoSuchFileException if the root directory is not present
     * @throws FileDatabaseConfigFileException if the syntax in the database.config is wrong for the root directory path
     */
    public static void isRootPresent()
            throws IOException, FileDatabaseConfigFileException {
        String rootPath = FileDatabaseConfigFile.rootPath();

        if(!new File(rootPath).exists())
            throw new NoSuchFileException("Root directory at path \"" + rootPath + "\" was not found. ");
    }

    /**
     * This method is used to check if the partition is present or not inside root directory.
     * @param partitionName the name of the partition
     * @throws FileDatabaseConfigFileException if the syntax in the database.config is wrong for root directory path
     * @throws NoSuchFileException if the root or the partition directory is not present
     * @throws IOException if the database.config could not be read
     */
    public static void isPartitionPresent(String partitionName)
            throws FileDatabaseConfigFileException, IOException {
        isRootPresent();

        if(!new File(FileDatabaseConfigFile.rootPath() + "/" + partitionName).exists())
            throw new NoSuchFileException("Partition at path \"" +
                    FileDatabaseConfigFile.rootPath() + "/" +
                    partitionName + "\" was not found. ");
    }

    /**
     * This method is used to check if the record.ser file in a partition is present or not.
     * @param partitionName the name of the partition that needs to be checked
     * @throws FileDatabaseConfigFileException if the syntax in the database.config is wrong for root directory path
     * @throws NoSuchFileException if the root or partition or record.ser partition is not present
     * @throws IOException if the database.config could not be read
     */
    public static void isRecordSerFilePresent(String partitionName)
            throws FileDatabaseConfigFileException,  IOException {
        isPartitionPresent(partitionName);

        if(!new File(FileDatabaseConfigFile.rootPath() + "/" + partitionName + "/record.ser").exists())
            throw new NoSuchFileException("record.ser file at path \"" +
                    FileDatabaseConfigFile.rootPath() + "/" + partitionName +
                    "/record.ser\" was not found. ");
    }

    /**
     * This method is used to check if the data directory in a partition is present or not.
     * @param partitionName the name of the partition that needs to be checked
     * @throws NoSuchFileException if the root or partition or data directory is not present
     * @throws FileDatabaseConfigFileException if the syntax in the database.config is wrong for root directory path
     * @throws IOException if the database.config could not be read
     */
    public static void isDataDirPresent(String partitionName)
            throws FileDatabaseConfigFileException, IOException {
        isPartitionPresent(partitionName);

        if(!new File(FileDatabaseConfigFile.rootPath() + "/" + partitionName + "/data").exists())
            throw new NoSuchFileException("data directory at path \"" +
                    FileDatabaseConfigFile.rootPath() + "/" + partitionName +
                    "/data\" was not found. ");
    }

    /**
     * This method creates the root directory if it does not exist.
     * @throws FileDatabaseConfigFileException if the syntax in the database.config is wrong
     * @throws IOException if the database.config could not be read
     * @throws CannotCreateFileException if the root directory could not be created
     */
    public static void createRoot()
            throws FileDatabaseConfigFileException, IOException, CannotCreateFileException {
        try {
            isRootPresent();
        }
        catch (NoSuchFileException ignored) {
            if(!new File(FileDatabaseConfigFile.rootPath()).mkdir())
                throw new CannotCreateFileException("Cannot create root directory at:" + FileDatabaseConfigFile.rootPath());
        }
    }

    /**
     * This method creates a partition inside the root directory. It also invokes createRoot() method.
     * @param partitionName the name of the partition
     * @throws FileDatabaseConfigFileException if the syntax in the database.config is wrong
     * @throws IOException if the database.config could not be read or if there are any errors creating the record.ser file
     * @throws CannotCreateFileException if the partition directory or the data directory could not be created
     */
    public static void createPartition(String partitionName)
            throws FileDatabaseConfigFileException, IOException, CannotCreateFileException {
        createRoot();

        try {
            isPartitionPresent(partitionName);
        }
        catch (NoSuchFileException exception) {
            if(!new File(FileDatabaseConfigFile.rootPath() + "/" + partitionName).mkdir())
                throw new CannotCreateFileException("Cannot create partition directory at:" +
                        FileDatabaseConfigFile.rootPath() + "/" + partitionName);
        }

        try {
            isDataDirPresent(partitionName);
        }
        catch (NoSuchFileException exception) {
            if(!new File(FileDatabaseConfigFile.rootPath() + "/" + partitionName + "/data").mkdir())
                throw new CannotCreateFileException("Cannot create data directory inside partition directory at:" +
                        FileDatabaseConfigFile.rootPath() + "/" + partitionName + "/data");
        }

        try {
            isRecordSerFilePresent(partitionName);
        }
        catch (NoSuchFileException exception) {
            ObjectOutputStream recordFile = new ObjectOutputStream(
                    new FileOutputStream(FileDatabaseConfigFile.rootPath() + "/" + partitionName + "/record.ser"));

            recordFile.writeObject(new TreeMap<Object, String>());
            recordFile.flush();
            recordFile.close();
        }
    }

    /**
     *This method adds an Object to a partition and updates the record.ser file. If the resource exists, it overwrites it.
     * @param partitionName the name of the partition
     * @param keyForRecord the key to be added for the entry in the record.ser file for the given resource. This will
     *                     be used for sorting.
     * @param uniqueId the value to be added for the key of the given resource in the record.ser file. This is the unique
     *                 identifier of the resource. This is also the name of the file for the resource inside the data directory
     * @param resource the resource to be added
     * @return the updated size of the partition
     * @throws CannotCreateFileException if the partition directory or the data directory could not be created
     * @throws FileDatabaseConfigFileException if the syntax in the database.config is wrong
     * @throws IOException if the database.config or the record.ser could not be read or
     * if there are any errors updating the record.ser file and creating resource file the data directory
     * @throws ClassNotFoundException if the class is not found during deserialization of the record.ser file
     * @throws PartitionOverflowException if the partition is already full
     */
    public static int addResource(String partitionName, Object keyForRecord, String uniqueId, Object resource)
            throws CannotCreateFileException, FileDatabaseConfigFileException, IOException,
            ClassNotFoundException, PartitionOverflowException {
        createPartition(partitionName);

        String fullPartitionPath = FileDatabaseConfigFile.rootPath() + "/" + partitionName;

        // Read the record.ser file
        ObjectInputStream recordFileReader = new ObjectInputStream(
                new FileInputStream(fullPartitionPath + "/record.ser"));
        TreeMap<Object, String> recordMap = (TreeMap<Object, String>) recordFileReader.readObject();
        recordFileReader.close();

        ObjectOutputStream recordFileWriter = new ObjectOutputStream(
                new FileOutputStream(fullPartitionPath + "/record.ser"));
        ObjectOutputStream resourceFileInDataDir = new ObjectOutputStream(
                new FileOutputStream(fullPartitionPath + "/data" + "/" + uniqueId));

        // Check if the partition is already full or not and update the data retrieved from record.ser
        if(recordMap.size() == PARTITION_RESOURCES_COUNT)
            throw new PartitionOverflowException("Partition at path \"" + fullPartitionPath + "\" is already full.");
        recordMap.put(keyForRecord, uniqueId);

        // Write the data to the partition
        recordFileWriter.writeObject(recordMap);
        resourceFileInDataDir.writeObject(resource);

        recordFileWriter.flush();
        resourceFileInDataDir.flush();
        recordFileWriter.close();
        resourceFileInDataDir.close();

        return recordMap.size();
    }

    /**
     * This method retrieves the resource from the file database.
     * @param partitionName the name of the partition
     * @param uniqueId the unique identifier of the resource to be retrieved
     * @return the resource object retrieved from the data directory or null in case it's not present
     * @throws CannotCreateFileException If the partition directory or the data directory could not be created
     * @throws FileDatabaseConfigFileException If the syntax in the database.config is wrong
     * @throws IOException If the database.config could not be read or if there are any errors reading the resource or record.ser file
     * @throws ClassNotFoundException If the class is not found during deserialization of the record.ser file
     * @throws FileDatabaseInternalException If there's a mismatch between the record.ser file and the data directory entries
     */
    public static Object getResource(String partitionName, Object uniqueId)
            throws CannotCreateFileException, FileDatabaseConfigFileException, IOException,
            ClassNotFoundException, FileDatabaseInternalException {
        createPartition(partitionName);

        String fullPartitionPath = FileDatabaseConfigFile.rootPath() + "/" + partitionName;

        // Read the record.ser file
        ObjectInputStream recordFileReader = new ObjectInputStream(
                new FileInputStream(fullPartitionPath + "/record.ser"));
        TreeMap<Object, String> recordMap = (TreeMap<Object, String>) recordFileReader.readObject();
        recordFileReader.close();

        Object resource = null;

        if(recordMap.containsValue(uniqueId)) {
            File resourceFile = new File(fullPartitionPath + "/data" + "/" + uniqueId);

            if(resourceFile.exists()) {
                ObjectInputStream resourceFileInDataDir = new ObjectInputStream(
                        new FileInputStream(fullPartitionPath + "/data" + "/" + uniqueId));
                resource = resourceFileInDataDir.readObject();
                resourceFileInDataDir.close();
            }
            else
                throw new FileDatabaseInternalException("Mismatch is record.src file entries and data directory entries.");
        }

        return resource;
    }

    /**
     * This method retrieves the ids of all the resource from a partition.
     * @param partitionName the name of the partition
     * @return the resource object retrieved from the data directory or null in case it's not present
     * @throws CannotCreateFileException If the partition directory or the data directory could not be created
     * @throws FileDatabaseConfigFileException If the syntax in the database.config is wrong
     * @throws IOException If the database.config could not be read or if there are any errors reading the record.ser file
     * @throws ClassNotFoundException If the class is not found during deserialization of the record.ser file
     */
    public static TreeSet<Object> getIdsOfResources(String partitionName)
            throws CannotCreateFileException, FileDatabaseConfigFileException,
            IOException, ClassNotFoundException {
        createPartition(partitionName);

        String fullPartitionPath = FileDatabaseConfigFile.rootPath() + "/" + partitionName;

        // Read the record.ser file
        ObjectInputStream recordFileReader = new ObjectInputStream(
                new FileInputStream(fullPartitionPath + "/record.ser"));
        TreeMap<Object, String> recordMap = (TreeMap<Object, String>) recordFileReader.readObject();
        recordFileReader.close();

        return new TreeSet<>(recordMap.values());
    }

    /**
     *This method adds an Object to a partition and updates the record.ser file. If the resource exists, it overwrites it.
     * @param partitionName the name of the partition
     * @param uniqueId the unique value of the resource that's used to delete it from the database
     * @throws CannotCreateFileException if the partition directory or the data directory could not be created
     * @throws FileDatabaseConfigFileException if the syntax in the database.config is wrong
     * @throws IOException if the database.config or the record.ser could not be read or
     * if there are any errors updating the record.ser file
     * @throws ClassNotFoundException if the class is not found during deserialization of the record.ser file
     */
    public static void removeResource(String partitionName, String uniqueId)
            throws CannotCreateFileException, FileDatabaseConfigFileException, IOException,
            ClassNotFoundException {
        createPartition(partitionName);

        String fullPartitionPath = FileDatabaseConfigFile.rootPath() + "/" + partitionName;

        // Read the record.ser file
        ObjectInputStream recordFileReader = new ObjectInputStream(
                new FileInputStream(fullPartitionPath + "/record.ser"));
        TreeMap<Object, String> recordMap = (TreeMap<Object, String>) recordFileReader.readObject();
        recordFileReader.close();

        ObjectOutputStream recordFileWriter = new ObjectOutputStream(
                new FileOutputStream(fullPartitionPath + "/record.ser"));
        File resourceFileInDataDir = new File(fullPartitionPath + "/data" + "/" + uniqueId);

        for(Object key : recordMap.keySet()) {
            if(recordMap.get(key).equals(uniqueId)) {
                recordMap.remove(key);
                break;
            }
        }

        resourceFileInDataDir.delete();

        // Write the record.ser data to the file
        recordFileWriter.writeObject(recordMap);
        recordFileWriter.flush();
        recordFileWriter.close();
    }

    /**
     * This method checks if a resource is present in the file database.
     * @param partitionName the name of the partition
     * @param resource The resource to be matched
     * @return true if the resource is present, false otherwise
     * @throws FileDatabaseConfigFileException If the syntax in the database.config is wrong
     * @throws CannotCreateFileException If the partition directory or the data directory could not be created
     * @throws IOException If the database.config could not be read or if there are any errors reading the resource file
     * @throws ClassNotFoundException If the class is not found during deserialization of the record.ser file
     * @throws FileDatabaseInternalException If there's a mismatch between the record.ser file and the data directory entries
     */
    public static boolean isResourcePresent(String partitionName, Object resource)
            throws FileDatabaseConfigFileException, IOException,
            CannotCreateFileException, FileDatabaseInternalException,
            ClassNotFoundException {
        for(Object uniqueId : getIdsOfResources(partitionName)) {
            if(getResource(partitionName, uniqueId).equals(resource))
                return true;
        }

        return false;
    }

    /**
     * This is a private method that deletes a file or directory recursively.
     * @param file  the file or directory to be deleted
     */
    private static void deleteDir(File file) {
        if(!file.exists()) return;

        if(file.isDirectory()) {
            File[] files = file.listFiles();

            assert files != null;
            for (File fileToBeDeleted : files) {
                if (fileToBeDeleted.isDirectory())
                    deleteDir(fileToBeDeleted);
                else
                    fileToBeDeleted.delete();
            }
        }

        file.delete();
    }

    /**
     * This method takes deletes a partition.
     * @param partitionName the name of the partition to be deleted
     * @throws FileDatabaseConfigFileException If the syntax in the database.config is wrong
     * @throws IOException If the database.config could not be read
     */
    public static void removePartition(String partitionName)
            throws FileDatabaseConfigFileException, IOException {
        File partitionDirectory = new File(FileDatabaseConfigFile.rootPath() + "/" + partitionName);
        deleteDir(partitionDirectory);
    }
}
