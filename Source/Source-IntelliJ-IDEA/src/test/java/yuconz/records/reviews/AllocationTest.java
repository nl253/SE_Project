package yuconz.records.reviews;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import yuconz.exceptions.ReviewersNotDiffException;
import yuconz.exceptions.ValidationException;
import yuconz.records.MockDataGenerator;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The type Allocation test.
 *
 * @param <A> the type parameter
 */
final class AllocationTest<A> extends MockDataGenerator<A> {

    /**
     * Gets staff number.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void getStaffNo() {
        final Allocation allocation = randAllocation();
        assertNotNull(allocation.getStaffNo());
        assertNotNull(allocation.reviewer1StaffNo);
        assertNotNull(allocation.reviewer2StaffNo);
    }

    /**
     * Negative staff number.
     */
    @RepeatedTest(5)
    @Tag("AnnualPerformanceReviews")
    @Tag("Validation")
    final void negStaffNo() {
        // check different combinations
        assertThrows(ValidationException.class, () -> new Allocation(-randStaffNo(), randStaffNo(), randStaffNo()));
        assertThrows(ValidationException.class, () -> new Allocation(randStaffNo(), -randStaffNo(), randStaffNo()));
        assertThrows(ValidationException.class, () -> new Allocation(randStaffNo(), randStaffNo(), -randStaffNo()));
        assertThrows(ValidationException.class, () -> new Allocation(-randStaffNo(), -randStaffNo(), randStaffNo()));
        assertThrows(ValidationException.class, () -> new Allocation(randStaffNo(), -randStaffNo(), -randStaffNo()));
        assertThrows(ValidationException.class, () -> new Allocation(-randStaffNo(), randStaffNo(), -randStaffNo()));
        assertThrows(ValidationException.class, () -> new Allocation(-randStaffNo(), -randStaffNo(), -randStaffNo()));
    }

    /**
     * The same reviewers.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    @Tag("Validation")
    void theSameReviewers() {
        assertThrows(ReviewersNotDiffException.class, () -> {
            final int r = randStaffNo();
            new Allocation(randStaffNo(), r, r);
        });
    }

    /**
     * Reviewing yourself.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    @Tag("Validation")
    void reviewingYourself() {
        assertThrows(ReviewersNotDiffException.class, () -> {
            final int r = randStaffNo();
            new Allocation(r, randStaffNo(), r);
        });

        assertThrows(ReviewersNotDiffException.class, () -> {
            final int r = randStaffNo();
            new Allocation(r, r, randStaffNo());
        });
    }
}
