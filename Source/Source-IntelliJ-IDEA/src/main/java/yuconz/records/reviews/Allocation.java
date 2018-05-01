package yuconz.records.reviews;

import java.text.MessageFormat;
import yuconz.exceptions.ReviewersNotDiffException;
import yuconz.exceptions.StaffNoException;
import yuconz.records.Record;

/**
 * Allocation.
 */
@SuppressWarnings("MethodParameterNamingConvention")
public final class Allocation implements Record {

    /**
     * The employee staff number.
     */
    @SuppressWarnings("WeakerAccess")
    public final int empStaffNo, /**
     * the Reviewer 1 staff number.
     */
    reviewer1StaffNo, /**
     * the Reviewer 2 staff number.
     */
    reviewer2StaffNo;

    /**
     * Instantiates a new Allocation.
     *
     * @param empStaffNo employee staff number
     * @param reviewer1StaffNo the reviewer 1 staff number
     * @param reviewer2StaffNo the reviewer 2 staff number
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws ReviewersNotDiffException when reviewers are not two different people
     */
    public Allocation(final int empStaffNo, final int reviewer1StaffNo, final int reviewer2StaffNo) throws StaffNoException, ReviewersNotDiffException {
        // validation
        for (final int n : new int[]{empStaffNo, reviewer1StaffNo, reviewer2StaffNo})
            if (n < 0) throw new StaffNoException();
        if ((reviewer1StaffNo == reviewer2StaffNo) || (empStaffNo == reviewer1StaffNo) || (empStaffNo == reviewer2StaffNo))
            throw new ReviewersNotDiffException();
        this.empStaffNo = empStaffNo;
        this.reviewer1StaffNo = reviewer1StaffNo;
        this.reviewer2StaffNo = reviewer2StaffNo;
    }

    @Override
    public int getStaffNo() {
        return empStaffNo;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Allocation<{0} and {1} to review {2}>", reviewer1StaffNo, reviewer2StaffNo, empStaffNo);
    }
}
