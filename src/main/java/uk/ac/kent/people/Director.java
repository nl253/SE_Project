package uk.ac.kent.people;

import java.time.LocalDateTime;

/**
 * @author norbert
 */

@SuppressWarnings({"unused", "PublicMethodNotExposedInInterface", "WeakerAccess", "ParameterHidesMemberVariable", "PublicConstructor", "FieldNotUsedInToString", "InstanceVariableMayNotBeInitialized", "FieldNamingConvention", "ClassWithoutLogger", "NewClassNamingConvention", "ClassTooDeepInInheritanceTree"})
public class Director extends Manager {

    public Director(final String surname, final String name, final LocalDateTime dateEmployed, final Departament departament) {
        super(surname, name, dateEmployed, departament);
    }
}
