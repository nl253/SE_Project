package uk.ac.kent.records;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Random;
import java.util.function.Supplier;
import javax.persistence.Entity;

/**
 * @author norbert
 */

@Entity(name = "PersonalDetailsRecord")
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class TerminationRecord extends BaseRecord {

    private TerminationReason reason;
    private LocalDate endDate;

    /**
     * @param reason termination reason
     * @param endDate end date
     */

    public TerminationRecord(final TerminationReason reason, final LocalDate endDate) {
        this.reason = reason;
        this.endDate = endDate;
    }

    /**
     * Generate TerminationRecord with random data.
     */

    public TerminationRecord() {

        // secure pseudo-random number generator
        final Random random = new SecureRandom();

        // @formatter:off
        final Supplier<LocalDate> dateSupplier = () -> LocalDate.parse(
                MessageFormat.format(
                        "201{0}-{1}-{2}",
                        16 + random.nextInt(2),
                        1 + random.nextInt(12),
                        1 + random.nextInt(28)));
        // @formatter:on

        endDate = dateSupplier.get();
        reason = TerminationReason.values()[random
                .nextInt(TerminationReason.values().length)];
    }

    public TerminationReason getReason() { return reason; }

    public void setReason(final TerminationReason reason) { this.reason = reason; }

    public LocalDate getEndDate() { return endDate; }

    public void setEndDate(final LocalDate endDate) { this.endDate = endDate; }

    @Override
    public String toString() {
        return MessageFormat
                .format("TerminationRecord<reason={0}, endDate={1}>", reason, endDate);
    }
}
