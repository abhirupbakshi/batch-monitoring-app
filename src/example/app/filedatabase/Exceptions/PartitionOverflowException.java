package example.app.filedatabase.Exceptions;

/**
 * <h1>Class PartitionOverflowException</h1>
 * This class extends Exception class and is used to represent a partition overflow exception
 */
public class PartitionOverflowException extends Exception {
    public PartitionOverflowException() {
        super();
    }

    public PartitionOverflowException(String message) {
        super(message);
    }

    public PartitionOverflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public PartitionOverflowException(Throwable cause) {
        super(cause);
    }

    protected PartitionOverflowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
