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
    // @Column(name = "review_date")
    private LocalDate reviewDate;

    public ProbationRecord(final LocalDate startDate, final LocalDate endDate, final LocalDate reviewDate, final String reason) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reviewDate = reviewDate;
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

        final ProbationRecord record = new ProbationRecord();

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
        record.startDate = dateSupplier.get();

        // get random LocalDate
        record.endDate = record.startDate.plusMonths(random.nextInt(50));

        // get random LocalDate
        record.reviewDate = record.endDate.minusMonths(random.nextInt(10));

        record.reason = faker.lorem().paragraph();

        return record;
    }

    public LocalDate getStartDate() { return startDate; }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() { return endDate; }

    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getReviewDate() { return reviewDate; }

    public void setReviewDate(final LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReason() { return reason; }

    public void setReason(final String reason) { this.reason = reason; }

    @Override
    public String toString() {
        // @formatter:off
        return MessageFormat.format(
                        "ProbationRecord<startDate={0}, endDate={1}, reviewDate={2}, reason='{3}'>",
                        startDate, endDate, reviewDate, reason);
        // @formatter:on
    }
}
