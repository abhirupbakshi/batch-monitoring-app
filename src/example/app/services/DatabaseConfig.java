package example.app.services;

import java.io.*;
import java.util.HashMap;

/**
 * <h1>Class DatabaseConfig</h1>
 * A final class, used to get information about the paths related to database
 * <ul>
 *     <li>root: Path to the database</li>
 *     <li>Every other key is for a specific partition inside database</li>
 * </ul>
 * Example:
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; root="path to root"
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; foo = "path to foo resource"
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp; So absolute path: root/foo
 */
public final class DatabaseConfig {
    /**
     * Creates a new database-path.config file to the present directory
     * @throws IOException if the file could not be created
     */
    static void createNew() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("database-path.config"));

        writer.write("""
                # Each key-value pair is separated by a '='
                # The "root" key is the path to the Database
                # Example: foo="path to it's file" or foo = "path to it's file"
                # Lines starting with # are ignored
                """);

        writer.flush();
        writer.close();
    }

    /**
     * Reads all the entries in the database-path.config file, ignoring
     * lines starting with '#', and returns them as a line terminated string.
     * @return a line terminated string of all entries
     * @throws IOException if the file could not be read
     */
    static String readAll() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("database-path.config"));
        StringBuilder lines = new StringBuilder();

        while(reader.ready()) {
            String temp = reader.readLine().trim();

            if(!temp.startsWith("#"))
                lines.append(temp).append("\n");
        }
        reader.close();

        return lines.toString();
    }

    /**
     * Reads all the entries in the database-path.config file and returns them as a HashMap
     * @return a HashMap of all entries
     * @throws IOException if the file could not be read
     */
    static HashMap<String, String> getAllEntry() throws IOException {
        HashMap<String, String> entries = new HashMap<>();

        String[] lines = readAll().split("\n");
        for(String line : lines) {
            String[] entry = line.split("=");

            if(entry.length == 2 && !entry[0].isEmpty() && !entry[1].isEmpty())
                entries.put(entry[0].trim(), entry[1].trim());
        }

        return entries;
    }

    /**
     * Takes a key and returns its path from the database-path.config file
     * @param key the key
     * @return the path associated with the key in the database-path.config file
     * @throws IOException if the file could not be read
     */
    static String getPath(String key) throws IOException {
        return getAllEntry().get(key);
    }
}
