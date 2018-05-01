package yuconz.exceptions;

/**
 * Reviewers not assigned exception.
 */
@SuppressWarnings("CheckedExceptionClass")
public final class ReviewersNotAssignedException extends ValidationException {

    private static final long serialVersionUID = 6498911208903165856L;

    /**
     * Instantiates a new Reviewers not assigned exception.
     */
    public ReviewersNotAssignedException() {
        super("Reviewers have not been assigned.");
    }
}
