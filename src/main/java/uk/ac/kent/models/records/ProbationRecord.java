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
import javax.persistence.Basic;
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

    @Basic(optional = false)
    private String reason;
    // @Column(name = "start_date")
    private LocalDate startDate;
    // @Column(name = "end_date")
    private LocalDate endDate;

    /**
     * @param startDate start date of probation
     * @param endDate end date of probation
     * @param reason reason for probation
     */

    public ProbationRecord(final LocalDate startDate, final LocalDate endDate, final String reason) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    public ProbationRecord() {}

    @Transient
    @SuppressWarnings({"MagicNumber", "ImplicitNumericConversion", "LocalVariableOfConcreteClass", "AccessingNonPublicFieldOfAnotherObject"})
    public static ProbationRecord fake() {
        // secure pseudo-random number generator
        final Random random = new SecureRandom();

        // fake data generator
        final Faker faker = new Faker(new Locale("en-GB"));

        // @formatter:off
        final Supplier<LocalDate> dateSupplier = () -> LocalDate.parse(
                MessageFormat.format(
                        "20{0}-{1}-{2}",
                        16 + random.nextInt(2),
                        1 + random.nextInt(12),
                        1 + random.nextInt(28)));
        // @formatter:on

        // get random LocalDate
        final LocalDate start = dateSupplier.get();

        // get random LocalDate
        final LocalDate end = start.plusMonths(random.nextInt(50));

        final String reason = faker.lorem().paragraph();

        return new ProbationRecord(start, end, reason);
    }

    public LocalDate getStartDate() { return startDate; }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() { return endDate; }

    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getReason() { return reason; }

    public void setReason(final String reason) { this.reason = reason; }

    @Override
    public String toString() {
        // @formatter:off
        return MessageFormat.format(
                        "ProbationRecord<startDate={0}, endDate={1}, reason={2}>",
                        startDate, endDate, reason);
        // @formatter:on
    }
}
