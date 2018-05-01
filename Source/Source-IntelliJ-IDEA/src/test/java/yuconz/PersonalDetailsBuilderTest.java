package yuconz;

import java.time.LocalDate;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import yuconz.exceptions.DateException;
import yuconz.exceptions.StaffNoException;
import yuconz.exceptions.StringFormatException;
import yuconz.records.MockDataGenerator;
import yuconz.records.personal.ValidatingPersonalDetailsBuilder;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The type Personal details builder test.
 *
 * @param <A> the type parameter
 */
@SuppressWarnings({"MagicNumber", "ImplicitNumericConversion", "StringToUpperCaseOrToLowerCaseWithoutLocale", "ClassWithTooManyMethods", "ClassWithTooManyTransitiveDependencies"})
final class PersonalDetailsBuilderTest<A> extends MockDataGenerator<A> {

    /**
     * Numeric first name.
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    @Tag("Validation")
    void numFirstName() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setFirstName(String.valueOf(getRandom().nextInt()))
                                                                                    .create());
    }

    /**
     * Numeric last name.
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    @Tag("Validation")
    void numLastName() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setLastName(String.valueOf(getRandom().nextInt()))
                                                                                    .create());
    }

    /**
     * Numeric county.
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    @Tag("Validation")
    void numCounty() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setCounty(String.valueOf(randStaffNo()))
                                                                                    .create());
    }

    /**
     * Numeric city.
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    @Tag("Validation")
    void numCity() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setCity(String.valueOf(getRandom().nextInt()))
                                                                                    .create());
    }

    /**
     * Numeric emergency contact.
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    @Tag("Validation")
    void numEmergencyContact() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setEmergencyContact(String.valueOf(getRandom().nextInt()))
                                                                                    .create());
    }

    /**
     * Negative staff number.
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    @Tag("Validation")
    void negStaffNo() {
        assertThrows(StaffNoException.class, () -> new ValidatingPersonalDetailsBuilder(-randStaffNo()).create());
    }

    /**
     * Future birth.
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    @Tag("Validation")
    void futureBirth() {
        assertThrows(DateException.class, () -> randPersonalDetailsBuilder().setDateOfBirth(LocalDate.now()
                                                                                                     .plusYears(getRandom().nextInt(10) + 1))
                                                                            .create());
    }

    /**
     * Too old.
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    @Tag("Validation")
    void tooOld() {
        assertThrows(DateException.class, () -> randPersonalDetailsBuilder().setDateOfBirth(LocalDate.now()
                                                                                                     .minusYears(200))
                                                                            .create());
    }

    /**
     * Underage.
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    @Tag("Validation")
    void underage() {
        assertThrows(DateException.class, () -> randPersonalDetailsBuilder().setDateOfBirth(LocalDate.now()
                                                                                                     .minusYears(getRandom().nextInt(10)))
                                                                            .create());
    }

    /**
     * Capitalised county.
     */
    @RepeatedTest(10)
    void capCounty() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setCounty(randCapWord().toLowerCase())
                                                                                    .create());
    }

    /**
     * Capitalised city.
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    @Tag("Validation")
    void capCity() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setCity(randCapWord().toLowerCase())
                                                                                    .create());
    }

    /**
     * Capitalised first name.
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    @Tag("Validation")
    void capFirstName() {
        assertThrows(StringFormatException.class, () -> new ValidatingPersonalDetailsBuilder(randStaffNo()).setFirstName(randCapWord().toLowerCase())
                                                                                                           .create());
    }

    /**
     * Capitalised last name.
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    @Tag("Validation")
    void capLastName() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setLastName(randCapWord().toLowerCase())
                                                                                    .create());
    }

    /**
     * Capitalised emergency contact.
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    @Tag("Validation")
    void capEmergencyContact() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setEmergencyContact(randCapWord().toLowerCase())
                                                                                    .create());
    }

    /**
     * Short telephone number.
     */
    @Test
    @Tag("PersonalDetails")
    @Tag("Validation")
    void shortTelephoneNumber() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setTelNo("12312")
                                                                                    .create());
    }

    /**
     * Long telephone number.
     */
    @Test
    @Tag("PersonalDetails")
    @Tag("Validation")
    void longTelephoneNumber() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setTelNo("1128381927127332312")
                                                                                    .create());
    }

    /**
     * Alpha telephone number.
     */
    @RepeatedTest(10)
    void alphaTelephoneNumber() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setTelNo(randCapWord())
                                                                                    .create());
    }

    /**
     * Short mobile phone number.
     */
    @Test
    @Tag("PersonalDetails")
    @Tag("Validation")
    void shortMobilePhoneNumber() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setMobileNo("12312")
                                                                                    .create());
    }

    /**
     * Long mobile phone number.
     */
    @Test
    @Tag("PersonalDetails")
    @Tag("Validation")
    void longMobilePhoneNumber() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setMobileNo("1128381927127332312")
                                                                                    .create());
    }

    /**
     * Alpha mobile phone number.
     */
    @RepeatedTest(10)
    void alphaMobilePhoneNumber() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setMobileNo(randCapWord())
                                                                                    .create());
    }

    /**
     * Short emergency contact number.
     */
    @Test
    @Tag("PersonalDetails")
    @Tag("Validation")
    void shortEmergencyContactNumber() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setEmergencyContactNo("12312")
                                                                                    .create());
    }

    /**
     * Long emergency contact number.
     */
    @Test
    @Tag("PersonalDetails")
    @Tag("Validation")
    void longEmergencyContactNumber() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setEmergencyContactNo("1128381927127332312")
                                                                                    .create());
    }

    /**
     * Alpha emergency contact number.
     */
    @RepeatedTest(10)
    void alphaEmergencyContactNumber() {
        assertThrows(StringFormatException.class, () -> randPersonalDetailsBuilder().setEmergencyContactNo(randCapWord())
                                                                                    .create());
    }
}
