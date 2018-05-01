package yuconz.records.reviews;

import java.util.Objects;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import yuconz.exceptions.AlreadySignedException;
import yuconz.exceptions.NoSuchRecordException;
import yuconz.exceptions.NotSignedException;
import yuconz.exceptions.StaffNoException;
import yuconz.exceptions.ValidationException;
import yuconz.records.MockDataGenerator;

import static java.lang.System.exit;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Review builder test.
 *
 * @param <A> the type parameter
 */
final class ReviewBuilderTest<A> extends MockDataGenerator<A> {


    /**
     * Create with neg staff nos.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    @Tag("Validation")
    void createWithNegStaffNos() {
        // you cannot make a completed review with negative staff numbers
        // check different combinations
        assertThrows(StaffNoException.class, () -> new ReviewBuilder(-randStaffNo(), randFirstName(), randCapWord(), randStaffNo(), randFirstName(), randStaffNo(), randFirstName()));
        assertThrows(StaffNoException.class, () -> new ReviewBuilder(randStaffNo(), randFirstName(), randCapWord(), -randStaffNo(), randFirstName(), randStaffNo(), randFirstName()));
        assertThrows(StaffNoException.class, () -> new ReviewBuilder(randStaffNo(), randFirstName(), randCapWord(), randStaffNo(), randFirstName(), -randStaffNo(), randFirstName()));
        assertThrows(StaffNoException.class, () -> new ReviewBuilder(-randStaffNo(), randFirstName(), randCapWord(), -randStaffNo(), randFirstName(), randStaffNo(), randFirstName()));
        assertThrows(StaffNoException.class, () -> new ReviewBuilder(-randStaffNo(), randFirstName(), randCapWord(), randStaffNo(), randFirstName(), -randStaffNo(), randFirstName()));
        assertThrows(StaffNoException.class, () -> new ReviewBuilder(-randStaffNo(), randFirstName(), randCapWord(), -randStaffNo(), randFirstName(), -randStaffNo(), randFirstName()));
    }

    /**
     * Create with null names.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    @Tag("Validation")
    void createWithNullNames() {
        // you cannot make a completed review with null participants' names
        // check different combinations
        assertThrows(ValidationException.class, () -> new ReviewBuilder(randStaffNo(), null, randCapWord(), randStaffNo(), randFirstName(), randStaffNo(), randFirstName()));
        assertThrows(ValidationException.class, () -> new ReviewBuilder(randStaffNo(), randFirstName(), randCapWord(), randStaffNo(), null, randStaffNo(), randFirstName()));
        assertThrows(ValidationException.class, () -> new ReviewBuilder(randStaffNo(), randFirstName(), randCapWord(), randStaffNo(), randFirstName(), randStaffNo(), null));
        assertThrows(ValidationException.class, () -> new ReviewBuilder(randStaffNo(), null, randCapWord(), randStaffNo(), null, randStaffNo(), null));
        assertThrows(ValidationException.class, () -> new ReviewBuilder(randStaffNo(), null, randCapWord(), randStaffNo(), randFirstName(), randStaffNo(), null));
        assertThrows(ValidationException.class, () -> new ReviewBuilder(randStaffNo(), null, randCapWord(), randStaffNo(), null, randStaffNo(), randFirstName()));
        assertThrows(ValidationException.class, () -> new ReviewBuilder(randStaffNo(), randFirstName(), randCapWord(), randStaffNo(), null, randStaffNo(), null));
    }

    /**
     * Create with empty names.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    @Tag("Validation")
    void createWithEmptyNames() {
        // you cannot make a completed review with empty participants' names
        // check different combinations
        assertThrows(ValidationException.class, () -> new ReviewBuilder(randStaffNo(), "", randCapWord(), randStaffNo(), randFirstName(), randStaffNo(), randFirstName()));
        assertThrows(ValidationException.class, () -> new ReviewBuilder(randStaffNo(), randFirstName(), randCapWord(), randStaffNo(), "", randStaffNo(), randFirstName()));
        assertThrows(ValidationException.class, () -> new ReviewBuilder(randStaffNo(), randFirstName(), randCapWord(), randStaffNo(), randFirstName(), randStaffNo(), ""));
        assertThrows(ValidationException.class, () -> new ReviewBuilder(randStaffNo(), "", randCapWord(), randStaffNo(), "", randStaffNo(), ""));
        assertThrows(ValidationException.class, () -> new ReviewBuilder(randStaffNo(), "", randCapWord(), randStaffNo(), randFirstName(), randStaffNo(), ""));
        assertThrows(ValidationException.class, () -> new ReviewBuilder(randStaffNo(), "", randCapWord(), randStaffNo(), "", randStaffNo(), randFirstName()));
        assertThrows(ValidationException.class, () -> new ReviewBuilder(randStaffNo(), randFirstName(), randCapWord(), randStaffNo(), "", randStaffNo(), ""));
    }


    /**
     * Create signed.
     *
     * @throws NotSignedException the not signed exception
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    @Tag("Validation")
    void createSigned() throws NotSignedException {
        // shouldn't throw an exception as I am signing for all participants
        final ReviewBuilder reviewBuilder = Objects.requireNonNull(randReviewBuilder());
        try {
            reviewBuilder.signAndDate(reviewBuilder.employeeStaffNo);
            reviewBuilder.signAndDate(reviewBuilder.reviewer1StaffNo);
            reviewBuilder.signAndDate(reviewBuilder.reviewer2StaffNo);
            assertNotNull(reviewBuilder.create());
        } catch (final AlreadySignedException | NoSuchRecordException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    /**
     * Create unsigned.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    @Tag("Validation")
    void createUnsigned() {
        assertThrows(NotSignedException.class, () -> randReviewBuilder().create());
    }


    /**
     * Gets staff number.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void getStaffNo() {
        // generate a mock review builder
        final ReviewBuilder reviewBuilder = Objects.requireNonNull(randReviewBuilder());
        assertNotNull(reviewBuilder.employeeStaffNo);
        assertNotNull(reviewBuilder.reviewer1StaffNo);
        assertNotNull(reviewBuilder.reviewer2StaffNo);
    }

    /**
     * Sign and date.
     *
     * @throws AlreadySignedException when the review has already been signed
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    @Tag("Validation")
    void signAndDate() throws AlreadySignedException, NoSuchRecordException {
        // generate a mock review builder
        final ReviewBuilder reviewBuilder = Objects.requireNonNull(randReviewBuilder());
        reviewBuilder.signAndDate(reviewBuilder.employeeStaffNo);
        reviewBuilder.signAndDate(reviewBuilder.reviewer1StaffNo);
        reviewBuilder.signAndDate(reviewBuilder.reviewer2StaffNo);
        // should be true as I signed for all participants
        assertTrue(reviewBuilder.isSignedAndDated());
    }

    /**
     * Sign more than once.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    @Tag("Validation")
    void signMoreThanOnce() {

        assertThrows(AlreadySignedException.class, () -> {
            // generate a mock review builder
            final ReviewBuilder reviewBuilder = Objects.requireNonNull(randReviewBuilder());
            reviewBuilder.signAndDate(Objects.requireNonNull(reviewBuilder).employeeStaffNo);
            reviewBuilder.signAndDate(reviewBuilder.employeeStaffNo);
        });

        assertThrows(AlreadySignedException.class, () -> {
            // generate a mock review builder
            final ReviewBuilder reviewBuilder = Objects.requireNonNull(randReviewBuilder());
            reviewBuilder.signAndDate(reviewBuilder.reviewer1StaffNo);
            reviewBuilder.signAndDate(reviewBuilder.reviewer1StaffNo);
        });

        assertThrows(AlreadySignedException.class, () -> {
            // generate a mock review builder
            final ReviewBuilder reviewBuilder = Objects.requireNonNull(randReviewBuilder());
            reviewBuilder.signAndDate(reviewBuilder.reviewer2StaffNo);
            reviewBuilder.signAndDate(reviewBuilder.reviewer2StaffNo);
        });
    }

    /**
     * Sign with invalid staff number.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    @Tag("Validation")
    void signWithInvalidStaffNo() {
        assertThrows(ValidationException.class, () -> {

            int staffNo = randStaffNo();

            // generate a mock review builder
            final ReviewBuilder reviewBuilder = Objects.requireNonNull(randReviewBuilder());

            // make sure it doesn't match any of the participants' staff numbers
            while ((reviewBuilder.employeeStaffNo == staffNo) || (reviewBuilder.reviewer1StaffNo == staffNo) || (reviewBuilder.reviewer2StaffNo == staffNo))
                staffNo = randStaffNo();

            // sign using a random staff number
            reviewBuilder.signAndDate(staffNo);
        });
    }

    /**
     * Did sign and date.
     *
     * @throws AlreadySignedException when the review has already been signed
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    @Tag("Validation")
    void didSignAndDate() throws AlreadySignedException, NoSuchRecordException {

        // generate a mock review builder
        final ReviewBuilder reviewBuilder = Objects.requireNonNull(randReviewBuilder());

        reviewBuilder.signAndDate(reviewBuilder.employeeStaffNo);
        // see if recorded signing of the review properly
        assertTrue(reviewBuilder.didEmployeeSignAndDate());

        reviewBuilder.signAndDate(reviewBuilder.reviewer1StaffNo);
        // see if recorded signing of the review properly
        assertTrue(reviewBuilder.didReviewer1Sign());

        reviewBuilder.signAndDate(reviewBuilder.reviewer2StaffNo);
        // see if recorded signing of the review properly
        assertTrue(reviewBuilder.didReviewer2Sign());
    }

    /**
     * Saves stored data.
     *
     * @throws ValidationException the validation exception
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void savesStoredData() throws ValidationException {

        // generate a mock review builder
        final ReviewBuilder reviewBuilder = Objects.requireNonNull(randReviewBuilder());
        String paragraph;

        // set value and check that it gets saved ie is retrievable
        paragraph = randomParagraph();
        reviewBuilder.setObjectivesAndAchievements(paragraph);
        assertNotNull(reviewBuilder.getObjectivesAndAchievements());
        assertEquals(reviewBuilder.getObjectivesAndAchievements(), paragraph);

        // set value and check that it gets saved ie is retrievable
        paragraph = randomParagraph();
        reviewBuilder.setPerformanceSummary(paragraph);
        assertNotNull(reviewBuilder.getPerformanceSummary());
        assertEquals(reviewBuilder.getPerformanceSummary(), paragraph);

        // set value and check that it gets saved ie is retrievable
        paragraph = randomParagraph();
        reviewBuilder.setGoalsAndPlannedOutcomes(paragraph);
        assertNotNull(reviewBuilder.getGoalsAndPlannedOutcomes());
        assertEquals(reviewBuilder.getGoalsAndPlannedOutcomes(), paragraph);

        // set value and check that it gets saved ie is retrievable
        paragraph = randomParagraph();
        reviewBuilder.setReviewerComments(paragraph);
        assertNotNull(reviewBuilder.getReviewerComments());
        assertEquals(reviewBuilder.getReviewerComments(), paragraph);

        // set value and check that it gets saved ie is retrievable
        final Recommendation recommendation = randRecommendation();
        reviewBuilder.setRecommendation(recommendation);
        assertNotNull(reviewBuilder.getRecommendation());
    }

    /**
     * Stores participants names.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    @Tag("Validation")
    void storesParticipantsNames() {
        // generate a mock review builder
        final ReviewBuilder reviewBuilder = Objects.requireNonNull(randReviewBuilder());

        // reviewers names are stored in reviews
        assertNotNull(reviewBuilder.employeeName);
        assertNotNull(reviewBuilder.getReviewer1Name());
        assertNotNull(reviewBuilder.getReviewer2Name());

        // they aren't null
        assertNotEquals(reviewBuilder.employeeName, null);
        assertNotEquals(reviewBuilder.reviewer1Name, null);
        assertNotEquals(reviewBuilder.reviewer2Name, null);

        // or empty
        assertNotEquals(reviewBuilder.employeeName, "");
        assertNotEquals(reviewBuilder.reviewer1Name, "");
        assertNotEquals(reviewBuilder.reviewer2Name, "");
    }

    /**
     * Sets null values.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void setNullValues() {
        // generate a mock review builder
        final ReviewBuilder reviewBuilder = Objects.requireNonNull(randReviewBuilder());
        // null values cannot be set
        assertThrows(ValidationException.class, () -> reviewBuilder.setPerformanceSummary(null));
        assertThrows(ValidationException.class, () -> reviewBuilder.setObjectivesAndAchievements(null));
        assertThrows(ValidationException.class, () -> reviewBuilder.setReviewerComments(null));
        assertThrows(ValidationException.class, () -> reviewBuilder.setGoalsAndPlannedOutcomes(null));
        assertThrows(ValidationException.class, () -> reviewBuilder.setRecommendation(null));
    }
}
