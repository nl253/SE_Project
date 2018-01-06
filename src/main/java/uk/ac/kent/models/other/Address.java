package uk.ac.kent.models.other;

import java.text.MessageFormat;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import uk.ac.kent.models.records.PersonalDetailsRecord;

/**
 * Used as a field of {@link PersonalDetailsRecord}.
 *
 * @author norbert
 */

@SuppressWarnings({"ProtectedMemberInFinalClass", "PublicMethodNotExposedInInterface", "WeakerAccess"})
@Entity
@Table(name = "addresses")
@Access(AccessType.FIELD)
public final class Address {

    @Id
    @GeneratedValue
    private int id;

    private String street;

    @Column(name = "phone_number")
    private String houseNumber;

    private String city;

    @Column(name = "post_code")
    private String postCode;

    /**
     * @param street street name
     * @param houseNumber house number
     * @param postCode post (zip) code
     * @param city city
     */

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

    public void setId(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

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

    @SuppressWarnings({"MethodWithMoreThanThreeNegations", "ConditionalExpression"})
    @Override
    public final String toString() {
        // @formatter:off
        return MessageFormat
                .format("Address<street={0}, houseNumber={1}, city={2}, postCode={3}>",
                        (street != null)      ? street      : "not available",
                        (houseNumber != null) ? houseNumber : "not available",
                        (city != null)        ? city        : "not available",
                        (postCode != null)    ? postCode    : "not available");
        // @formatter:on
    }
}
