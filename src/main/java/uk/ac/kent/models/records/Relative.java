package uk.ac.kent.models.records;

import com.github.javafaker.Faker;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Optional;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(optional = false)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Basic(optional = false)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    public Relative() {}

    /**
     * Make a new {@link Relative}.
     *
     * @param name first name
     * @param surname last name
     */

    public Relative(final String name, final String surname) {
        lastName = surname;
        firstName = name;
    }

    /**
     * @param name first name
     * @param surname last name
     * @param phone phone number
     */

    public Relative(final String name, final String surname, final String phone) {
        this(name, surname);
        phoneNumber = phone;
    }

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
    public Optional<String> getPhoneNumber() {
        return (phoneNumber != null) ? Optional.of(phoneNumber) : Optional
                .empty();
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return a fake {@link Relative}
     */

    public static Relative fake() {
        // fake data generator
        final Faker faker = new Faker(new Locale("en-GB"));

        // @formatter:off
        return new Relative(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().cellPhone());
        // @formatter:on
    }

    // @formatter:off
    @Override
    @SuppressWarnings("ConditionalExpression")
    public final String toString() {
        return MessageFormat.format("Person<name={0} {1}, phone={2}>",
                                    firstName,
                                    lastName,
                                    getPhoneNumber().orElse(""));
    }
    // @formatter:on
}