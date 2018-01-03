package uk.ac.kent.models.records;

import java.text.MessageFormat;

/**
 * A {@link Position} represents an {@link uk.ac.kent.models.people.Employee}'s
 * position in the company.
 * <p>
 * There are only 3 main types ie {@link uk.ac.kent.models.people.Director},
 * {@link uk.ac.kent.models.people.Manager} and a regular {@link uk.ac.kent.models.people.Employee}.
 *
 * @author norbert
 */

public enum Position {

    DIRECTOR, EMPLOYEE, MANAGER;

    @Override
    public String toString() {
        return MessageFormat.format("Position<{0}>", name());
    }
}
