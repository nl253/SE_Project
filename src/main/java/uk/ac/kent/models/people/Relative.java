package uk.ac.kent.models.people;

import java.text.MessageFormat;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import uk.ac.kent.models.records.PersonalDetailsRecord;

/**
 * Used in {@link PersonalDetailsRecord}. A {@link Relative} is the "next of kin"
 * that the company may get in touch with in case something happens.
 *
 * @author norbert
 */

@SuppressWarnings({"InnerClassFieldHidesOuterClassField", "InnerClassMayBeStatic", "NonStaticInnerClassInSecureContext", "PublicMethodNotExposedInInterface"})
@Entity
@Table(name = "relatives")
@Access(AccessType.FIELD)
public final class Relative {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * @param name first name
     * @param surname last name
     * @param phone phone number
     */

    public Relative(final String name, final String surname, final String phone) {
        firstName = name;
        lastName = surname;
        phoneNumber = phone;
    }

    /**
     * @param name first name
     * @param surname last name
     */

    public Relative(final String name, final String surname) {
        this(name, surname, null);
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    public Relative() {}


    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    @SuppressWarnings({"WeakerAccess", "ConditionalExpression"})
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    @Override
    @SuppressWarnings("ConditionalExpression")
    public final String toString() {
        // @formatter:off
        return MessageFormat.format("Person<firstName={0}, lastName={1}, phone={2}>",
                                    (firstName != null) ? firstName : "not available",
                                    (lastName != null) ? lastName : "not available",
                                    (phoneNumber != null) ? phoneNumber : "not available"
                                   );
        // @formatter:on
    }
}
