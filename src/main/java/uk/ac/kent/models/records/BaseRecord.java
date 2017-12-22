package uk.ac.kent.models.records;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.logging.Logger;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    /** Logger for the class */
    @Transient
    protected static final Logger log = Logger.getAnonymousLogger();

    @Basic(optional = false)
    private boolean signed;

    @Column(name = "modified_date")
    @UpdateTimestamp
    private LocalDate modifiedDate;

    @SuppressWarnings({"WeakerAccess", "ProtectedField"})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected final int id;

    @Basic(optional = false)
    @CreationTimestamp
    @Column(name = "date_created", updatable = false)
    private LocalDate dateCreated = LocalDate.now();

    @Transient
    private static int nextId;

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod")
    BaseRecord() {
        id = nextId;
        nextId++;
    }

    public final LocalDate getModifiedDate() {
        return modifiedDate;
    }
    public final void setModifiedDate(final LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    final int getId() {
        return id;
    }

    final boolean getSigned() {
        return signed;
    }

    public final void setSigned(final boolean signed) {
        this.signed = signed;
    }

    public final LocalDate getDateCreated() {
        return dateCreated;
    }

    public final void setDateCreated(final LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @SuppressWarnings("DesignForExtension")
    @Override
    public String toString() {
        return MessageFormat.format("{0}<id={1} signed={2}>", getClass()
                .getName(), id, signed);
    }
}
