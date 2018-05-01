package yuconz.records.reviews;

import java.util.Objects;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import yuconz.exceptions.DateException;
import yuconz.records.MockDataGenerator;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The type Completed review test.
 *
 * @param <A> the type parameter
 */
final class CompletedReviewTest<A> extends MockDataGenerator<A> {

    /**
     * Gets year done.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void getYearDone() {
        assertNotNull(Objects.requireNonNull(randCompletedReview())
                             .getYearDone());
    }

    /**
     * Sets year done.
     *
     * @throws DateException when the date is invalid
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void setYearDone() throws DateException {
        Objects.requireNonNull(randCompletedReview())
               .setYearDone(now().getYear());
    }

    /**
     * Sets year done to future year.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void setYearDoneToFutureYear() {
        assertThrows(DateException.class, () -> Objects.requireNonNull(randCompletedReview())
                                                       .setYearDone(now().plusYears(1 + RAND.nextInt(10))
                                                                         .getYear()));
    }

    /**
     * Gets staff number.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void getStaffNo() {
        assertNotNull(Objects.requireNonNull(randCompletedReview())
                             .getStaffNo());
    }


    /**
     * Gets objectives and achievements.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void getObjectivesAndAchievements() {
        assertNotNull(Objects.requireNonNull(randCompletedReview()).objectivesAndAchievements);
    }

    /**
     * Gets performance summary.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void getPerformanceSummary() {
        assertNotNull(Objects.requireNonNull(randCompletedReview()).performanceSummary);
    }

    /**
     * Gets goals and planned outcomes.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void getGoalsAndPlannedOutcomes() {
        assertNotNull(Objects.requireNonNull(randCompletedReview()).goalsAndPlannedOutcomes);
    }

    /**
     * Gets reviewer comments.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void getReviewerComments() {
        assertNotNull(Objects.requireNonNull(randCompletedReview()).reviewerComments);
    }

    /**
     * Gets recommendation.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void getRecommendation() {
        assertNotNull(Objects.requireNonNull(randCompletedReview()).recommendation);
    }

    /**
     * Gets participants names.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void getParticipantsNames() {
        assertNotNull(Objects.requireNonNull(randCompletedReview()).employeeName);
        assertNotNull(Objects.requireNonNull(randCompletedReview()).reviewer1Name);
        assertNotNull(Objects.requireNonNull(randCompletedReview()).reviewer2Name);
    }
}
