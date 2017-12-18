package uk.ac.kent.models.records;

import com.github.javafaker.Faker;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * @author norbert
 */

@Entity(name = "PersonalDetailsRecord")
@Access(AccessType.FIELD)
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class SalaryIncreaseRecord extends BaseRecord {

    private long newSalary;
    private LocalDateTime startDate;

    public SalaryIncreaseRecord(final long newSalary, final LocalDateTime startDate) {
        this.newSalary = newSalary;
        this.startDate = startDate;
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    public SalaryIncreaseRecord() {}

    @Transient
    public static SalaryIncreaseRecord fake() {
        SalaryIncreaseRecord record = new SalaryIncreaseRecord();

        // fake data generator
        final Faker faker = new Faker(new Locale("en-GB"));

        record.newSalary = faker.number().numberBetween(15_000, 100_000);
        return record;
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
        return MessageFormat
                .format("SalaryIncreaseRecord<newSalary={0}, startDate={1}>", newSalary, startDate);
    }
}
