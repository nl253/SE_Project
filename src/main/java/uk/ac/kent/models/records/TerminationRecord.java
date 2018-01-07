package uk.ac.kent.models.records;

import java.text.MessageFormat;
import java.time.LocalDate;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Each {@link uk.ac.kent.models.people.Employee} <em>may</em> have a single
 * {@link TerminationRecord}.
 * <p>
 * Even though after receiving such a record she would no longer employed,
 * the data remains in the system.
 *
 * @author norbert
 */

@Entity(name = "PersonalDetailsRecord")
@Table(name = "termination_records")
@Access(AccessType.FIELD)
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class TerminationRecord extends BaseRecord {

    private String reason;

    @Column(name = "end_of_employment")
    private LocalDate endOfEmployment;

    /**
     * @param creationDate date of creation ({@link LocalDate})
     * @param modificationDate date of modification ({@link LocalDate})
     * @param endOfEmployment last day in work ({@link LocalDate})
     * @param reason reason for termination ({@link String})
     */

    public TerminationRecord(final LocalDate creationDate, final LocalDate modificationDate, final String reason, final LocalDate endOfEmployment) {
        super(creationDate, modificationDate);
        this.reason = reason;
        this.endOfEmployment = endOfEmployment;
    }

    /**
     * @param creationDate date of creation ({@link LocalDate})
     * @param modificationDate date of modification ({@link LocalDate})
     * @param endOfEmployment last day in work ({@link LocalDate})
     */

    public TerminationRecord(final LocalDate creationDate, final LocalDate modificationDate, final LocalDate endOfEmployment) {
        super(creationDate, modificationDate);
        this.endOfEmployment = endOfEmployment;
    }

    /**
     * @param reason reason for termination ({@link String})
     * @param endOfEmployment end date
     */

    public TerminationRecord(final String reason, final LocalDate endOfEmployment) {
        this.reason = reason;
        this.endOfEmployment = endOfEmployment;
    }


    /**
     * @param reason reason for termination ({@link String})
     */

    public TerminationRecord(final String reason) {
        this(reason, LocalDate.now());
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings({"MagicNumber", "ProtectedMemberInFinalClass"})
    public TerminationRecord() {}

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    public LocalDate getEndOfEmployment() {
        return endOfEmployment;
    }

    public void setEndOfEmployment(final LocalDate endOfEmployment) {
        this.endOfEmployment = endOfEmployment;
    }

    @SuppressWarnings("ConditionalExpression")
    @Override
    public String toString() {
        // @formatter:off
        return MessageFormat.format("TerminationRecord<reason={0}, endDate={1}>",
                                    (reason == null) ? "not available" : reason,
                                    (endOfEmployment == null) ? "not available" : endOfEmployment.toString(),
                                    (getCreationDate() == null) ? "not available" : getCreationDate().toString()
                                   );
        // @formatter:on
    }
}
