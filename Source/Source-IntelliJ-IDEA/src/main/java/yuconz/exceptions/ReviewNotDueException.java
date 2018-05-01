package yuconz.exceptions;

/**
 * Review not due exception.
 */
public final class ReviewNotDueException extends BaseYuconzException {

    private static final long serialVersionUID = 3813771958872580936L;

    /**
     * Instantiates a new Review not due exception.
     *
     * @param msg the message
     */
    public ReviewNotDueException(final String msg) {
        super(msg);
    }

}
