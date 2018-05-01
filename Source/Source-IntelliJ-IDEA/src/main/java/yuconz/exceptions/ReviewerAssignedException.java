package yuconz.exceptions;

/**
 * Reviewer assigned exception.
 */
@SuppressWarnings("CheckedExceptionClass")
public final class ReviewerAssignedException extends ValidationException {

    private static final long serialVersionUID = -1939291180699044956L;

    /**
     * Instantiates a new Reviewer assigned exception.
     *
     * @param msg the message
     */
    public ReviewerAssignedException(final String msg) {
        super(msg);
    }

}
