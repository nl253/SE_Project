package uk.ac.kent.people;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author norbert
 */

@Entity(name = "Employee")
@SuppressWarnings({"unused", "PublicMethodNotExposedInInterface", "WeakerAccess", "ParameterHidesMemberVariable", "PublicConstructor", "FieldNotUsedInToString", "InstanceVariableMayNotBeInitialized", "FieldNamingConvention", "ClassWithoutLogger", "UseOfClone", "UseOfObsoleteDateTimeApi"})
public class Employee {

    @Id
    private int id;
    private final String surname;
    private final String name;
    private final LocalDateTime dateEmployed;
    private Departament departament;

    public final LocalDateTime getEmployed() { return dateEmployed;}

    final Departament getDepartament() { return departament;}

    @SuppressWarnings("ImplicitCallToSuper")
    public Employee(final String surname, final String name, final LocalDateTime dateEmployed, final Departament departament) {
        this.surname = surname;
        this.name = name;
        this.dateEmployed = dateEmployed;
        this.departament = departament;
    }

    final void setDepartament(final Departament departament) {
        this.departament = departament;
    }

    final String getName() { return name;}

    final String getSurname() { return surname;}

    final String fullname() {
        return MessageFormat.format("{0} {1}", name, surname);
    }

    @SuppressWarnings("DesignForExtension")
    @Override
    public final String toString() {
        return MessageFormat
                .format("{0}<{1}>", getClass().getName(), fullname());
    }
}
