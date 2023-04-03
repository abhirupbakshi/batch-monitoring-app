package example.app.filedatabase.Exceptions;

/**
 * <h1>Class FileDatabaseInternalException</h1>
 * This class extends the Exception class and used to represent exceptions that caused by internal logic
 * of the FileDatabase class.
 */
public class FileDatabaseInternalException extends Exception {
    public FileDatabaseInternalException() {
        super();
    }

    public FileDatabaseInternalException(String message) {
        super(message);
    }

    public FileDatabaseInternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileDatabaseInternalException(Throwable cause) {
        super(cause);
    }

    protected FileDatabaseInternalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
