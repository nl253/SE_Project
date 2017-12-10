package uk.ac.kent.records;

import javax.persistence.Id;

/**
 * Base record is the superclass for all types of records
 * kept in the system.
 * <p>
 * It equips all subclasses with basic fields and
 * getters / setters. Because it is abstract, it cannot
 * be directly instantiated.
 *
 * @author norbert
 */

@SuppressWarnings({"WeakerAccess", "FieldNamingConvention", "unused", "AbstractClassWithoutAbstractMethods", "AbstractClassNeverImplemented", "ClassWithoutLogger", "ParameterHidesMemberVariable", "InstanceVariableMayNotBeInitialized", "FieldNotUsedInToString", "MethodParameterOfConcreteClass", "MethodReturnOfConcreteClass", "InstanceVariableOfConcreteClass", "DesignForExtension", "ClassHasNoToStringMethod"})
public abstract class BaseRecord {

    private boolean signed;

    @Id
    private int id;

    final int getId() { return id; }

    final boolean isSigned() { return signed; }

    final void setSigned(final boolean signed) { this.signed = signed; }
}
