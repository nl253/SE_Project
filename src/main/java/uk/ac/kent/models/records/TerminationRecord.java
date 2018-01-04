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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * Each {@link uk.ac.kent.models.people.Employee} <em>may</em> have a single
 * {@link TerminationRecord}.
 * <p>
 * Even though after receiving such a record she would no longer employed,
 * the data remains in the system.
 *
 * @author norbert
 */

@Entity(name = "PersonalDetailsRecord")
@Table(name = "termination_records")
@Access(AccessType.FIELD)
@SuppressWarnings({"ClassWithoutLogger", "unused", "PublicConstructor", "PublicMethodNotExposedInInterface"})
public final class TerminationRecord extends BaseRecord {

    @Enumerated(EnumType.STRING)
    private TerminationReason reason;

    @Column(name = "end_date")
    private LocalDate endDate = LocalDate.now();

    /**
     * @param reason termination reason
     * @param endDate end date
     */

    public TerminationRecord(final TerminationReason reason, final LocalDate endDate) {
        this.reason = reason;
        this.endDate = endDate;
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings({"MagicNumber", "ProtectedMemberInFinalClass"})
    protected TerminationRecord() {}

    /**
     * @return a fake {@link TerminationRecord}
     */

    @SuppressWarnings({"AccessingNonPublicFieldOfAnotherObject", "LocalVariableOfConcreteClass", "MagicNumber", "Duplicates"})
    public static TerminationRecord fake() {

        // secure pseudo-random number generator
        final Random random = new SecureRandom();

        // @formatter:off
        final Supplier<LocalDate> randomDateSupplier = () -> {
            // generate a random integer between those two values and finally
            // convert it back to a LocalDate
            final long minDay = LocalDate.of(2013, 1, 1).toEpochDay();
            final long maxDay = LocalDate.of(2017, 1, 31).toEpochDay();
            final long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            return LocalDate.ofEpochDay(randomDay);
        };

        final TerminationRecord record = new TerminationRecord();


        // get random value from TerminationReason enum
        final TerminationReason reason = TerminationReason.values()[random.nextInt(TerminationReason.values().length)];

        // @formatter:on
        return new TerminationRecord(reason, randomDateSupplier.get());
    }

    public TerminationReason getReason() {
        return reason;
    }

    public void setReason(final TerminationReason reason) {
        this.reason = reason;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return MessageFormat
                .format("TerminationRecord<reason={0}, endDate={1}>", reason, endDate);
    }
}
