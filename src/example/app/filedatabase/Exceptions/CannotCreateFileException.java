package example.app.filedatabase.Exceptions;

/**
 * <h1>Class CannotCreateFileException</h1>
 * This class extends Exception class and used when a file could not be created
 */
public class CannotCreateFileException extends Exception {
    public CannotCreateFileException() {
        super();
    }

    public CannotCreateFileException(String message) {
        super(message);
    }

    public CannotCreateFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotCreateFileException(Throwable cause) {
        super(cause);
    }

    protected CannotCreateFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
