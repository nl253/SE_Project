package yuconz.records.staff;

import java.time.LocalDate;
import yuconz.DateValidator;
import yuconz.exceptions.DateException;
import yuconz.exceptions.StaffNoException;
import yuconz.records.Record;

import static java.text.MessageFormat.format;

/**
 * User.
 */
@SuppressWarnings({"ClassWithTooManyDependents", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class User implements Record {

    private final int staffNo;
    private final LocalDate employmentStart;
    private final Section section;


    /**
     * Instantiates a new User.
     *
     * @param staffNo the staff number
     * @param section the section
     * @throws DateException when the date is invalid
     * @throws StaffNoException when the staff number is negative or parsing fails due to bad format
     */
    User(final int staffNo, final Section section) throws StaffNoException, DateException {
        this(staffNo, section, LocalDate.now());
    }

    /**
     * Instantiates a new User.
     *
     * @param staffNo the staff number
     * @param section the section
     * @param employmentStart the employment start
     * @throws DateException when the date is invalid
     * @throws StaffNoException when the staff number is negative or parsing fails due to bad format
     */
    User(final int staffNo, final Section section, final LocalDate employmentStart) throws StaffNoException, DateException {
        if (staffNo < 0) throw new StaffNoException();
        else if (DateValidator.isInTheFuture(employmentStart))
            throw new DateException(format("Date {0} is in the future.", employmentStart.toString()));
        this.staffNo = staffNo;
        this.employmentStart = employmentStart;
        this.section = section;
    }

    @Override
    public final int getStaffNo() {
        return staffNo;
    }

    /**
     * Gets employment start.
     *
     * @return the employment start
     */
    public final LocalDate getEmploymentStart() {
        return employmentStart;
    }

    /**
     * Gets section.
     *
     * @return the section
     */
    public final Section getSection() {
        return section;
    }

    /**
     * Gets job title.
     *
     * @return the job title
     */
    @SuppressWarnings("StringToUpperCaseOrToLowerCaseWithoutLocale")
    public final String getJobTitle() {
        //noinspection DynamicRegexReplaceableByCompiledPattern
        return String.format("%s %s", section.name()
                                             .replace("_", " ")
                                             .toLowerCase(), getClass().getSimpleName()
                                                                       .toLowerCase());
    }

    @SuppressWarnings("DesignForExtension")
    @Override
    public String toString() {
        return format("{0}<staffNo={1}, section={2}>", getClass().getSimpleName(), staffNo, section.toString());
    }
}
