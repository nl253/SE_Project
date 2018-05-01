package yuconz.exceptions;

/**
 * Already signed exception.
 */
@SuppressWarnings("CheckedExceptionClass")
public final class AlreadySignedException extends ValidationException {

    private static final long serialVersionUID = 587738194166156940L;

    /**
     * Instantiates a new Already signed exception.
     *
     * @param msg the message
     */
    public AlreadySignedException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Already signed exception.
     */
    public AlreadySignedException() {
        super("The review has already been signed.");
    }
}
