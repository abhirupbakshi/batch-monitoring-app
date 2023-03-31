package example.app.exceptions;

/**
 * <h1>Class DuplicatePresentException</h1>
 * This is class extends to the Exception class and the general use case for it to be thrown when an entity already exists
 */
public class DuplicatePresentException extends Exception {
    public DuplicatePresentException() {
        super();
    }

    public DuplicatePresentException(String message) {
        super(message);
    }

    public DuplicatePresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatePresentException(Throwable cause) {
        super(cause);
    }

    protected DuplicatePresentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
