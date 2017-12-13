package uk.ac.kent.models.records;

import com.github.javafaker.Faker;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;
import java.util.function.Supplier;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import uk.ac.kent.models.people.Department;

/**
 * @author norbert
 */

@Entity
@Access(AccessType.FIELD)
@Table(name = "promotion_records")
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class PromotionRecord extends BaseRecord {

    private Department newDepartament;
    private Position newPosition;
    private LocalDate startDate;
    private long newSalary;

    public PromotionRecord(final Department newDepartament, final Position newPosition, final LocalDate startDate, final long newSalary) {
        this.newDepartament = newDepartament;
        this.newPosition = newPosition;
        this.startDate = startDate;
        this.newSalary = newSalary;
    }

    public PromotionRecord() {}

    @Transient
    @SuppressWarnings({"AlibabaAvoidCommentBehindStatement", "ImplicitNumericConversion", "MagicNumber", "LocalVariableOfConcreteClass"})
    public static PromotionRecord fake() {

        final PromotionRecord record = new PromotionRecord();

        // fake data generator
        final Faker faker = new Faker(new Locale("en-GB"));

        // secure pseudo-random number generator
        final Random random = new SecureRandom();

        record.newDepartament = Department.values()[random
                .nextInt(Department.values().length)];
        record.newPosition = Position.values()[random
                .nextInt(Position.values().length)];

        // @formatter:off
        final Supplier<LocalDate> dateSupplier = () -> LocalDate.parse(
                MessageFormat.format(
                        "201{0}-{1}-{2}",
                        16 + random.nextInt(2),
                        1 + random.nextInt(12),
                        1 + random.nextInt(28)));
        // @formatter:on

        record.startDate = dateSupplier.get();

        record.newSalary = faker.number().numberBetween(15_000, 100_000);
        return record;
    }

    public Department getNewDepartament() { return newDepartament; }

    public void setNewDepartament(final Department newDepartament) {
        this.newDepartament = newDepartament;
    }

    public Position getNewPosition() { return newPosition; }

    public void setNewPosition(final Position newPosition) {
        this.newPosition = newPosition;
    }

    public LocalDate getStartDate() { return startDate; }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    public long getNewSalary() { return newSalary; }

    public void setNewSalary(final long newSalary) { this.newSalary = newSalary; }

    @Override
    public String toString() {
        return MessageFormat
                .format("PromotionRecord'{'newDepartament={0}, newPosition={1}, newSalary={2}, startDate={3}'}'", newDepartament, newPosition, newSalary, startDate);
    }
}
