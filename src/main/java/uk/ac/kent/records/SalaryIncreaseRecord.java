package uk.ac.kent.records;

import com.github.javafaker.Faker;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import javax.persistence.Entity;

/**
 * @author norbert
 */

@Entity(name = "PersonalDetailsRecord")
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class SalaryIncreaseRecord extends BaseRecord {

    private long newSalary;
    private LocalDateTime startDate;

    public SalaryIncreaseRecord(final long newSalary, final LocalDateTime startDate) {
        this.newSalary = newSalary;
        this.startDate = startDate;
    }

    public SalaryIncreaseRecord() {

        // fake data generator
        final Faker faker = new Faker(new Locale("en-GB"));

        newSalary = faker.number().numberBetween(15_000, 100_000);
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
