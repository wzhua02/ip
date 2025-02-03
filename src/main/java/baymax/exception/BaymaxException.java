package baymax.exception;

/**
 * Represents an exception that occurs when there is an error in the baymax package.
 */
public class BaymaxException extends Exception {
    /**
     * Constructs a new BaymaxException with the specified message.
     *
     * @param message the error message detailing the cause of the exception.
     */
    public BaymaxException(String message) {
        super(message);
    }
}
