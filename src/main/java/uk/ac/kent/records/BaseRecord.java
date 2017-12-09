package uk.ac.kent.records;

import java.text.MessageFormat;
import uk.ac.kent.people.Employee;

/**
 * @author norbert
 */

@SuppressWarnings({"WeakerAccess", "FieldNamingConvention", "unused", "AbstractClassWithoutAbstractMethods", "AbstractClassNeverImplemented", "ClassWithoutLogger", "ParameterHidesMemberVariable", "InstanceVariableMayNotBeInitialized", "FieldNotUsedInToString", "MethodParameterOfConcreteClass", "MethodReturnOfConcreteClass", "InstanceVariableOfConcreteClass"})
abstract class BaseRecord {

    private boolean signed;

    private Employee employee;

    final Employee getEmployee() { return employee;}

    final void setEmployee(final Employee employee) { this.employee = employee;}

    public final boolean isSigned() { return signed;}

    public final void setSigned(final boolean signed) { this.signed = signed;}

    @Override
    public String toString() {
        return MessageFormat.format("BaseRecord<signed={0}>", signed);
    }
}
