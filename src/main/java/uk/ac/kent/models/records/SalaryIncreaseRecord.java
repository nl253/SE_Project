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

@Entity(name = "PersonalDetailsRecord")
@Table(name = "personal_details")
@Access(AccessType.FIELD)
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class SalaryIncreaseRecord extends BaseRecord {

    @Column(name = "new_salary")
    private long newSalary;

    @Column(name = "start_date")
    private LocalDate startDate;

    /**
     * @param dateCreated date of creation ({@link LocalDate})
     * @param modifiedDate date of modification ({@link LocalDate})
     * @param newSalary new annual salary
     * @param startDate start date ({@link LocalDate})
     */

    public SalaryIncreaseRecord(final LocalDate dateCreated, final LocalDate modifiedDate, final long newSalary, final LocalDate startDate) {
        super(dateCreated, modifiedDate);
        this.newSalary = newSalary;
        this.startDate = startDate;
    }

    /**
     * @param newSalary new annual salary
     * @param startDate start date ({@link LocalDate})
     */

    public SalaryIncreaseRecord(final long newSalary, final LocalDate startDate) {
        this(LocalDate.now(), LocalDate.now(), newSalary, startDate);
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    public SalaryIncreaseRecord() {}


    /**
     * @return a fake {@link SalaryIncreaseRecord}
     */


    public long getNewSalary() {
        return newSalary;
    }

    public void setNewSalary(final long newSalary) {
        this.newSalary = newSalary;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    @SuppressWarnings("ConditionalExpression")
    @Override
    public String toString() {
        // @formatter:off
        return MessageFormat.format("SalaryIncreaseRecord<newSalary={0}, startDate={1}, modificationDate={2}, creationDate={3}>",
                                    newSalary,
                                    (startDate == null) ? "not available" : startDate.toString(),
                                    (getModificationDate() == null) ? "not available" : getModificationDate().toString(),
                                    (getCreationDate() == null) ? "not available" : getCreationDate().toString()
                                   );
        // @formatter:on
    }
}
