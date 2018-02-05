package uk.ac.kent.models.other;

import java.text.MessageFormat;

/**
 * @author Norbert
 */

@SuppressWarnings({"ProtectedMemberInFinalClass", "PublicMethodNotExposedInInterface", "WeakerAccess"})
public final class Address {

    private String street, houseNumber, city, postCode;

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
