package yuconz;

import java.util.Arrays;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * String validator.
 */
@SuppressWarnings("UtilityClass")
public final class StringValidator {

    private static final Pattern USERNAME_REGEX = compile("^[a-z]{3}\\d{3}$");
    private static final Pattern PHONE_NO_REGEX = compile("^[0-9]{10,11}$");
    private static final Pattern NAME_REGEX = compile("^([A-Z][a-zA-Z]{1,20} ?){1,5}$");
    private static final Pattern POSTCODE_REGEX = compile("^[a-zA-Z0-9]{6,8}$");
    private static final Pattern YEAR_REGEX = compile("^(19|20)\\d{2}$");
    private static final Pattern STAFF_NO_REGEX = compile("^[1-9]\\d*$");

    private StringValidator() {}

    /**
     * All words cap.
     *
     * @param words the words
     * @return true if and only if the conditions aren't violated
     */
    @SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
    public static boolean allWordsCap(final String words) {
        try {
            return Arrays.stream(words.split(" "))
                         .map(w -> w.charAt(0))
                         .allMatch(Character::isUpperCase);
        } catch (final StringIndexOutOfBoundsException ignored) {
            return false;
        }
    }

    /**
     * Sanitise string string.
     *
     * @param mobileNo the mobile no
     * @return the string
     */
    public static String sanitiseString(final String mobileNo) {
        //noinspection DynamicRegexReplaceableByCompiledPattern
        return mobileNo.replace("[- )(_]", "");
    }

    /**
     * Is valid username.
     *
     * @param username the username
     * @return true if and only if the conditions aren't violated
     */
    public static boolean isValidUsername(final String username) {
        return USERNAME_REGEX.asPredicate()
                             .test(username);
    }

    /**
     * Is valid staff number.
     *
     * @param staffNo the staff number
     * @return true if and only if the conditions aren't violated
     */
    public static boolean isValidStaffNo(final String staffNo) {
        return STAFF_NO_REGEX.asPredicate()
                             .test(staffNo);
    }

    /**
     * Is valid year.
     *
     * @param year the year
     * @return true if and only if the conditions aren't violated
     */
    public static boolean isValidYear(final String year) {
        return YEAR_REGEX.asPredicate()
                         .test(year);
    }

    /**
     * Is valid postcode.
     *
     * @param postcode the postcode
     * @return true if and only if the conditions aren't violated
     */
    public static boolean isValidPostcode(final String postcode) {
        return POSTCODE_REGEX.asPredicate()
                             .test(sanitiseString(postcode));
    }

    /**
     * Is valid name.
     *
     * @param name the name
     * @return true if and only if the conditions aren't violated
     */
    public static boolean isValidName(final String name) {
        return NAME_REGEX.asPredicate()
                         .test(name);
    }

    /**
     * Is valid phone number.
     *
     * @param phoneNo the phone no
     * @return true if and only if the conditions aren't violated
     */
    public static boolean isValidPhoneNo(final String phoneNo) {
        return PHONE_NO_REGEX.asPredicate()
                             .test(sanitiseString(phoneNo));
    }
}
