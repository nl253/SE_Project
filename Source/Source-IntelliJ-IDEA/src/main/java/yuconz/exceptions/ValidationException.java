package yuconz.exceptions;

/**
 * Validation exception.
 */
@SuppressWarnings("CheckedExceptionClass")
public class ValidationException extends BaseYuconzException {

    private static final long serialVersionUID = -1362257575168573506L;

    /**
     * Instantiates a new Validation exception.
     *
     * @param msg the message
     */
    public ValidationException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Validation exception.
     */
    public ValidationException() {
        super("Validation error.");
    }
}
