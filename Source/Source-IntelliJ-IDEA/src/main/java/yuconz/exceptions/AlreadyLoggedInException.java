package yuconz.exceptions;

/**
 * Already logged in exception.
 */
@SuppressWarnings("CheckedExceptionClass")
public final class AlreadyLoggedInException extends BaseAuthenticationException {

    private static final long serialVersionUID = 3769290987671540516L;

    /**
     * Instantiates a new Already logged in exception.
     *
     * @param msg the message
     */
    public AlreadyLoggedInException(final String msg) {
        super(msg);
    }
}
