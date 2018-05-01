package yuconz.records.staff;

import java.text.MessageFormat;
import java.time.LocalDate;
import yuconz.exceptions.DateException;
import yuconz.exceptions.StaffNoException;

/**
 * Director.
 */
public final class Director extends User implements Reviewer {

    /**
     * Instantiates a new Director.
     *
     * @param staffNo the staff number
     * @param section the section
     * @throws DateException when the date is invalid
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    public Director(final int staffNo, final Section section) throws StaffNoException, DateException {
        super(staffNo, section);
    }

    /**
     * Instantiates a new Director.
     *
     * @param staffNo the staff number
     * @param section the section
     * @param employmentStart the employment start
     * @throws DateException when the date is invalid
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    public Director(final int staffNo, final Section section, final LocalDate employmentStart) throws StaffNoException, DateException {
        super(staffNo, section, employmentStart);
    }

    @Override
    public String toString() {
        return MessageFormat.format("Director<staffNo={0}, employmentStart={1}>", getStaffNo(), getEmploymentStart());
    }
}
