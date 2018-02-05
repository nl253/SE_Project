package uk.ac.kent;

import uk.ac.kent.models.people.User;

/**
 * @author Norbert
 */

public final class Authorization {

    private Authorization() {}

    static boolean authorizationCheck(final User user) {
        return false;
    }
}
