package uk.ac.kent.records;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import javax.persistence.Entity;

/**
 * @author norbert
 */

@Entity(name = "PersonalDetailsRecord")
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class TerminationRecord extends BaseRecord {

    private TerminationReason reason;
    private LocalDateTime endDate;

    public TerminationRecord(final TerminationReason reason, final LocalDateTime endDate) {
        this.reason = reason;
        this.endDate = endDate;
    }

    public TerminationReason getReason() {
        return reason;
    }

    public void setReason(final TerminationReason reason) {
        this.reason = reason;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(final LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return MessageFormat
                .format("TerminationRecord<reason={0}, endDate={1}>", reason, endDate);
    }
}
