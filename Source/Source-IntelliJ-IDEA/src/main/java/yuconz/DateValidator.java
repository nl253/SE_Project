package yuconz;

import java.time.LocalDate;

import static java.time.LocalDate.now;

/**
 * Date validator.
 */
@SuppressWarnings({"WeakerAccess", "UtilityClass"})
public final class DateValidator {

    /**
     * The constant MAX_YEAR.
     */
    public static final int MAX_YEAR = now().getYear();
    /**
     * The constant MIN_YEAR.
     */
    public static final int MIN_YEAR = 1950;
    /**
     * The constant MIN_AGE.
     */
    public static final int MIN_AGE = 15;
    /**
     * The constant MAX_AGE.
     */
    public static final int MAX_AGE = 200;

    private DateValidator() {}

    /**
     * Is too old.
     *
     * @param dob the date of birth
     * @return true if and only if the conditions aren't violated
     */
    public static boolean isTooOld(final LocalDate dob) {
        return (now().getYear() - dob.getYear()) >= MAX_AGE;
    }

    /**
     * Is too young.
     *
     * @param dob the date of birth
     * @return true if and only if the conditions aren't violated
     */
    public static boolean isTooYoung(final LocalDate dob) {
        return (now().getYear() - dob.getYear()) < MIN_AGE;
    }

    /**
     * Is in the future.
     *
     * @param date the date of birth
     * @return true if and only if the conditions aren't violated
     */
    public static boolean isInTheFuture(final LocalDate date) {
        return (now().toEpochDay() - date.toEpochDay()) < 0;
    }

    /**
     * Is valid date of birth.
     *
     * @param dob the date of birth
     * @return true if and only if the conditions aren't violated
     */
    public static boolean isValidDOB(final LocalDate dob) {
        return !isTooYoung(dob) && !isTooOld(dob) && !isInTheFuture(dob);
    }
}
