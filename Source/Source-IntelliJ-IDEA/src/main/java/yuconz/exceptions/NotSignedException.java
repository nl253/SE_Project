package yuconz.exceptions;

/**
 * Not signed exception.
 */
@SuppressWarnings("CheckedExceptionClass")
public final class NotSignedException extends ValidationException {

    private static final long serialVersionUID = 1759046696725142776L;

    /**
     * Instantiates a new Not signed exception.
     */
    public NotSignedException() {
        super("The review is not signed.");
    }

}
