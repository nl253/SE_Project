package uk.ac.kent.models.records;

import com.github.javafaker.Faker;
import java.text.MessageFormat;
import java.util.Locale;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author norbert
 */

@Entity
@Table(name = "personal_details")
@Access(AccessType.FIELD)
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface", "ReturnOfInnerClass"})
public final class PersonalDetailsRecord extends BaseRecord {

    /**
     * @author norbert
     */

    @SuppressWarnings({"InnerClassFieldHidesOuterClassField", "InnerClassMayBeStatic", "NonStaticInnerClassInSecureContext"})
    @Entity
    @Table(name = "relative")
    @Access(AccessType.FIELD)
    private static final class Relative {

        private String lastName;
        private String firstName;

        @Id
        private Integer id;

        private String phoneNumber;

        @SuppressWarnings("PublicConstructorInNonPublicClass")
        public Relative() {}

        static Relative fake() {

            // fake data generator
            final Faker faker = new Faker(new Locale("en-GB"));

            return new Relative(faker.name().firstName(), faker.name()
                    .lastName(), faker.phoneNumber().cellPhone());
        }

        Relative(final String name, final String surname) {
            lastName = surname;
            firstName = name;
        }

        Relative(final String name, final String surname, final String phone) {
            this(name, surname);
            phoneNumber = phone;
        }

        // @formatter:off
        @Override
        @SuppressWarnings("ConditionalExpression")
        public final String toString() {
            return MessageFormat
                    .format("Person<lastname={0}, firstname={1}, phone={2}>",
                            lastName, firstName,
                            (phoneNumber != null) ? phoneNumber : "");
        }
        // @formatter:on
    }

    private final String lastname;
    private final String firstname;
    private String address;
    private String email;
    @OneToOne(targetEntity = Relative.class)
    private Relative nextOfKin;

    /**
     * @param lastname surname
     * @param address full address
     * @param email email address
     * @param nextOfKin full name of relative
     * @param firstname first name
     */

    public PersonalDetailsRecord(final String lastname, final String address, final String email, final Relative nextOfKin, final String firstname) {
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
        nextOfKin = new Relative(faker.name().firstName(), faker.name()
                .lastName(), faker.phoneNumber().cellPhone());
    }

    public Relative getNextOfKin() { return nextOfKin; }

    public void setNextOfKin(final Relative nextOfKin) {
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