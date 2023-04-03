package example.app.filedatabase.Exceptions;

/**
 * <h1>Class FileDatabaseConfigFileException</h1>
 * This class extends the Exception class and used to represent an exception when the database.config file
 * is not configured properly
 */
public class FileDatabaseConfigFileException extends Exception {
    public FileDatabaseConfigFileException() {
        super();
    }

    public FileDatabaseConfigFileException(String message) {
        super(message);
    }

    public FileDatabaseConfigFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileDatabaseConfigFileException(Throwable cause) {
        super(cause);
    }

    protected FileDatabaseConfigFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
