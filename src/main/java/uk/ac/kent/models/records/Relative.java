package uk.ac.kent.models.records;

import com.github.javafaker.Faker;
import java.text.MessageFormat;
import java.util.Locale;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author norbert
 */

@SuppressWarnings({"InnerClassFieldHidesOuterClassField", "InnerClassMayBeStatic", "NonStaticInnerClassInSecureContext"})
@Entity
@Table(name = "relatives")
@Access(AccessType.FIELD)
public final class Relative {

    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    protected Relative() {}

    /**
     * @return Fake {@link Relative}
     */

    Relative fake() {
        // fake data generator
        final Faker faker = new Faker(new Locale("en-GB"));

        // @formatter:off
        return new Relative(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().cellPhone());
        // @formatter:on
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
