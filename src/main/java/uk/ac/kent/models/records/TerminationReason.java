package uk.ac.kent.models.records;

import java.text.MessageFormat;

/**
 * Used for a field in {@link TerminationRecord}.
 *
 * @author norbert
 */

public enum TerminationReason {
    DISMISSAL, RESIGNATION;

    @Override
    public String toString() {
        return MessageFormat.format("TerminationReason<{0}>", name());
    }
}
