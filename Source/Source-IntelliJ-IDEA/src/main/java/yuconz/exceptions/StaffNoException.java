package yuconz.exceptions;

/**
 * Staff number exception.
 */
@SuppressWarnings({"CheckedExceptionClass", "ClassWithTooManyDependents"})
public final class StaffNoException extends ValidationException {

    private static final long serialVersionUID = 4975164245850492419L;

    /**
     * Instantiates a new staff number exception.
     *
     * @param msg the message
     */
    public StaffNoException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new staff number exception.
     */
    public StaffNoException() {
        super("Staff numbers cannot be negative.");
    }
}
