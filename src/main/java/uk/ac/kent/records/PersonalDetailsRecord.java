package uk.ac.kent.records;


import java.text.MessageFormat;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author norbert
 */

@Entity(name = "PersonalDetailsRecord")
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class PersonalDetailsRecord {

    private final String surname;
    private String address;
    private String email;
    private final String name;
    private String nextOfKin;

    public PersonalDetailsRecord(final String surname, final String address, final String email, final String nextOfKin, final String name) {
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.nextOfKin = nextOfKin;
        this.name = name;
    }


    public String getNextOfKin() { return nextOfKin; }

    public void setNextOfKin(final String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    String getAddress() { return address; }

    void setAddress(final String address) { this.address = address; }

    String getEmail() { return email; }

    void setEmail(final String email) { this.email = email; }

    String getName() { return name; }

    String getSurname() { return surname; }

    @SuppressWarnings("WeakerAccess")
    @Column(name = "fullname")
    String getFullName() {
        return MessageFormat.format("{0} {1}", name, surname);
    }

    @SuppressWarnings("DesignForExtension")
    @Override
    public String toString() {
        return MessageFormat
                .format("{0}<{1}: {2}>", getClass().getName(), getFullName());
    }

}
