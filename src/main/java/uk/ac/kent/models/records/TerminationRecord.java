package uk.ac.kent.models.records;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Random;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @SuppressWarnings({"AccessingNonPublicFieldOfAnotherObject", "LocalVariableOfConcreteClass", "MagicNumber"})
    public static TerminationRecord fake() {

        final TerminationRecord record = new TerminationRecord();

        // secure pseudo-random number generator
        final Random random = new SecureRandom();

        // @formatter:off
        record.endDate = LocalDate.parse(
                MessageFormat.format(
                        "20{0}-{1}-{2}",
                        16 + random.nextInt(2),
                        1 + random.nextInt(12),
                        1 + random.nextInt(28)));

        // get random value from TerminationReason enum
        record.reason = TerminationReason.values()[random.nextInt(TerminationReason.values().length)];

        // @formatter:on
        return record;
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
