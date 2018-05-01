package yuconz.records.reviews;

import java.text.MessageFormat;
import yuconz.exceptions.DateException;
import yuconz.records.Record;

import static java.time.LocalDate.now;

/**
 * Current review.
 */
@SuppressWarnings({"WeakerAccess", "PublicField", "ClassWithTooManyFields"})
public final class CompletedReview implements Record {

    /**
     * the Employee staff number.
     */
    public final Integer employeeStaffNo;
    /**
     * The Employee name.
     */
    public final String employeeName;
    /**
     * The Employee job title.
     */
    public final String employeeJobTitle;
    /**
     * the Reviewer 1 staff number.
     */
    public final Integer reviewer1StaffNo;
    /**
     * The Reviewer 1 name.
     */
    public final String reviewer1Name;
    /**
     * The Reviewer 2 name.
     */
    public final String reviewer2Name;
    /**
     * the Reviewer 2 staff number.
     */
    public final Integer reviewer2StaffNo;
    /**
     * The Performance summary.
     */
    public final String performanceSummary;
    /**
     * The Goals and planned outcomes.
     */
    public final String goalsAndPlannedOutcomes;
    /**
     * the objectives and outcomes
     */
    public final String objectivesAndAchievements;
    /**
     * The Reviewer comments.
     */
    public final String reviewerComments;
    /**
     * The Recommendation.
     */
    public final Recommendation recommendation;

    private int yearDone;

    /**
     * Instantiates a new Current review.
     *
     * @param employeeStaffNo the employee staff number
     * @param employeeName the employee name
     * @param employeeJobTitle the employee job title
     * @param reviewer1StaffNo the reviewer 1 staff number
     * @param reviewer1Name the reviewer 1 name
     * @param reviewer2StaffNo the reviewer 2 staff number
     * @param reviewer2Name the reviewer 2 name
     * @param performanceSummary the performance summary
     * @param goalsAndPlannedOutcomes the goals and planned outcomes
     * @param objectivesAndAchievements the objectives and outcomes
     * @param reviewerComments the reviewer comments
     * @param recommendation the recommendation
     */
    @SuppressWarnings({"MethodParameterNamingConvention", "ConstructorWithTooManyParameters"})
    public CompletedReview(final Integer employeeStaffNo, final String employeeName, final String employeeJobTitle, final Integer reviewer1StaffNo, final String reviewer1Name, final Integer reviewer2StaffNo, final String reviewer2Name, final String performanceSummary, final String goalsAndPlannedOutcomes, final String objectivesAndAchievements, final String reviewerComments, final Recommendation recommendation) {
        this.employeeStaffNo = employeeStaffNo;
        this.employeeName = employeeName;
        this.employeeJobTitle = employeeJobTitle;
        this.reviewer1StaffNo = reviewer1StaffNo;
        this.reviewer1Name = reviewer1Name;
        this.reviewer2StaffNo = reviewer2StaffNo;
        this.reviewer2Name = reviewer2Name;
        this.performanceSummary = performanceSummary;
        this.objectivesAndAchievements = objectivesAndAchievements;
        this.goalsAndPlannedOutcomes = goalsAndPlannedOutcomes;
        this.reviewerComments = reviewerComments;
        this.recommendation = recommendation;
        yearDone = now().getYear();
    }

    /**
     * Gets year done.
     *
     * @return the year done
     */
    public int getYearDone() {
        return yearDone;
    }

    /**
     * Sets year done.
     *
     * @param yearDone the year done
     */
    public void setYearDone(final int yearDone) throws DateException {
        if (now().getYear() < yearDone) throw new DateException();
        this.yearDone = yearDone;
    }

    @Override
    public int getStaffNo() {
        return employeeStaffNo;
    }

    @Override
    public String toString() {
        return MessageFormat.format("CompletedReview<employeeStaffNo={0}, reviewer1StaffNo={1}, recommendation={2}, signedOn={3}>", employeeStaffNo, reviewer1StaffNo, recommendation.toString(), yearDone);
    }
}
