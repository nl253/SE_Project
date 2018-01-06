package uk.ac.kent.models.records;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
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

    @Column(name = "new_salary")
    private long newSalary;

    @Column(name = "start_date")
    private LocalDate startDate;

    // /**
    //  * @param newSalary new salary
    //  * @param startDate starting data
    //  */
    //
    // public SalaryIncreaseRecord(final long newSalary, final LocalDate startDate) {
    //     this.newSalary = newSalary;
    //     this.startDate = startDate;
    // }
    //
    // /**
    //  * @param newSalary new salary
    //  */
    //
    // public SalaryIncreaseRecord(final long newSalary) {
    //     this(newSalary, LocalDate.now());
    // }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    public SalaryIncreaseRecord() {}

    /**
     * @return a fake {@link SalaryIncreaseRecord}
     */

    @SuppressWarnings("Duplicates")
    @Transient
    public static SalaryIncreaseRecord fake() {
        final Random random = new SecureRandom();
        final SalaryIncreaseRecord record = new SalaryIncreaseRecord();

        // @formatter:off
        final Supplier<LocalDate> randomDateSupplier = () -> {
            // generate a random integer between those two values and finally
            // convert it back to a LocalDate
            final long minDay = LocalDate.of(2013, 1, 1).toEpochDay();
            final long maxDay = LocalDate.of(2017, 1, 31).toEpochDay();
            final long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            return LocalDate.ofEpochDay(randomDay);
        };

        record.setNewSalary(15_000 + random.nextInt(30_000));
        record.setStartDate(randomDateSupplier.get());

        return record;
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

    @SuppressWarnings("ConditionalExpression")
    @Override
    public String toString() {
        return MessageFormat
                .format("SalaryIncreaseRecord<newSalary={0}, startDate={1}>", newSalary, (startDate == null) ? "not available" : startDate
                        .toString());
    }
}
