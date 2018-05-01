package yuconz.records.personal;

import java.time.LocalDate;
import java.util.stream.Stream;
import yuconz.exceptions.DateException;
import yuconz.exceptions.NullValueException;
import yuconz.exceptions.StringFormatException;

import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.joining;

/**
 * Base personal details builder.
 */
@SuppressWarnings({"DesignForExtension", "ClassWithTooManyFields", "PublicMethodNotExposedInInterface"})
public abstract class BasePersonalDetailsBuilder {

    private final int staffNo;
    private LocalDate dateOfBirth;
    private String firstName;
    private String lastName;
    private String address;
    private String county;
    private String city;
    private String postcode;
    private String telNo;
    private String mobileNo;
    private String emergencyContact;
    private String emergencyContactNo;

    /**
     * Instantiates a new Base personal details builder.
     *
     * @param staffNo the staff number
     */
    BasePersonalDetailsBuilder(final int staffNo) {
        this.staffNo = staffNo;
    }

    /**
     * Sets dateOfBirth.
     *
     * @param dateOfBirth the date of birth
     * @return the date of birth
     *
     * @throws DateException when the date is invalid
     */
    public BasePersonalDetailsBuilder setDateOfBirth(final LocalDate dateOfBirth) throws DateException, NullValueException {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     * @return the first name
     *
     * @throws StringFormatException on badly formatted input
     */
    public BasePersonalDetailsBuilder setFirstName(final String firstName) throws StringFormatException, NullValueException {
        this.firstName = firstName;
        return this;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     * @return the last name
     *
     * @throws StringFormatException on badly formatted input
     */
    public BasePersonalDetailsBuilder setLastName(final String lastName) throws StringFormatException, NullValueException {
        this.lastName = lastName;
        return this;
    }

    /**
     * Sets address.
     *
     * @param address the address
     * @return the address
     */
    public final BasePersonalDetailsBuilder setAddress(final String address) {
        this.address = address;
        return this;
    }

    /**
     * Sets county.
     *
     * @param county the county
     * @return the county
     *
     * @throws StringFormatException on badly formatted input
     */
    public BasePersonalDetailsBuilder setCounty(final String county) throws StringFormatException, NullValueException {
        this.county = county;
        return this;
    }

    /**
     * Sets city.
     *
     * @param city the city
     * @return the city
     *
     * @throws StringFormatException on badly formatted input
     */
    public BasePersonalDetailsBuilder setCity(final String city) throws StringFormatException, NullValueException {
        this.city = city;
        return this;
    }

    /**
     * Sets postcode.
     *
     * @param postcode the postcode
     * @return the postcode
     *
     * @throws StringFormatException on badly formatted input
     */
    public BasePersonalDetailsBuilder setPostcode(final String postcode) throws StringFormatException, NullValueException {
        this.postcode = postcode;
        return this;
    }

    /**
     * Sets tel number.
     *
     * @param telNo the telephone number.
     * @return the telephone number.
     *
     * @throws StringFormatException on badly formatted input
     */
    public BasePersonalDetailsBuilder setTelNo(final String telNo) throws StringFormatException, NullValueException {
        this.telNo = telNo;
        return this;
    }

    /**
     * Sets mobile number.
     *
     * @param mobileNo the mobile no
     * @return the mobile no
     *
     * @throws StringFormatException on badly formatted input
     */
    public BasePersonalDetailsBuilder setMobileNo(final String mobileNo) throws StringFormatException, NullValueException {
        this.mobileNo = mobileNo;
        return this;
    }

    /**
     * Sets emergency contact.
     *
     * @param contact the contact
     * @return the emergency contact
     *
     * @throws StringFormatException on badly formatted input
     */
    public BasePersonalDetailsBuilder setEmergencyContact(final String contact) throws StringFormatException, NullValueException {
        emergencyContact = contact;
        return this;
    }

    /**
     * Sets emergency contact number.
     *
     * @param contactNo the phone no
     * @return the emergency contact no
     *
     * @throws StringFormatException on badly formatted input
     */
    public BasePersonalDetailsBuilder setEmergencyContactNo(final String contactNo) throws StringFormatException, NullValueException {
        emergencyContactNo = contactNo;
        return this;
    }

    /**
     * Create personal detail record.
     *
     * @return the personal detail record
     */
    public final PersonalDetailRecord create() {
        return new PersonalDetailRecord(staffNo, firstName, lastName, dateOfBirth, address, county, city, postcode, telNo, mobileNo, emergencyContact, emergencyContactNo);
    }

    @Override
    public final String toString() {
        return format("{0}<{1}>", getClass().getSimpleName(), Stream.of(firstName, lastName, dateOfBirth)
                                                                    .map(Object::toString)
                                                                    .collect(joining()));
    }
}
