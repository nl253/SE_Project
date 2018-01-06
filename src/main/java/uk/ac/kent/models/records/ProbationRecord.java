package uk.ac.kent.models.records;


import com.github.javafaker.Faker;
import com.github.javafaker.Lorem;
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
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author norbert
 */

@Entity
@Access(AccessType.FIELD)
@Table(name = "probation_records")
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class ProbationRecord extends BaseRecord {

    private String reason;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    // /**
    //  * @param startDate start date of probation
    //  * @param endDate end date of probation
    //  * @param reason reason for probation
    //  */

    // public ProbationRecord(final LocalDate startDate, final LocalDate endDate, final String reason) {
    //     this.startDate = startDate;
    //     this.endDate = endDate;
    //     this.reason = reason;
    // }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    public ProbationRecord() {}

    @Transient
    @SuppressWarnings({"MagicNumber", "ImplicitNumericConversion", "LocalVariableOfConcreteClass", "AccessingNonPublicFieldOfAnotherObject", "Duplicates"})
    public static ProbationRecord fake() {
        // secure pseudo-random number generator
        final Random random = new SecureRandom();

        // fake data generator
        final Faker faker = new Faker(new Locale("en-GB"));
        final Lorem loremFaker = faker.lorem();

        // @formatter:off
        final Supplier<LocalDate> randomDateSupplier = () -> {
            // generate a random integer between those two values and finally
            // convert it back to a LocalDate
            final long minDay = LocalDate.of(2013, 1, 1).toEpochDay();
            final long maxDay = LocalDate.of(2017, 1, 31).toEpochDay();
            final long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            return LocalDate.ofEpochDay(randomDay);
        };

        // @formatter:on

        // get random LocalDate
        final LocalDate start = randomDateSupplier.get();

        // get random LocalDate
        final LocalDate end = start.plusMonths(random.nextInt(50));

        final String reason = loremFaker.paragraph();

        final ProbationRecord record = new ProbationRecord();
        record.setReason(reason);
        record.setEndDate(end);
        record.setStartDate(start);
        return record;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    @SuppressWarnings("ConditionalExpression")
    @Override
    public String toString() {
        // @formatter:off
        return MessageFormat.format(
                        "ProbationRecord<startDate={0}, endDate={1}, reason={2}>",
                        (startDate == null) ? "not available" : startDate.toString(),
                        (endDate == null) ? "not available" : endDate.toString(),
                        (reason == null) ? "not available" : reason.toString());
        // @formatter:on
    }
}
