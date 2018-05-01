package yuconz.records.reviews;

import java.time.LocalDate;
import java.util.Optional;
import yuconz.exceptions.AlreadySignedException;
import yuconz.exceptions.NoSuchRecordException;
import yuconz.exceptions.NotSignedException;
import yuconz.exceptions.NullValueException;
import yuconz.exceptions.ReviewersNotDiffException;
import yuconz.exceptions.StaffNoException;
import yuconz.exceptions.StringFormatException;
import yuconz.records.Record;

import static java.text.MessageFormat.format;
import static yuconz.records.reviews.Recommendation.REMAIN_IN_POST;

/**
 * Review builder.
 */
@SuppressWarnings({"WeakerAccess", "NewExceptionWithoutArguments", "unused", "MethodParameterNamingConvention", "ClassWithTooManyFields", "OptionalUsedAsFieldOrParameterType"})
public final class ReviewBuilder implements Record {

    /**
     * the Employee staff number.
     */
    public final int employeeStaffNo;
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
    public final int reviewer1StaffNo;
    /**
     * The Reviewer 1 name.
     */
    public final String reviewer1Name;
    /**
     * the Reviewer 2 staff number.
     */
    public final int reviewer2StaffNo;
    /**
     * The Reviewer 2 name.
     */
    public final String reviewer2Name;
    /**
     * the objectives and outcomes
     */
    private String objectivesAndAchievements = "";
    /**
     * The Performance summary.
     */
    private String performanceSummary = "";
    /**
     * The Goals and planned objectives.
     */
    private String goalsAndPlannedOutcomes = "";
    /**
     * The Reviewer comments.
     */
    private String reviewerComments = "";
    /**
     * The Recommendation.
     */
    private Recommendation recommendation = REMAIN_IN_POST;
    /**
     * The Employee signed.
     */
    private Optional<LocalDate> employeeSigned = Optional.empty();
    /**
     * The Reviewer 1 signed.
     */
    private Optional<LocalDate> reviewer1Signed = Optional.empty();
    /**
     * The Reviewer 2 signed.
     */
    private Optional<LocalDate> reviewer2Signed = Optional.empty();

    /**
     * Instantiates a new Review builder.
     *
     * @param employeeStaffNo employee staff number
     * @param employeeName employee name
     * @param employeeJobTitle employee job
     * @param reviewer1StaffNo the rev 1 staff number
     * @param reviewer1Name the rev 1 name
     * @param reviewer2StaffNo the rev 2 staff number
     * @param reviewer2Name the rev 2 name
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws ReviewersNotDiffException when reviewers are not two different people
     * @throws NullValueException when a value is null
     * @throws StringFormatException on badly formatted input
     */
    @SuppressWarnings({"ConstructorWithTooManyParameters", "MethodWithMultipleLoops"})
    public ReviewBuilder(final int employeeStaffNo, final String employeeName, final String employeeJobTitle, final int reviewer1StaffNo, final String reviewer1Name, final int reviewer2StaffNo, final String reviewer2Name) throws StaffNoException, ReviewersNotDiffException, NullValueException, StringFormatException {

        for (final int n : new int[]{employeeStaffNo, reviewer1StaffNo, reviewer2StaffNo})
            if (n < 0) throw new StaffNoException();

        for (final String s : new String[]{employeeName, reviewer1Name, reviewer2Name})
            if (s == null)
                throw new NullValueException("Received a null value when creating ReviewBuilder.");
            else if (s.isEmpty())
                throw new StringFormatException("Received an empty string when creating a ReviewBuilder.");

        if (reviewer1StaffNo == reviewer2StaffNo)
            throw new ReviewersNotDiffException();

        this.employeeStaffNo = employeeStaffNo;
        this.employeeName = employeeName;
        this.employeeJobTitle = employeeJobTitle;
        this.reviewer1StaffNo = reviewer1StaffNo;
        this.reviewer1Name = reviewer1Name;
        this.reviewer2StaffNo = reviewer2StaffNo;
        this.reviewer2Name = reviewer2Name;
    }

    /**
     * Create completed review.
     *
     * @return the completed review
     *
     * @throws NotSignedException the not signed exception
     */
    public CompletedReview create() throws NotSignedException {
        if (!didEmployeeSignAndDate() || !didReviewer1Sign() || !didReviewer2Sign())
            throw new NotSignedException();
        return new CompletedReview(employeeStaffNo, employeeName, employeeJobTitle, reviewer1StaffNo, reviewer1Name, reviewer2StaffNo, reviewer2Name, performanceSummary, goalsAndPlannedOutcomes, objectivesAndAchievements, reviewerComments, recommendation);
    }

    @Override
    public int getStaffNo() {
        return employeeStaffNo;
    }

