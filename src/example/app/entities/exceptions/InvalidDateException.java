package example.app.entities.exceptions;

public class InvalidDateException extends Exception {
    public InvalidDateException() {
        super();
    }

    public InvalidDateException(String message) {
        super(message);
    }

    public InvalidDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDateException(Throwable cause) {
        super(cause);
    }

    protected InvalidDateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
