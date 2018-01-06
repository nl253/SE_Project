package uk.ac.kent.models.records;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.logging.Logger;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Base record is the superclass for all types of records kept in the system.
 * <p>
 * It equips all subclasses with basic fields and getters / setters.
 *
 * @author norbert
 */

@SuppressWarnings({"ClassHasNoToStringMethod", "PublicMethodNotExposedInInterface"})
@MappedSuperclass
@Table(name = "records")
@Access(AccessType.FIELD)
public abstract class BaseRecord {

    /** Logger for the class */
    @Transient
    protected static final Logger log = Logger.getAnonymousLogger();

    private boolean signed;

    @Column(name = "modified_date")
    @UpdateTimestamp
    private LocalDate modifiedDate;

    @SuppressWarnings({"WeakerAccess", "ProtectedField"})
    @Id
    @GeneratedValue
    protected int id;

    @Column(name = "date_created", updatable = false)
    @CreationTimestamp
    private LocalDate dateCreated = LocalDate.now();


    public final LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public final void setModifiedDate(final LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    final int getId() {
        return id;
    }

    public final void setId(final int id) {
        this.id = id;
    }

    final boolean getSigned() {
        return signed;
    }

    public final boolean isSigned() {
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

    @SuppressWarnings({"DesignForExtension", "ConditionalExpression"})
    @Override
    public String toString() {
        return MessageFormat.format("{0}<id={1}, signed={2}>", getClass()
                .getName(), id, signed);
    }
}