    /**
     * Sign and date.
     *
     * @param staffNo the staff number
     * @throws AlreadySignedException when the review has already been signed
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     */
    public final void signAndDate(final int staffNo) throws AlreadySignedException, NoSuchRecordException {
        if (staffNo == employeeStaffNo)
            if (didEmployeeSignAndDate()) throw new AlreadySignedException();
            else employeeSigned = Optional.of(LocalDate.now());
        else if (staffNo == reviewer1StaffNo)
            if (didReviewer1Sign()) throw new AlreadySignedException();
            else reviewer1Signed = Optional.of(LocalDate.now());
        else if (staffNo == reviewer2StaffNo)
            if (didReviewer2Sign()) throw new AlreadySignedException();
            else reviewer2Signed = Optional.of(LocalDate.now());
        else
            throw new NoSuchRecordException(format("Staff number {0} does not match this record.", staffNo));
    }

    /**
     * Did employee sign and date.
     *
     * @return true if and only if the conditions aren't violated
     */
    public final boolean didEmployeeSignAndDate() {
        return employeeSigned.isPresent();
    }

    /**
     * Did reviewer 1 sign.
     *
     * @return true if and only if the conditions aren't violated
     */
    public final boolean didReviewer1Sign() {
        return reviewer1Signed.isPresent();
    }

    /**
     * Did reviewer 2 sign.
     *
     * @return true if and only if the conditions aren't violated
     */
    public final boolean didReviewer2Sign() {
        return reviewer2Signed.isPresent();
    }

    /**
     * Gets objectives and achievements.
     *
     * @return the objectives and outcomes
     */
    public final String getObjectivesAndAchievements() {
        return objectivesAndAchievements;
    }

    /**
     * Sets objectives and achievements.
     *
     * @param objectivesAndAchievements the objectives and outcomes
     * @return the objectives and outcomes
     */
    public final ReviewBuilder setObjectivesAndAchievements(final String objectivesAndAchievements) throws NullValueException {
        if (objectivesAndAchievements == null)
            throw new NullValueException("Received null value for recommendation.");
        this.objectivesAndAchievements = objectivesAndAchievements;
        return this;
    }

    /**
     * Gets performance summary.
     *
     * @return the performance summary
     */
    public final String getPerformanceSummary() {
        return performanceSummary;
    }

    /**
     * Sets performance summary.
     *
     * @param performanceSummary the performance summary
     * @return the performance summary
     */
    public final ReviewBuilder setPerformanceSummary(final String performanceSummary) throws NullValueException {
        if (performanceSummary == null)
            throw new NullValueException("Received null value for performance summary.");
        this.performanceSummary = performanceSummary.trim();
        return this;
    }

    /**
     * Is signed and dated.
     *
     * @return true if and only if the conditions aren't violated
     */
    public final boolean isSignedAndDated() {
        return didReviewer1Sign() && didReviewer2Sign() && didEmployeeSignAndDate();
    }

    /**
     * Gets goals and planned objectives.
     *
     * @return the goals and planned objectives
     */
    public final String getGoalsAndPlannedOutcomes() {
        return goalsAndPlannedOutcomes;
    }

    /**
     * Sets goals and planned objectives.
     *
     * @param goalsAndPlannedOutcomes the goals and planned objectives
     * @return the goals and planned objectives
     */
    public final ReviewBuilder setGoalsAndPlannedOutcomes(final String goalsAndPlannedOutcomes) throws NullValueException {
        if (goalsAndPlannedOutcomes == null)
            throw new NullValueException("Received null value for goals and planned outcomes.");
        this.goalsAndPlannedOutcomes = goalsAndPlannedOutcomes.trim();
        return this;
    }

    /**
     * Gets reviewer comments.
     *
     * @return the reviewer comments
     */
    public final String getReviewerComments() {
        return reviewerComments;
    }

    /**
     * Sets reviewer comments.
     *
     * @param comments the comments
     * @return the reviewer comments
     */
    public final ReviewBuilder setReviewerComments(final String comments) throws NullValueException {
        if (comments == null)
            throw new NullValueException("Received null value for reviewer comments.");
        reviewerComments = comments.trim();
        return this;
    }

    /**
     * Gets recommendation.
     *
     * @return the recommendation
     */
    public final Recommendation getRecommendation() {
        return recommendation;
    }

    /**
     * Sets recommendation.
     *
     * @param recommendation the recommendation
     * @return the recommendation
     */
    public final ReviewBuilder setRecommendation(final Recommendation recommendation) throws NullValueException {
        if (recommendation == null)
            throw new NullValueException("Received null value for recommendation.");
        this.recommendation = recommendation;
        return this;
    }

    /**
     * Gets reviewer 1 name.
     *
     * @return the reviewer 1 name
     */
    public final String getReviewer1Name() {
        return reviewer1Name;
    }

    /**
     * Gets reviewer 2 name.
     *
     * @return the reviewer 2 name
     */
    public final String getReviewer2Name() {
        return reviewer2Name;
    }


    @Override
    public final String toString() {
        return format("{0}<employee={1}, reviewers={2} and {3}>", getClass().getSimpleName(), employeeStaffNo, reviewer1StaffNo, reviewer2StaffNo);
    }
}
