package uk.ac.kent.people;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author norbert
 */

@SuppressWarnings("PublicMethodNotExposedInInterface")
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    private int id;
    private final String surname;
    private final String name;
    private final LocalDateTime dateEmployed;
    private Department department;

    final LocalDateTime getEmployed() { return dateEmployed; }

    final Department getDepartment() { return department; }

    @SuppressWarnings("ImplicitCallToSuper")
    Employee(final String surname, final String name, final LocalDateTime dateEmployed, final Department department) {
        this.surname = surname;
        this.name = name;
        this.dateEmployed = dateEmployed;
        this.department = department;
    }

    @SuppressWarnings("WeakerAccess")
    @Column(name = "fullname")
    final String getFullName() {
        return MessageFormat.format("{0} {1}", name, surname);
    }

    final void setDepartment(final Department department) {
        this.department = department;
    }

    final int getId() { return id; }

    public final LocalDateTime getDateEmployed() { return dateEmployed;}

    final String getName() { return name; }

    final String getSurname() { return surname; }

    @SuppressWarnings("DesignForExtension")
    @Override
    public final String toString() {
        return MessageFormat.format("{0}<{1}: {2}>", getClass()
                .getName(), id, getFullName());
    }
}
