package uk.ac.kent.models.records;

import java.text.MessageFormat;
import java.time.LocalDate;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import uk.ac.kent.models.yuconz.Department;
import uk.ac.kent.models.yuconz.Position;

/**
 * {@link uk.ac.kent.models.people.Employee}s and {@link uk.ac.kent.models.people.Manager}s can get promoted to a new {@link Position}.
 * The {@link PromotionRecord} is a subclass of {@link BaseRecord}.
 *
 * @author norbert
 */

@Entity
@Access(AccessType.FIELD)
@Table(name = "promotion_records")
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class PromotionRecord extends BaseRecord {

    @Enumerated(EnumType.STRING)
    @Column(name = "new_department")
    private Department newDepartment;

    @Column(name = "new_position")
    @Enumerated(EnumType.STRING)
    private Position newPosition;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "new_salary")
    private long newSalary;

    /**
     * @param newDepartment new {@link Department}
     * @param newPosition new {@link Position}
     * @param newSalary new salary (numeric)
     * @param startDate start date ({@link LocalDate})
     */

    @SuppressWarnings("ConstructorWithTooManyParameters")
    public PromotionRecord(final Department newDepartment, final Position newPosition, final long newSalary, final LocalDate startDate) {
        super(LocalDate.now(), LocalDate.now());
        this.newDepartment = newDepartment;
        this.newPosition = newPosition;
        this.startDate = startDate;
        this.newSalary = newSalary;
    }

    /**
     * @param newDepartment new {@link Department}
     * @param newPosition new {@link Position}
     * @param newSalary new salary (numeric)
     * @param startDate start date ({@link LocalDate})
     */

    public PromotionRecord(final Position newPosition, final Department newDepartment, final long newSalary, final LocalDate startDate) {
        this(newDepartment, newPosition, newSalary, startDate);
    }

    /**
     * @param newDepartment new {@link Department}
     * @param newPosition new {@link Position}
     * @param newSalary new salary (numeric)
     */

    public PromotionRecord(final Position newPosition, final Department newDepartment, final long newSalary) {
        this(newDepartment, newPosition, newSalary, LocalDate.now());
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    protected PromotionRecord() {}

    /**
     * @return a fake {@link PromotionRecord}
     */

    public Department getNewDepartment() {
        return newDepartment;
    }

    public void setNewDepartment(final Department newDepartment) {
        this.newDepartment = newDepartment;
    }

    public Position getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(final Position newPosition) {
        this.newPosition = newPosition;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    public long getNewSalary() {
        return newSalary;
    }

    public void setNewSalary(final long newSalary) { this.newSalary = newSalary; }

    @SuppressWarnings("ConditionalExpression")
    @Override
    public String toString() {
        // @formatter:off
        return MessageFormat
                .format("PromotionRecord<newDepartment={0}, newPosition={1}, newSalary={2}, startDate={3}, modifiedDate={4}, dateCreated={5}>",
                        (newDepartment == null) ? "not available" : newDepartment.toString(),
                        (newPosition == null) ? "not available" : newPosition.toString() ,
                        newSalary,
                        (startDate == null) ? "not available" : startDate.toString(),
                        (getCreationDate() == null) ? "not available" : getCreationDate().toString());
        // @formatter:on
    }
}
