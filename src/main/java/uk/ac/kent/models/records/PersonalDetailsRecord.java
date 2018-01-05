package uk.ac.kent.models.records;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import java.text.MessageFormat;
import java.util.Locale;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
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

    @OneToOne(targetEntity = Address.class)
    private Address address;

    private String email;

    @OneToOne(targetEntity = Relative.class)
    private Relative relative;

    /**
     * @param lastName surname
     * @param address full address
     * @param email email address
     * @param nextOfKin full name of relative
     * @param firstName first name
     */

    public PersonalDetailsRecord(final String firstName, final String lastName, final String email, final Address address, final Relative nextOfKin) {
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        relative = nextOfKin;
        this.firstName = firstName;
    }

    public PersonalDetailsRecord() {}

    /**
     * @return a fake {@link PersonalDetailsRecord}
     */

    public static PersonalDetailsRecord fake() {
        // fake data generator
        final Faker faker = new Faker(new Locale("en-GB"));
        final Name nameFaker = faker.name();

        // @formatter:off
        return new PersonalDetailsRecord(
                nameFaker.firstName(),
                nameFaker.lastName(),
                faker.internet().emailAddress(),
                Address.fake(),
                new Relative(
                        nameFaker.firstName(),
                        nameFaker.lastName(),
                        faker.phoneNumber().cellPhone()));
        // @formatter:on
    }

    public Relative getRelative() {
        return relative;
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

    @SuppressWarnings("WeakerAccess")
    // @Column(name = "fullname")
    public String getFullName() {
        return MessageFormat.format("{0} {1}", firstName, lastName);
    }

    @SuppressWarnings("DesignForExtension")
    @Override
    public String toString() {
        return MessageFormat.format("{0}<{1}, {2}>", getClass()
                .getName(), firstName, lastName);
    }
}
