package yuconz.records.personal;

import java.time.LocalDate;
import yuconz.exceptions.DateException;
import yuconz.exceptions.NullValueException;
import yuconz.exceptions.StaffNoException;
import yuconz.exceptions.StringFormatException;

import static java.text.MessageFormat.format;
import static yuconz.DateValidator.*;
import static yuconz.StringValidator.*;

/**
 * Validating personal details builder.
 */
@SuppressWarnings({"PublicConstructor", "PublicMethodNotExposedInInterface", "ClassWithTooManyDependencies", "StringToUpperCaseOrToLowerCaseWithoutLocale"})
public final class ValidatingPersonalDetailsBuilder extends BasePersonalDetailsBuilder {

    /**
     * Instantiates a new Validating personal details builder.
     *
     * @param staffNo the staff number
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    public ValidatingPersonalDetailsBuilder(final int staffNo) throws StaffNoException {
        super(staffNo);
        if (staffNo < 0) throw new StaffNoException(badFmt("staff number"));
    }

    @Override
    public BasePersonalDetailsBuilder setDateOfBirth(final LocalDate dob) throws DateException, NullValueException {
        if (dob == null)
            throw new NullValueException("Null value for date of birth.");
        if (isTooOld(dob))
            throw new DateException(badFmt("date of birth", "Age is too old."));
        else if (isTooYoung(dob))
            throw new DateException(badFmt("date of birth", "Age is too young."));
        else if (isInTheFuture(dob))
            throw new DateException(badFmt("date of birth", "Date is in the future."));
        return super.setDateOfBirth(dob);
    }

    @Override
    public BasePersonalDetailsBuilder setFirstName(final String firstName) throws StringFormatException, NullValueException {
        if (firstName == null)
            throw new NullValueException("Null value for first name.");
        final String trimmed = firstName.trim();
        if (isValidName(trimmed)) return super.setFirstName(trimmed);
        throw new StringFormatException(badFmt("first name"));
    }

    @Override
    public BasePersonalDetailsBuilder setLastName(final String lastName) throws StringFormatException, NullValueException {
        if (lastName == null)
            throw new NullValueException("Null value for last name.");
        final String trimmed = lastName.trim();
        if (isValidName(trimmed)) return super.setLastName(trimmed);
        throw new StringFormatException(badFmt("last name"));
    }

    @Override
    public BasePersonalDetailsBuilder setCounty(final String county) throws StringFormatException, NullValueException {
        if (county == null)
            throw new NullValueException("Null value for county.");
        final String trimmed = county.trim();
        if (allWordsCap(trimmed)) return super.setCounty(trimmed);
        throw new StringFormatException(badFmt("county"));
    }

    @Override
    public BasePersonalDetailsBuilder setCity(final String city) throws StringFormatException, NullValueException {
        if (city == null) throw new NullValueException("Null value for city.");
        final String trimmed = city.trim();
        if (allWordsCap(trimmed)) return super.setCity(trimmed);
        throw new StringFormatException(badFmt("city"));
    }

    @Override
    public BasePersonalDetailsBuilder setPostcode(final String postcode) throws StringFormatException, NullValueException {
        if (postcode == null)
            throw new NullValueException("Null value for postcode.");
        final String sanitised = sanitiseString(postcode).trim();
        if (isValidPostcode(sanitised))
            // ensure all postcodes are in uppercase
            return super.setPostcode(sanitised.toUpperCase());
        throw new StringFormatException(badFmt("postcode"));
    }

    @Override
    public BasePersonalDetailsBuilder setTelNo(final String telNo) throws StringFormatException, NullValueException {
        if (telNo == null)
            throw new NullValueException("Null value for telephone number.");
        final String sanitised = sanitiseString(telNo).trim();
        if (isValidPhoneNo(telNo)) return super.setTelNo(sanitised);
        throw new StringFormatException(badFmt("telephone number"));
    }

    @Override
    public BasePersonalDetailsBuilder setMobileNo(final String mobileNo) throws StringFormatException, NullValueException {
        if (mobileNo == null)
            throw new NullValueException("Null value for mobile number.");
        // remove whitespaces before saving data
        final String sanitised = sanitiseString(mobileNo).trim();
        if (isValidPhoneNo(sanitised)) return super.setMobileNo(sanitised);
        throw new StringFormatException(badFmt("mobile number: " + mobileNo));
    }

    @Override
    public BasePersonalDetailsBuilder setEmergencyContact(final String contact) throws StringFormatException, NullValueException {
        if (contact == null)
            throw new NullValueException("Null value for emergency contact.");
        final String trimmed = contact.trim();
        if (isValidName(trimmed)) return super.setEmergencyContact(trimmed);
        throw new StringFormatException();
    }

    @Override
    public BasePersonalDetailsBuilder setEmergencyContactNo(final String contactNo) throws StringFormatException, NullValueException {
        if (contactNo == null)
            throw new NullValueException("Null value for phone number.");
        final String sanitised = sanitiseString(contactNo).trim();
        if (isValidPhoneNo(sanitised))
            return super.setEmergencyContactNo(sanitised);
        throw new StringFormatException(badFmt("emergency contact number"));
        // remove whitespaces before saving data
    }

    private String badFmt(final String what) {
        return format("Bad {0} format.", what);
    }

    private String badFmt(final String what, final String details) {
        return format("Bad {0} format. {1}.", what, details);
    }
}
