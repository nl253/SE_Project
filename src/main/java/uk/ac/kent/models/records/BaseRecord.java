package uk.ac.kent.models.records;

import java.text.MessageFormat;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Transient;

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

@SuppressWarnings("ClassHasNoToStringMethod")
@MappedSuperclass
@Table(name = "records")
@Access(AccessType.FIELD)
abstract class BaseRecord {

    private boolean signed;

    @SuppressWarnings({"WeakerAccess", "ProtectedField"})
    @Id
    protected final int id;

    @Transient
    private static int nextId;

    @SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod")
    BaseRecord() {
        id = nextId;
        nextId++;
    }

    final int getId() { return id; }

    final boolean isSigned() { return signed; }

    final void sign() { signed = true; }

    final void unsign() { signed = false; }

    @SuppressWarnings("DesignForExtension")
    @Override
    public String toString() {
        return MessageFormat.format("{0}<id={1} signed={2}>", getClass()
                .getName(), id, signed);
    }
}
