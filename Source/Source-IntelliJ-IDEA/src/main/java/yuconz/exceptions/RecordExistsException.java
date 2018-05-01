package yuconz.exceptions;

/**
 * Record exists exception.
 */
@SuppressWarnings("CheckedExceptionClass")
public final class RecordExistsException extends ValidationException {

    private static final long serialVersionUID = 5742847490139363768L;

    /**
     * Instantiates a new Record exists exception.
     *
     * @param msg the message
     */
    public RecordExistsException(final String msg) {
        super(msg);
    }
}
