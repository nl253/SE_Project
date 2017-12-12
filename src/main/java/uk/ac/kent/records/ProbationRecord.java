package uk.ac.kent.records;


import com.github.javafaker.Faker;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;
import java.util.function.Supplier;
import javax.persistence.Entity;

/**
 * @author norbert
 */

@Entity(name = "PersonalDetailsRecord")
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class ProbationRecord extends BaseRecord {

    private String reason;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate reviewDate;

    public ProbationRecord(final LocalDate startDate, final LocalDate endDate, final LocalDate reviewDate, final String reason) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reviewDate = reviewDate;
        this.reason = reason;
    }

    @SuppressWarnings("MagicNumber")
    public ProbationRecord() {
        // secure pseudo-random number generator
        final Random random = new SecureRandom();

        // fake data generator
        final Faker faker = new Faker(new Locale("en-GB"));

        // @formatter:off
        final Supplier<LocalDate> dateSupplier = () -> LocalDate.parse(
                MessageFormat.format(
                        "201{0}-{1}-{2}",
                        16 + random.nextInt(2),
                        1 + random.nextInt(12),
                        1 + random.nextInt(28)));
        // @formatter:on

        // get random LocalDate
        endDate = dateSupplier.get();

        // get random LocalDate
        startDate = dateSupplier.get();

        // get random LocalDate
        reviewDate = dateSupplier.get();

        reason = faker.lorem().paragraph();
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
