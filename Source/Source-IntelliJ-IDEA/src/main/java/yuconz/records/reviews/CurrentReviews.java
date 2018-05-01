package yuconz.records.reviews;

import yuconz.exceptions.NoSuchRecordException;
import yuconz.exceptions.NotSignedException;
import yuconz.exceptions.StaffNoException;
import yuconz.records.AmendableRecords;
import yuconz.records.CreatableRecords;
import yuconz.records.ReadableRecords;

/**
 * The interface Current Reviews.
 */
@SuppressWarnings("InterfaceWithOnlyOneDirectInheritor")
@FunctionalInterface
public interface CurrentReviews extends CreatableRecords<ReviewBuilder>, AmendableRecords<ReviewBuilder>, ReadableRecords<ReviewBuilder> {

    /**
     * End current review.
     *
     * @param empStaffNo employee staff number
     * @return the current review
     *
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws NotSignedException when the annual performance review record has not been signed and dated by both reviewers and the employee
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    default CompletedReview end(final int empStaffNo) throws NoSuchRecordException, NotSignedException, StaffNoException {

        // fetch and save
        final CompletedReview completedReview = read(empStaffNo).create();

        // remove from records
        get().removeIf(review -> review.employeeStaffNo == empStaffNo);

        return completedReview;
    }
}
