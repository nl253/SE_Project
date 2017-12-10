package uk.ac.kent.records;

import java.time.LocalDateTime;
import javax.persistence.Entity;

/**
 * @author norbert
 */

@Entity(name = "PersonalDetailsRecord")
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor"})
public final class SalaryIncreaseRecord extends BaseRecord {

    private long newSalary;
    private LocalDateTime startDate;

    public SalaryIncreaseRecord(final long newSalary, final LocalDateTime startDate) {
        this.newSalary = newSalary;
        this.startDate = startDate;
    }

    public long getNewSalary() {
        return newSalary;
    }

    public void setNewSalary(final long newSalary) {
        this.newSalary = newSalary;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(final LocalDateTime startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
