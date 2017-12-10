package uk.ac.kent.records;

/**
 * @author norbert
 */

public enum TerminationReason {
    DISMISSAL, RESIGNATION;

    @Override
    public String toString() {
        return name();
    }
}
