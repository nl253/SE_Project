package yuconz.exceptions;

/**
 * Date exception.
 */
@SuppressWarnings("CheckedExceptionClass")
public final class DateException extends ValidationException {

    private static final long serialVersionUID = 4529934494449191174L;

    /**
     * Instantiates a new Date exception.
     *
     * @param msg the message
     */
    public DateException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Date exception.
     */
    @SuppressWarnings("unused")
    public DateException() {
        super("Selected date is invalid.");
    }
}
