package yuconz.exceptions;

/**
 * No such record exception.
 */
@SuppressWarnings("CheckedExceptionClass")
public final class NoSuchRecordException extends ValidationException {

    private static final long serialVersionUID = 745707078476815835L;

    /**
     * Instantiates a new No such record exception.
     *
     * @param msg the message
     */
    public NoSuchRecordException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new No such record exception.
     */
    public NoSuchRecordException() {
        super("The record does not exist.");
    }
}
