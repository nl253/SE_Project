package uk.ac.kent.people;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author norbert
 */

@SuppressWarnings({"unused", "PublicMethodNotExposedInInterface", "WeakerAccess", "ParameterHidesMemberVariable", "PublicConstructor", "FieldNotUsedInToString", "InstanceVariableMayNotBeInitialized", "FieldNamingConvention", "ClassWithoutLogger", "NewClassNamingConvention"})
public class Manager extends Employee {

    public Manager(final String surname, final String name, final LocalDateTime dateEmployed, final Departament departament) {
        super(surname, name, dateEmployed, departament);
    }
}
