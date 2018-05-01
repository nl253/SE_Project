package yuconz.exceptions;

/**
 * Authorisation exception.
 */
@SuppressWarnings("CheckedExceptionClass")
public final class AuthorisationException extends BaseYuconzException {

    private static final long serialVersionUID = 6279413160574065115L;

    /**
     * Instantiates a new Authorisation exception.
     */
    public AuthorisationException() {
        super("Insufficient privileges.");
    }

    /**
     * Instantiates a new Authorisation exception.
     *
     * @param msg the message
     */
    @SuppressWarnings("unused")
    public AuthorisationException(final String msg) {
        super(msg);
    }
}
