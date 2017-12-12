package uk.ac.kent.records;


import com.github.javafaker.Faker;
import java.text.MessageFormat;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author norbert
 */

@Entity(name = "PersonalDetailsRecord")
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class PersonalDetailsRecord extends BaseRecord {

    private final String lastname;
    private String address;
    private String email;
    private final String firstname;
    private String nextOfKin;

    /**
     * @param lastname surname
     * @param address full address
     * @param email email address
     * @param nextOfKin full name of relative
     * @param firstname first name
     */

    public PersonalDetailsRecord(final String lastname, final String address, final String email, final String nextOfKin, final String firstname) {
        this.lastname = lastname;
        this.address = address;
        this.email = email;
        this.nextOfKin = nextOfKin;
        this.firstname = firstname;
    }

    /**
     * Empty constructor to create dummy objects.
     */

    public PersonalDetailsRecord() {
        // fake data generator
        final Faker faker = new Faker(new Locale("en-GB"));

        firstname = faker.name().name();
        lastname = faker.name().lastName();
        address = faker.address().fullAddress();
        email = faker.internet().emailAddress();
        nextOfKin = faker.name().fullName();
    }

    public String getNextOfKin() { return nextOfKin; }

    public void setNextOfKin(final String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    String getAddress() { return address; }

    void setAddress(final String address) { this.address = address; }

    String getEmail() { return email; }

    void setEmail(final String email) { this.email = email; }

    String getFirstname() { return firstname; }

    String getLastname() { return lastname; }

    @SuppressWarnings("WeakerAccess")
    @Column(name = "fullname")
    String getFullName() {
        return MessageFormat.format("{0} {1}", firstname, lastname);
    }

    @SuppressWarnings("DesignForExtension")
    @Override
    public String toString() {
        return MessageFormat
                .format("{0}<{1}: {2}>", getClass().getName(), getFullName());
    }
}
