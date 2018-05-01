package yuconz.exceptions;

/**
 * Reviewers not diff exception.
 */
@SuppressWarnings({"CheckedExceptionClass", "SerializableHasSerializationMethods"})
public final class ReviewersNotDiffException extends ValidationException {

    private static final long serialVersionUID = 2361450727108931528L;

    /**
     * Instantiates a new Reviewers not diff exception.
     *
     * @param msg the message
     */
    public ReviewersNotDiffException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Reviewers not diff exception.
     */
    public ReviewersNotDiffException() {
        super("Reviewers must be different.");
    }
}
