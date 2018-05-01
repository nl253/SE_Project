package yuconz.records.personal;

import java.time.LocalDate;
import yuconz.records.Record;

import static java.text.MessageFormat.format;

/**
 * Personal detail record.
 */
@SuppressWarnings({"ClassWithTooManyFields", "ClassNamePrefixedWithPackageName", "WeakerAccess", "PublicField", "ClassWithTooManyDependents", "MethodParameterNamingConvention"})
public final class PersonalDetailRecord implements Record {

    /**
     * the staff number.
     */
    public final int staffNo;
    /**
     * the date of birth.
     */
    public final LocalDate dob;
    /**
     * The First name.
     */
    public final String firstName;
    /**
     * The Last name.
     */
    public final String lastName;
    /**
     * The Addr.
     */
    public final String address;
    /**
     * The County.
     */
    public final String county;
    /**
     * The City.
     */
    public final String city;
    /**
     * The Postcode.
     */
    public final String postcode;
    /**
     * The Tel number.
     */
    public final String telNo;
    /**
     * The Mobile number.
     */
    public final String mobileNo;
    /**
     * the employee contact.
     */
    public final String emergencyContact;
    /**
     * the emergency contact number.
     */
    public final String emergencyContactNo;

    /**
     * Instantiates a new Personal detail record.
     *
     * @param staffNo the staff number
     * @param firstName the first name
     * @param lastName the last name
     * @param dob the date of birth
     * @param address the address
     * @param county the county
     * @param city the city
     * @param postcode the postcode
     * @param telNo the telephone number.
     * @param mobileNo the mobile no
     * @param emergencyContact the employee contact
     * @param emergencyContactNo the employee contact number
     */
    @SuppressWarnings("ConstructorWithTooManyParameters")
    PersonalDetailRecord(final int staffNo, final String firstName, final String lastName, final LocalDate dob, final String address, final String county, final String city, final String postcode, final String telNo, final String mobileNo, final String emergencyContact, final String emergencyContactNo) {
        this.staffNo = staffNo;
        this.dob = dob;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.county = county;
        this.city = city;
        this.postcode = postcode;
        this.telNo = telNo;
        this.mobileNo = mobileNo;
        this.emergencyContact = emergencyContact;
        this.emergencyContactNo = emergencyContactNo;
    }

    @Override
    public String toString() {
        return format("PersonalDetailRecord<staffNo={0}, name={1}>", staffNo, format("{0} {1}", firstName, lastName));
    }

    @Override
    public int getStaffNo() {
        return staffNo;
    }
}
