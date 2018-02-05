package uk.ac.kent.models.yuconz;

import java.text.MessageFormat;

/**
 * A {@link Position} represents a position in the company.
 * <p>
 * There are only 3 main types ie
 * <p>
 * - {@link uk.ac.kent.models.people.Director},
 * - {@link uk.ac.kent.models.people.Manager},
 * - a regular {@link uk.ac.kent.models.people.Employee}.
 *
 * @author Norbert
 */

public enum Position {

    DIRECTOR, EMPLOYEE, MANAGER;

    @Override
    public String toString() {
        return MessageFormat.format("Position<{0}>", name());
    }
}
