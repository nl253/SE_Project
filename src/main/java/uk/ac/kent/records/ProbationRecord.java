package uk.ac.kent.records;


import java.text.MessageFormat;
import java.time.LocalDateTime;
import javax.persistence.Entity;

/**
 * @author norbert
 */

@Entity(name = "PersonalDetailsRecord")
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class ProbationRecord extends BaseRecord {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime reviewDate;
    private String reason;

    public LocalDateTime getStartDate() { return startDate; }

    public void setStartDate(final LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() { return endDate; }

    public void setEndDate(final LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getReviewDate() { return reviewDate; }

    public void setReviewDate(final LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReason() { return reason; }

    public void setReason(final String reason) { this.reason = reason; }

    @Override
    public String toString() {
        return MessageFormat
                .format("ProbationRecord<startDate={0}, endDate={1}, reviewDate={2}, reason='{3}'>", startDate, endDate, reviewDate, reason);
    }
}
