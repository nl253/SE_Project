package uk.ac.kent.records;


import java.text.MessageFormat;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import uk.ac.kent.people.Department;

/**
 * @author norbert
 */

@Entity(name = "PersonalDetailsRecord")
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class PromotionRecord {

    private Department newDepartament;
    private Position newPosition;
    private LocalDateTime startDate;
    private long newSalary;

    @Override
    public String toString() {
        return MessageFormat
                .format("PromotionRecord'{'newDepartament={0}, newPosition={1}, newSalary={2}, startDate={3}'}'", newDepartament, newPosition, newSalary, startDate);
    }

    public Department getNewDepartament() { return newDepartament; }

    public void setNewDepartament(final Department newDepartament) {
        this.newDepartament = newDepartament;
    }

    public Position getNewPosition() { return newPosition; }

    public void setNewPosition(final Position newPosition) {
        this.newPosition = newPosition;
    }

    public LocalDateTime getStartDate() { return startDate; }

    public void setStartDate(final LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public long getNewSalary() { return newSalary; }

    public void setNewSalary(final long newSalary) { this.newSalary = newSalary; }
}
