package uk.ac.kent.models.records;

import com.github.javafaker.Faker;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import uk.ac.kent.models.yuconz.Department;
import uk.ac.kent.models.yuconz.Position;

/**
 * @author norbert
 */

@Entity
@Access(AccessType.FIELD)
@Table(name = "promotion_records")
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class PromotionRecord extends BaseRecord {

    @Enumerated(EnumType.STRING)
    private Department newDepartment;

    @Enumerated(EnumType.STRING)
    private Position newPosition;

    private LocalDate startDate;

    @Column(name = "new_salary")
    private long newSalary;

    // /**
    //  * Create a new {@link PromotionRecord}.
    //  *
    //  * @param newDepartament new department the employee is assigned to
    //  * @param newPosition new position of the employee
    //  * @param startDate start
    //  * @param newSalary new salary
    //  */

    // public PromotionRecord(final Department newDepartament, final Position newPosition, final LocalDate startDate, final long newSalary) {
    //     this.newDepartment = newDepartament;
    //     this.newPosition = newPosition;
    //     this.startDate = startDate;
    //     this.newSalary = newSalary;
    // }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    protected PromotionRecord() {}

    /**
     * @return a fake {@link PromotionRecord}
     */

    @Transient
    @SuppressWarnings({"AlibabaAvoidCommentBehindStatement", "ImplicitNumericConversion", "MagicNumber", "LocalVariableOfConcreteClass", "AccessingNonPublicFieldOfAnotherObject", "Duplicates"})
    public static PromotionRecord fake() {

        final PromotionRecord record = new PromotionRecord();

        // fake data generator
        final Faker faker = new Faker(new Locale("en-GB"));

        // secure pseudo-random number generator
        final Random random = new SecureRandom();

        record.newDepartment = Department.values()[random
                .nextInt(Department.values().length)];
        record.newPosition = Position.values()[random
                .nextInt(Position.values().length)];

        // @formatter:off
        final Supplier<LocalDate> randomDateSupplier = () -> {
            // generate a random integer between those two values and finally
            // convert it back to a LocalDate
            final long minDay = LocalDate.of(2013, 1, 1).toEpochDay();
            final long maxDay = LocalDate.of(2017, 1, 31).toEpochDay();
            final long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            return LocalDate.ofEpochDay(randomDay);
        };

        record.startDate = randomDateSupplier.get();

        record.newSalary = faker.number().numberBetween(15_000, 100_000);
        return record;
    }

    public Department getNewDepartment() {
        return newDepartment;
    }

    public void setNewDepartment(final Department newDepartament) {
        this.newDepartment = newDepartament;
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
                .format("PromotionRecord<newDepartament={0}, newPosition={1}, newSalary={2}, startDate={3}>",
                        (newDepartment == null) ? "not available" : newDepartment.toString(),
                        (newPosition == null) ? "not available" : newPosition.toString() ,
                        newSalary,
                        (startDate == null) ? "not available" : startDate.toString());
        // @formatter:on
    }
}
