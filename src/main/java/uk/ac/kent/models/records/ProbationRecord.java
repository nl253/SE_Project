package uk.ac.kent.models.records;

import java.text.MessageFormat;
import java.time.LocalDate;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author norbert
 */

@Entity
@Access(AccessType.FIELD)
@Table(name = "probation_records")
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class ProbationRecord extends BaseRecord {

    private String reason;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    /**
     * @param startDate start date of probation
     * @param endDate end date of probation
     * @param reason reason for probation
     */

    public ProbationRecord(final LocalDate startDate, final LocalDate endDate, final String reason) {
        super(LocalDate.now(), LocalDate.now());
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    public ProbationRecord() {}

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    @SuppressWarnings("ConditionalExpression")
    @Override
    public String toString() {
        // @formatter:off
        return MessageFormat.format(
                        "ProbationRecord<startDate={0}, endDate={1}, reason={2}, creationDate={3}>",
                        (startDate == null) ? "not available" : startDate.toString(),
                        (endDate == null) ? "not available" : endDate.toString(),
                        (reason == null) ? "not available" : reason,
                        (getCreationDate() == null) ? "not available" : getCreationDate().toString()
                                   );
        // @formatter:on
    }
}
