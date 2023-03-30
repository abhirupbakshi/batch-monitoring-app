package example.app.exceptions;

/**
 * <h1>Class DatabaseOperationException</h1>
 * This exception is used to handle database related exceptions
 */
public class DatabaseOperationException extends Exception {
    public DatabaseOperationException() {
        super();
    }

    public DatabaseOperationException(String message) {
        super(message);
    }

    public DatabaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseOperationException(Throwable cause) {
        super(cause);
    }

    protected DatabaseOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
