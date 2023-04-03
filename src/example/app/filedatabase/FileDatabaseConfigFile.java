package example.app.filedatabase;

import example.app.filedatabase.Exceptions.FileDatabaseConfigFileException;

import java.io.*;

/**
 * <h1>Class FileDatabaseConfigFile</h1>
 * This class provides the methods for interacting with the database.config file.
 * <br>
 * Structure of the database.config file:
 * <ul>
 *     <li>Every key and it's value is separated by a '='</li>
 *     <li>Every key value pair is separated by a line terminator</li>
 *     <li>"root" key represents root database directory</li>
 *     <li>"root" key's value represents it's directory path</li>
 *     <li>Lines starting with '#' are ignored</li>
 * </ul>
 */
public final class FileDatabaseConfigFile {
    private static String CONFIG_FILE_NAME = "database.config";

    /**
     * This method creates a new database.config file. The file path is the present directory
     * @throws IOException if the file could not be created
     */
    public static void create() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE_NAME));

        writer.write(
                """
                 # Structure of the database.config file:
                 #   Every key and it's value is separated by a '='
                 #   Every key value pair is separated by a line terminator
                 #   "root" key represents root database directory
                 #   "root" key's value represents it's directory path
                 #   Lines starting with '#' are ignored
                 """
        );

        writer.flush();
        writer.close();
    }

    /**
     * This method returns the path to root directory path from the database.config file.
     * If the root key or it's value not present, null is returned
     * @return the path to root directory path or null
     * @throws IOException if the file could not be read
     */
    public static String rootPath() throws IOException, FileDatabaseConfigFileException {
        BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE_NAME));

        while(reader.ready()) {
            String line = reader.readLine().trim();

            if(!line.startsWith("#")) // Ignoring lines starting with '#'
            {
                String[] keyValue = line.split("=");

                if(keyValue.length == 2 && keyValue[0].trim().equals("root")) {
                    return keyValue[1].trim();
                }
            }
        }

        throw new FileDatabaseConfigFileException("Cannot find root directory path in database.config file");
    }
}
