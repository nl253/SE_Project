package yuconz.exceptions;

/**
 * Base yuconz exception.
 */
@SuppressWarnings({"AbstractClassExtendsConcreteClass", "CheckedExceptionClass", "ClassUnconnectedToPackage", "ClassOnlyUsedInOnePackage", "ClassWithTooManyTransitiveDependents"})
public abstract class BaseYuconzException extends Exception {

    private static final long serialVersionUID = 6493650153173594211L;

    /**
     * Instantiates a new Base yuconz exception.
     *
     * @param msg the message
     */
    BaseYuconzException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new Base yuconz exception.
     */
    @SuppressWarnings("unused")
    BaseYuconzException() {
        super("An unexpected exception has occurred.");
    }
}
