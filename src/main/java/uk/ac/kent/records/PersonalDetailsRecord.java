package uk.ac.kent.records;


import java.text.MessageFormat;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author norbert
 */

@Entity(name = "PersonalDetailsRecord")
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor"})
public class PersonalDetailsRecord {


    private final String surname;
    private String address;
    private String email;
    private final String name;

    public PersonalDetailsRecord(final String surname, final String name) {
        this.surname = surname;
        this.name = name;
    }

    final String getAddress() { return address; }

    final void setAddress(final String address) { this.address = address; }

    final String getEmail() { return email; }

    final void setEmail(final String email) { this.email = email; }

    final String getName() { return name; }

    final String getSurname() { return surname; }

    @SuppressWarnings("WeakerAccess")
    @Column(name = "fullname")
    final String getFullName() {
        return MessageFormat.format("{0} {1}", name, surname);
    }

    @SuppressWarnings("DesignForExtension")
    @Override
    public final String toString() {
        return MessageFormat
                .format("{0}<{1}: {2}>", getClass().getName(), getFullName());
    }

}
