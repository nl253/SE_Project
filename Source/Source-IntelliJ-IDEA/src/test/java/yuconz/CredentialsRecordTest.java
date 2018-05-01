package yuconz;

import java.util.stream.IntStream;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import yuconz.exceptions.StringFormatException;
import yuconz.exceptions.ValidationException;
import yuconz.records.MockDataGenerator;
import yuconz.records.credentials.CredentialsRecord;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Credentials record test.
 *
 * @param <A> the type parameter
 */
@SuppressWarnings({"ConstantConditions", "MessageMissingOnJUnitAssertion", "ObviousNullCheck"})
class CredentialsRecordTest<A> extends MockDataGenerator<A> {

    /**
     * Gets staff number.
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    @Tag("Validation")
    final void getStaffNo() {
        assertNotNull(randCredentialsRecord().getStaffNo());
    }

    /**
     * Negative staff number.
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    @Tag("Validation")
    final void negStaffNo() {
        assertThrows(ValidationException.class, () -> randCredentialsRecord(-randStaffNo()));
    }


    /**
     * Gets password.
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    @Tag("Validation")
    final void getPassword() {
        assertNotNull(randCredentialsRecord().getUsername());
        assertTrue(() -> randCredentialsRecord().getPassword() instanceof String);
    }

    /**
     * Gets username.
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    @Tag("Validation")
    final void getUsername() {
        assertNotNull(randCredentialsRecord().getPassword());
        assertTrue(() -> randCredentialsRecord().getUsername() instanceof String);
    }

    /**
     * Null username.
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    @Tag("Validation")
    final void nullUsername() {
        assertThrows(StringFormatException.class, () -> new CredentialsRecord(randStaffNo(), null, randPassword()));
    }

    /**
     * Empty username.
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    @Tag("Validation")
    final void emptyUsername() {
        assertThrows(StringFormatException.class, () -> new CredentialsRecord(randStaffNo(), "", randPassword()));
    }

    /**
     * Bad username format.
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    @Tag("Validation")
    final void badUsernameFormat() {

        assertThrows(StringFormatException.class, () -> new CredentialsRecord(randStaffNo(), "ex]]mp", randPassword()));
        assertThrows(StringFormatException.class, () -> new CredentialsRecord(randStaffNo(), "easl1mp", randPassword()));
        assertThrows(StringFormatException.class, () -> new CredentialsRecord(randStaffNo(), "sp", randPassword()));
        assertThrows(StringFormatException.class, () -> new CredentialsRecord(randStaffNo(), "123", randPassword()));
        assertThrows(StringFormatException.class, () -> new CredentialsRecord(randStaffNo(), "asldkf212", randPassword()));
        assertThrows(StringFormatException.class, () -> new CredentialsRecord(randStaffNo(), "e1231ad", randPassword()));

        IntStream.range(1, 4)
                 .forEach((int i) -> assertThrows(StringFormatException.class, () -> new CredentialsRecord(randStaffNo(), "emp123".substring(0, i), randPassword())));

        //noinspection StringConcatenationMissingWhitespace
        IntStream.range(1, 6)
                 .forEach((int i) -> assertThrows(StringFormatException.class, () -> new CredentialsRecord(randStaffNo(), "emp123" + "emp123".substring(0, i), randPassword())));
    }

    /**
     * Null password.
     */
    @RepeatedTest(10)
    @Tag("Validation")
    @Tag("Authentication")
    final void nullPassword() {
        assertThrows(StringFormatException.class, () -> new CredentialsRecord(randStaffNo(), randUsername(), null));
    }

    /**
     * Empty password.
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    @Tag("Validation")
    final void emptyPassword() {
        assertThrows(StringFormatException.class, () -> new CredentialsRecord(randStaffNo(), randUsername(), ""));
    }
}
