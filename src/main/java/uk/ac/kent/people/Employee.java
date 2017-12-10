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

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    private int id;
    private final String surname;
    private final String name;
    private final LocalDateTime dateEmployed;
    private Departament departament;

    final LocalDateTime getEmployed() { return dateEmployed; }

    final Departament getDepartament() { return departament; }

    @SuppressWarnings("ImplicitCallToSuper")
    Employee(final String surname, final String name, final LocalDateTime dateEmployed, final Departament departament) {
        this.surname = surname;
        this.name = name;
        this.dateEmployed = dateEmployed;
        this.departament = departament;
    }

    final void setDepartament(final Departament departament) {
        this.departament = departament;
    }

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
                .format("{0}<{1}>", getClass().getName(), getFullName());
    }
}
