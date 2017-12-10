package uk.ac.kent.records;

import java.text.MessageFormat;
import javax.persistence.Id;
import uk.ac.kent.people.Employee;

/**
 * @author norbert
 */

@SuppressWarnings({"WeakerAccess", "FieldNamingConvention", "unused", "AbstractClassWithoutAbstractMethods", "AbstractClassNeverImplemented", "ClassWithoutLogger", "ParameterHidesMemberVariable", "InstanceVariableMayNotBeInitialized", "FieldNotUsedInToString", "MethodParameterOfConcreteClass", "MethodReturnOfConcreteClass", "InstanceVariableOfConcreteClass", "DesignForExtension"})
public abstract class BaseRecord {

    private boolean signed;

    private Employee employee;

    @Id
    private int id;

    final int getId() { return id; }

    final Employee getEmployee() { return employee; }

    final void setEmployee(final Employee employee) { this.employee = employee; }

    final boolean isSigned() { return signed; }

    final void setSigned(final boolean signed) { this.signed = signed; }

    @Override
    public String toString() {
        return MessageFormat.format("BaseRecord<{0}>", id);
    }
}
