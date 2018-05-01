package yuconz.exceptions;

/**
 * String format exception.
 */
@SuppressWarnings({"CheckedExceptionClass", "SerializableHasSerializationMethods", "ClassWithTooManyDependents", "ClassWithTooManyTransitiveDependents"})
public final class StringFormatException extends ValidationException {

    private static final long serialVersionUID = -2972819662653428057L;

    /**
     * Instantiates a new String format exception.
     *
     * @param msg the message
     */
    public StringFormatException(final String msg) {
        super(msg);
    }

    /**
     * Instantiates a new String format exception.
     */
    @SuppressWarnings("unused")
    public StringFormatException() {
        super("Bad string format.");
    }
}
