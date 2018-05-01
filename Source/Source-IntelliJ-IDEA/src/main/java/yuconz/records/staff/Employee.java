package yuconz.records.staff;

import java.time.LocalDate;
import yuconz.exceptions.DateException;
import yuconz.exceptions.StaffNoException;

import static java.lang.Math.abs;
import static java.time.LocalDate.now;

/**
 * Employee.
 */
@SuppressWarnings("WeakerAccess")
public class Employee extends User {

    /**
     * The constant REVIEW_TIME_WINDOW.
     */
    public static final int REVIEW_TIME_WINDOW = 14;

    /**
     * Instantiates a new Employee.
     *
     * @param staffNo the staff number
     * @param section the section
     * @throws DateException when the date is invalid
     * @throws StaffNoException when the staff number is negative or parsing fails due to bad format
     */
    public Employee(final int staffNo, final Section section) throws StaffNoException, DateException {
        super(staffNo, section);
    }

    /**
     * Instantiates a new Employee.
     *
     * @param staffNo the staff number
     * @param section the section
     * @param employmentStart the employment start
     * @throws DateException when the date is invalid
     * @throws StaffNoException when the staff number is negative or parsing fails due to bad format
     */
    public Employee(final int staffNo, final Section section, final LocalDate employmentStart) throws StaffNoException, DateException {
        super(staffNo, section, employmentStart);
    }


    /**
     * Is review due.
     *
     * @return true if and only if the conditions aren't violated
     */
    public final boolean isReviewDue() {
        return abs(getEmploymentStart().getDayOfYear() - now().getDayOfYear()) < REVIEW_TIME_WINDOW;
    }
}
