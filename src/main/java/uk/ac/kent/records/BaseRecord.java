package uk.ac.kent.records;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

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

@MappedSuperclass
@SuppressWarnings("ClassHasNoToStringMethod")
abstract class BaseRecord {

    private boolean signed;

    @Id
    private final int id;

    private static int nextId;

    @SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod")
    BaseRecord() {
        id = nextId;
        nextId++;
    }

    final int getId() { return id; }

    final boolean isSigned() { return signed; }

    final void setSigned(final boolean signed) { this.signed = signed; }
}
