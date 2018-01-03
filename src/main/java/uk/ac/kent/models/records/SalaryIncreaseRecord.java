package uk.ac.kent.models.records;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Random;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author norbert
 */

@Entity(name = "PersonalDetailsRecord")
@Table(name = "personal_details")
@Access(AccessType.FIELD)
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class SalaryIncreaseRecord extends BaseRecord {

    // @Column(name = "new_salary", nullable = false)
    @Basic(optional = false)
    private long newSalary;
    // @Column(name = "start_date")
    private LocalDate startDate;

    /**
     * @param newSalary new salary
     * @param startDate starting data
     */

    public SalaryIncreaseRecord(final long newSalary, final LocalDate startDate) {
        this.newSalary = newSalary;
        this.startDate = startDate;
    }

    /**
     * @param newSalary new salary
     */

    public SalaryIncreaseRecord(final long newSalary) {
        this(newSalary, LocalDate.now());
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    public SalaryIncreaseRecord() {}

    /**
     * @return a fake {@link SalaryIncreaseRecord}
     */

    @Transient
    public static SalaryIncreaseRecord fake() {
        final Random random = new SecureRandom();
        return new SalaryIncreaseRecord(15_000 + random.nextInt(30_000));
    }

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

    @Override
    public String toString() {
        return MessageFormat
                .format("SalaryIncreaseRecord<newSalary={0}, startDate={1}>", newSalary, startDate);
    }
}
