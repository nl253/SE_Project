package uk.ac.kent.people;

import java.text.MessageFormat;
import java.util.Date;

/**
 * @author norbert
 */

@SuppressWarnings({"unused", "PublicMethodNotExposedInInterface", "WeakerAccess", "ParameterHidesMemberVariable", "PublicConstructor", "FieldNotUsedInToString", "InstanceVariableMayNotBeInitialized", "FieldNamingConvention", "ClassWithoutLogger", "UseOfClone", "UseOfObsoleteDateTimeApi"})
public class Employee {

    private final String surname;
    private final String name;
    private final Date dateEmployed;
    private Departament departament;

    public final Date getEmployed() { return (Date) dateEmployed.clone();}

    final Departament getDepartament() { return departament;}

    @SuppressWarnings("ImplicitCallToSuper")
    public Employee(final String surname, final String name, final Date dateEmployed, final Departament departament) {
        this.surname = surname;
        this.name = name;
        this.dateEmployed = (Date) dateEmployed.clone();
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
