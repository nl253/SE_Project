package uk.ac.kent.models.records;

import com.github.javafaker.Faker;
import java.text.MessageFormat;
import java.util.Locale;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author norbert
 */

@Entity
@Table(name = "personal_details")
@Access(AccessType.FIELD)
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface", "ReturnOfInnerClass", "WeakerAccess"})
public final class PersonalDetailsRecord extends BaseRecord {

    @Basic(optional = false)
    // @Column(name = "last_name")
    private String lastName;

    @Basic(optional = false)
    // @Column(name = "first_name")
    private String firstName;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, targetEntity = Address.class)
    private Address address;

    private String email;

    @OneToOne(targetEntity = Relative.class, orphanRemoval = true, cascade = CascadeType.ALL)
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

    public static PersonalDetailsRecord fake() {
        // fake data generator
        final Faker faker = new Faker(new Locale("en-GB"));

        // @formatter:off
        return new PersonalDetailsRecord(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                Address.fake(),
                new Relative(
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.phoneNumber().cellPhone()));
        // @formatter:on
    }

    public Relative getRelative() {
        return relative;
    }

    public void setRelative(final Relative nextOfKin) {
        relative = nextOfKin;
    }

    Address getAddress() {
        return address;
    }

    void setAddress(final Address address) {
        this.address = address;
    }

    String getEmail() {
        return email;
    }

    void setEmail(final String email) {
        this.email = email;
    }

    String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }

    @SuppressWarnings("WeakerAccess")
        // @Column(name = "fullname")
    String getFullName() {
        return MessageFormat.format("{0} {1}", firstName, lastName);
    }

    @SuppressWarnings("DesignForExtension")
    @Override
    public String toString() {
        return MessageFormat.format("{0}<{1}, {2}>", getClass()
                .getName(), firstName, lastName);
    }
}
