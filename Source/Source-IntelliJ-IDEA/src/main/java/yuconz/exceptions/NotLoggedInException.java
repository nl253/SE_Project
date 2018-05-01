package yuconz.exceptions;

/**
 * Not logged in exception.
 */
@SuppressWarnings({"CheckedExceptionClass", "SerializableHasSerializationMethods"})
public final class NotLoggedInException extends BaseAuthenticationException {

    private static final long serialVersionUID = 3275811516145268897L;

    /**
     * Instantiates a new Not logged in exception.
     *
     * @param msg the message
     */
    public NotLoggedInException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Not logged in exception.
     */
    public NotLoggedInException() {
        super("You need to LOG in first.");
    }
}
