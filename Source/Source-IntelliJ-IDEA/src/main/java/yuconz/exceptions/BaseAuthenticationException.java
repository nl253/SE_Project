package yuconz.exceptions;

/**
 * Base authentication exception.
 */
@SuppressWarnings("CheckedExceptionClass")
public abstract class BaseAuthenticationException extends BaseYuconzException {

    // for serialization
    private static final long serialVersionUID = 6102609126159319494L;

    /**
     * Instantiates a new Base authentication exception.
     *
     * @param msg the message
     */
    BaseAuthenticationException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Base authentication exception.
     */
    @SuppressWarnings("unused")
    BaseAuthenticationException() {
        super("An authentication exception has occurred.");
    }
}
