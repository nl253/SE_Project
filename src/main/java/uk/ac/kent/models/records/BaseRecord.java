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

@SuppressWarnings({"ClassHasNoToStringMethod", "PublicMethodNotExposedInInterface", "WeakerAccess", "ConstructorNotProtectedInAbstractClass"})
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
    private LocalDate modificationDate;

    @SuppressWarnings({"WeakerAccess", "ProtectedField"})
    @Id
    @GeneratedValue
    protected int id;


    @Column(name = "date_created", updatable = false)
    @CreationTimestamp
    private LocalDate creationDate;

    /**
     * @param creationDate date of creation ({@link LocalDate})
     * @param modificationDate date of modification ({@link LocalDate})
     */

    public BaseRecord(final LocalDate creationDate, final LocalDate modificationDate) {
        this.modificationDate = modificationDate;
        this.creationDate = creationDate;
    }

    /**
     * Empty constructor for Hibernate.
     */

    public BaseRecord() {}

    public final LocalDate getModificationDate() {
        return modificationDate;
    }

    public final void setModificationDate(final LocalDate modifiedDate) {
        this.modificationDate = modifiedDate;
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

    public final void sign() {
        signed = true;
    }

    public final LocalDate getCreationDate() {
        return creationDate;
    }

    public final void setCreationDate(final LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @SuppressWarnings({"DesignForExtension", "ConditionalExpression"})
    @Override
    public String toString() {
        // @formatter:off
        return MessageFormat.format("{0}<id={1}, signed={2}, modifiedDate={3}, dateCreated={4}>",
                                    getClass().getName(),
                                    id,
                                    signed,
                                    (modificationDate != null) ? getModificationDate().toString() : "not available",
                                    (creationDate != null) ? getCreationDate().toString() : "not available"
                                   );
        // @formatter:on
    }
}
