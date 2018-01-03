package uk.ac.kent.models.records;

import com.github.javafaker.Faker;
import java.text.MessageFormat;
import java.util.Locale;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author norbert
 */

@SuppressWarnings("ProtectedMemberInFinalClass")
@Entity
@Table(name = "addresses")
@Access(AccessType.FIELD)
public final class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String street;
    // @Column(name = "phone_number")
    private String houseNumber;
    private String city;
    // @Column(name = "post_code")
    private String postCode;

    public Address(final String street, final String houseNumber, final String postCode, final String city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.postCode = postCode;
        this.city = city;
    }

    /**
     * Empty constructor for Hibernate.
     */

    public Address() {}

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(final String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(final String postCode) {
        this.postCode = postCode;
    }


    public static Address fake() {

        // fake data generator
        final Faker faker = new Faker(new Locale("en-GB"));

        return new Address(faker.address().streetName(), faker.address()
                .buildingNumber(), faker.address().zipCode(), faker.address()
                                   .city());
    }

    @Override
    public final String toString() {
        return MessageFormat
                .format("Address<street={0}, houseNumber={1}, city={2}, postCode={3}>", street, houseNumber, city, postCode);
    }


    public int getId() {
        return id;
    }
}
