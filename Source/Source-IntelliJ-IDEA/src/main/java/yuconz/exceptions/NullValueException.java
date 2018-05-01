package yuconz.exceptions;

public class NullValueException extends ValidationException {

    /**
     * Instantiates a new Validation exception.
     *
     * @param msg the message
     */
    public NullValueException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Validation exception.
     */
    public NullValueException() {
        super("Null value received.");
    }
}
