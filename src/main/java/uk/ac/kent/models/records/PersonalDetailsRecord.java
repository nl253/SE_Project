package uk.ac.kent.models.records;

import java.text.MessageFormat;
import java.time.LocalDate;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import uk.ac.kent.models.other.Address;
import uk.ac.kent.models.people.Relative;

/**
 * A {@link BaseRecord} of an {@link uk.ac.kent.models.people.Employee}'s personal details.
 * This is how the system stores personal info about all {@link uk.ac.kent.models.people.Employee}s -
 * not in the {@link uk.ac.kent.models.people.Employee} class but rather in a {@link PersonalDetailsRecord}.
 * <p>
 * This class makes use of (is associated with) {@link Address} and {@link Relative}.
 *
 * @author norbert
 */

@Entity
@Table(name = "personal_details")
@Access(AccessType.FIELD)
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface", "ReturnOfInnerClass", "WeakerAccess"})
public final class PersonalDetailsRecord extends BaseRecord {

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @OneToOne
    private Address address;

    private String email;

    @OneToOne
    private Relative relative;

    /**
     * @param firstName first name ({@link String})
     * @param lastName surname ({@link String})
     * @param email email address ({@link String})
     * @param nextOfKin next of kin ({@link Relative})
     * @param address full address ({@link Address})
     */

    public PersonalDetailsRecord(final String firstName, final String lastName, final String email, final Address address, final Relative nextOfKin) {
        super(LocalDate.now(), LocalDate.now());
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        relative = nextOfKin;
        this.firstName = firstName;
    }

    /**
     * @param firstName first name ({@link String})
     * @param lastName surname ({@link String})
     * @param email email address ({@link String})
     */

    public PersonalDetailsRecord(final String firstName, final String lastName, final String email) {
        this(firstName, lastName, email, null, null);
    }

    /**
     * @param firstName first name ({@link String})
     * @param lastName surname ({@link String})
     */


    public PersonalDetailsRecord(final String firstName, final String lastName) {
        this(firstName, lastName, null);
    }

    /**
     * Empty constructor for Hibernate.
     */

    public PersonalDetailsRecord() {}

    public Relative getRelative() {
        return relative;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setRelative(final Relative nextOfKin) {
        relative = nextOfKin;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @SuppressWarnings({"WeakerAccess", "ConditionalExpression"})
    public String getFullName() {
        // @formatter:off
        return MessageFormat.format("{0} {1}",
                                    (firstName == null) ? "first name not available" : firstName,
                                    (lastName == null) ? "last name not available" : lastName);
        // @formatter:on
    }

    @SuppressWarnings({"DesignForExtension", "ConditionalExpression"})
    @Override
    public String toString() {
        // @formatter:off
        return MessageFormat.format("{0}<firstName={1}, lastName={2}, email={3}, address={4}>",
                                    getClass().getName(),
                                    (firstName == null) ? "not available" : firstName,
                                    (lastName == null) ? "not available" : lastName,
                                    (email == null) ? "not available" : email,
                                    (address == null) ? "not available" : address.toString()
                                   );
        // @formatter:on
    }
}
