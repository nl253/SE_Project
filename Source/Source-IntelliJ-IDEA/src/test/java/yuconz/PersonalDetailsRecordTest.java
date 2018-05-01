package yuconz;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import yuconz.exceptions.DateException;
import yuconz.exceptions.NullValueException;
import yuconz.exceptions.StaffNoException;
import yuconz.exceptions.StringFormatException;
import yuconz.records.MockDataGenerator;
import yuconz.records.personal.PersonalDetailRecord;
import yuconz.records.personal.ValidatingPersonalDetailsBuilder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The type Personal details record test.
 *
 * @param <A> the type parameter
 */
@SuppressWarnings({"UnnecessaryBoxing", "ConstantConditions", "MessageMissingOnJUnitAssertion", "ClassUnconnectedToPackage"})
final class PersonalDetailsRecordTest<A> extends MockDataGenerator<A> {

    private PersonalDetailRecord personalDetailRecord;

    /**
     * Sets up.
     *
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws StringFormatException on badly formatted input
     * @throws DateException when the date is invalid
     * @throws NullValueException when a value is null
     */
    @BeforeEach
    void setUp() throws StaffNoException, StringFormatException, DateException, NullValueException {
        // @formatter:off
        personalDetailRecord = new ValidatingPersonalDetailsBuilder(randStaffNo())
            .setDateOfBirth(randDateOfBirth())
            .setFirstName(randCapWord())
            .setLastName(randCapWord())
            .setTelNo(randPhoneNo())
            .setMobileNo(randPhoneNo())
            .setAddress("8 Something Rd")
            .setCounty(randCapWord())
            .setCity(randCapWord())
            .setPostcode("LL28771")
            .setEmergencyContact(String.format("%s %s", randCapWord(), randCapWord()))
            .setEmergencyContactNo(randPhoneNo())
            .create();
            // @formatter:on
    }

    /**
     * Gets staff number.
     */
    @RepeatedTest(10)
    void getStaffNo() {
        assertNotNull(Integer.valueOf(personalDetailRecord.staffNo));
        assertTrue(() -> Integer.valueOf(personalDetailRecord.staffNo) instanceof Integer);
    }

    /**
     * Gets date of birth.
     */
    @RepeatedTest(10)
    void getDateOfBirth() {
        assertNotNull(personalDetailRecord.dob);
        assertTrue(() -> personalDetailRecord.dob instanceof LocalDate);
    }

    /**
     * Gets county.
     */
    @RepeatedTest(10)
    void getCounty() {
        assertNotNull(personalDetailRecord.county);
        assertTrue(() -> personalDetailRecord.county instanceof String);
    }

    /**
     * Gets postcode.
     */
    @Test
    void getPostcode() {
        assertNotNull(personalDetailRecord.postcode);
        assertTrue(() -> personalDetailRecord.postcode instanceof String);
    }

    /**
     * Gets city.
     */
    @RepeatedTest(10)
    void getCity() {
        assertNotNull(personalDetailRecord.city);
        assertTrue(() -> personalDetailRecord.city instanceof String);
    }

    /**
     * Gets telephone number.
     */
    @RepeatedTest(10)
    void getTelNo() {
        assertNotNull(personalDetailRecord.telNo);
        assertTrue(() -> personalDetailRecord.telNo instanceof String);
    }

    /**
     * Gets mobile no.
     */
    @RepeatedTest(10)
    void getMobileNo() {
        assertNotNull(personalDetailRecord.mobileNo);
        assertTrue(() -> personalDetailRecord.mobileNo instanceof String);
    }

    /**
     * Gets emergency contact.
     */
    @Test
    void getEmergencyContact() {
        assertNotNull(personalDetailRecord.emergencyContact);
        assertTrue(() -> personalDetailRecord.emergencyContact instanceof String);
    }

    /**
     * Gets emergency contact no.
     */
    @RepeatedTest(10)
    void getEmergencyContactNo() {
        assertNotNull(personalDetailRecord.emergencyContactNo);
        assertTrue(() -> personalDetailRecord.emergencyContactNo instanceof String);
    }

    /**
     * Gets address.
     */
    @Test
    void getAddress() {
        assertNotNull(personalDetailRecord.address);
        assertTrue(() -> personalDetailRecord.address instanceof String);
    }

    /**
     * Gets last name.
     */
    @RepeatedTest(10)
    void getLastName() {
        assertNotNull(personalDetailRecord.lastName);
        assertTrue(() -> personalDetailRecord.lastName instanceof String);
    }

    /**
     * Gets first name.
     */
    @RepeatedTest(10)
    void getFirstName() {
        assertNotNull(personalDetailRecord.firstName);
        assertTrue(() -> personalDetailRecord.firstName instanceof String);
    }
}
