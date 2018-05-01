package yuconz;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import yuconz.exceptions.AlreadySignedException;
import yuconz.exceptions.AuthorisationException;
import yuconz.exceptions.NoSuchRecordException;
import yuconz.exceptions.NotLoggedInException;
import yuconz.exceptions.NotSignedException;
import yuconz.exceptions.NullValueException;
import yuconz.exceptions.RecordExistsException;
import yuconz.exceptions.ReviewNotDueException;
import yuconz.exceptions.ReviewerAssignedException;
import yuconz.exceptions.ReviewersNotAssignedException;
import yuconz.exceptions.ReviewersNotDiffException;
import yuconz.exceptions.StaffNoException;
import yuconz.exceptions.StringFormatException;
import yuconz.records.Record;
import yuconz.records.personal.MockPersonalDetails;
import yuconz.records.personal.PersonalDetailRecord;
import yuconz.records.personal.PersonalDetails;
import yuconz.records.personal.ValidatingPersonalDetailsBuilder;
import yuconz.records.reviews.Allocation;
import yuconz.records.reviews.Allocations;
import yuconz.records.reviews.CompletedReview;
import yuconz.records.reviews.CompletedReviews;
import yuconz.records.reviews.CurrentReviews;
import yuconz.records.reviews.MockAllocations;
import yuconz.records.reviews.MockCompletedReviews;
import yuconz.records.reviews.MockCurrentReviews;
import yuconz.records.reviews.ReviewBuilder;
import yuconz.records.staff.Employee;
import yuconz.records.staff.MockStaffDetails;
import yuconz.records.staff.Reviewer;
import yuconz.records.staff.StaffDetails;
import yuconz.records.staff.User;

import static java.lang.System.exit;
import static java.text.MessageFormat.format;
import static java.util.logging.Logger.getAnonymousLogger;
import static java.util.stream.Collectors.toList;

/**
 * HR database.
 *
 * @param <U> the type parameter
 */
@SuppressWarnings({"PublicMethodNotExposedInInterface", "AlibabaClassNamingShouldBeCamel", "ClassHasNoToStringMethod", "OverlyCoupledClass", "FeatureEnvy", "WeakerAccess", "PackageVisibleField", "BooleanMethodNameMustStartWithQuestion", "ClassWithTooManyMethods", "Singleton"})
public final class HRDatabase<U extends User> {

    private static HRDatabase<? extends User> self;
    /**
     * The Completed reviews.
     */
    final CompletedReviews completedReviews;
    /**
     * The Allocations.
     */
    final Allocations allocations;
    /**
     * The Current reviews.
     */
    final CurrentReviews currentReviews;
    /**
     * The Staff details.
     */
    final StaffDetails<U> staffDetails;
    /**
     * The Personal details.
     */
    final PersonalDetails personalDetails;

    @SuppressWarnings("StaticVariableOfConcreteClass")
    private final Authorisation<? extends User> authorisation;
    private final Logger log;

    private HRDatabase() {
        staffDetails = MockStaffDetails.getInstance();
        personalDetails = MockPersonalDetails.getInstance();
        completedReviews = MockCompletedReviews.getInstance();
        currentReviews = MockCurrentReviews.getInstance();
        allocations = MockAllocations.getInstance();
        authorisation = Authorisation.getInstance();
        log = getAnonymousLogger();
    }

    /**
     * Gets instance.
     *
     * @param <U> the type parameter
     * @return the instance
     */
    @SuppressWarnings({"NonThreadSafeLazyInitialization", "StaticVariableUsedBeforeInitialization"})
    public static <U extends User> HRDatabase<U> getInstance() {
        if (self == null) self = new HRDatabase<>();
        return (HRDatabase<U>) self;
    }

    /**
     * Read personal details personal detail record.
     *
     * @param staffNo the staff number
     * @return the personal detail record
     *
     * @throws NoSuchRecordException when the record does not exist
     * @throws NotLoggedInException when the user is not logged in while trying to perform the action
     * @throws AuthorisationException when the user lacks the privileges necessary to perform the action (this is dependent on the permission level with which you log in)
     * @throws StaffNoException when the staff number is negative or parsing fails
     */
    @SuppressWarnings("MethodWithTooExceptionsDeclared")
    public PersonalDetailRecord readPersonalDetails(final int staffNo) throws NoSuchRecordException, NotLoggedInException, AuthorisationException, StaffNoException {

        // contact authorisation to check if permission lvl is sufficient
        authorisation.authReadPersonalDetails(staffNo);

        // first check if record exists
        if (personalDetails.hasRecord(staffNo)) {

            // will throw an error if there is no such record to read
            // don't success-log yet
            final PersonalDetailRecord record = personalDetails.read(staffNo);

            logSuccess(format("accessed {0}'s {1}", staffNo, record.toString()));
            return record;

            // the record doesn't exist
        } else {
            final String msg = msgFailure(format("read personal details", staffNo), format("no personal details record", staffNo));
            log.warning(msg);
            throw new NoSuchRecordException(msg);
        }
    }

    /**
     * Amend personal details.
     *
     * @param staffNo the staff number
     * @param amendedRecord the amended record
     * @throws NoSuchRecordException when the record does not exist
     * @throws NotLoggedInException when the user is not logged in while trying to perform the action
     * @throws AuthorisationException when the user lacks the privileges necessary to perform the action (this is dependent on the permission level with which you log in)
     * @throws StaffNoException when the staff number is negative or parsing fails
     */
    @SuppressWarnings("MethodWithTooExceptionsDeclared")
    public void amendPersonalDetails(final int staffNo, final PersonalDetailRecord amendedRecord) throws NoSuchRecordException, NotLoggedInException, AuthorisationException, StaffNoException {

        // contact authorisation to check if permission lvl is sufficient
        authorisation.authoriseAmendPersonalDetails(staffNo);

        // will throw an exception when there is nothing to amend
        personalDetails.amend(staffNo, amendedRecord);

        logSuccess(format("amended a personal details record for: {0}", staffNo));
    }


    /**
     * Create personal details.
     *
     * @param staffNo the staff number
     * @throws RecordExistsException when the record exists
     * @throws StaffNoException when the staff number is negative or parsing fails
     * @throws NotLoggedInException when the user is not logged in while trying to perform the action
     * @throws AuthorisationException when the user lacks the privileges necessary to perform the action (this is dependent on the permission level with which you log in)
     */
    @SuppressWarnings("MethodWithTooExceptionsDeclared")
    public void createPersonalDetails(final int staffNo) throws RecordExistsException, StaffNoException, NotLoggedInException, AuthorisationException {

        // validation
        if (staffNo < 0) throw new StaffNoException();

        // contact authorisation to check if permission lvl is sufficient
        authorisation.authoriseCreatePersonalDetails();

        // this "create" might fail if the record exists
        personalDetails.create(new ValidatingPersonalDetailsBuilder(staffNo).create());

        logSuccess(format("created new personal details record for: {0}", staffNo));
    }

    /**
     * Read review base review builder.
     *
     * @param empStaffNo employee staff no
     * @return the base review builder
     *
     * @throws NoSuchRecordException when the record does not exist
     * @throws NotLoggedInException when the user is not logged in while trying to perform the action
     * @throws AuthorisationException when the user lacks the privileges necessary to perform the action (this is dependent on the permission level with which you log in)
     * @throws StaffNoException when the staff number is negative or parsing fails
     */
    @SuppressWarnings("MethodWithTooExceptionsDeclared")
    public ReviewBuilder readCurrentReview(final int empStaffNo) throws NoSuchRecordException, NotLoggedInException, AuthorisationException, StaffNoException {

        // contact authorisation to check if permission lvl is sufficient
        authorisation.authoriseReadCurrentReview(empStaffNo);

        // try to fetch (this might fail so no success logging yet)
        final ReviewBuilder review = currentReviews.read(empStaffNo);

        logSuccess(format("read {0}'s current {1}.", empStaffNo, review.toString()));
        return review;
    }

    /**
     * Amend review.
     *
     * @param empStaffNo employee staff no
     * @param amendedRev the amended review
     * @throws NoSuchRecordException when the record does not exist
     * @throws AuthorisationException when the user lacks the privileges necessary to perform the action (this is dependent on the permission level with which you log in)
     * @throws NotLoggedInException when the user is not logged in while trying to perform the action
     * @throws StaffNoException when the staff number is negative or parsing fails
     * @throws AlreadySignedException the already signed exception
     */
    @SuppressWarnings("MethodWithTooExceptionsDeclared")
    public void amendCurrentReview(final int empStaffNo, final ReviewBuilder amendedRev) throws NoSuchRecordException, AuthorisationException, NotLoggedInException, StaffNoException, AlreadySignedException {
        // @formatter:off

        // contact authorisation to check if permission lvl is sufficient
        authorisation.authoriseAmendCurrentReview(empStaffNo);

        // this "read" might fail
        // already signed and dated
        if (currentReviews.read(empStaffNo).didEmployeeSignAndDate()) {
            final String msg = msgFailure("amend review", "already signed and dated");
            log.warning(msg);
            throw new AlreadySignedException(msg);
        }

        // all checks passed
        else {
            currentReviews.amend(empStaffNo, amendedRev);
            logSuccess(format("amended a review record for: {0}", empStaffNo));
        }
        // @formatter:on
    }


    /**
     * Create review.
     *
     * @param empStaffNo employee staff no
     * @throws NotLoggedInException when the user is not logged in while trying to perform the action
     * @throws ReviewersNotAssignedException the reviewers not assigned exception
     * @throws AuthorisationException when the user lacks the privileges necessary to perform the action (this is dependent on the permission level with which you log in)
     * @throws NoSuchRecordException when the record does not exist
     * @throws StaffNoException when the staff number is negative or parsing fails
     * @throws RecordExistsException when the record exists
     * @throws ReviewersNotDiffException when reviewers are not two different people
     * @throws NullValueException
     * @throws StringFormatException
     */
    @SuppressWarnings({"MethodWithTooExceptionsDeclared", "OverlyCoupledMethod", "NewExceptionWithoutArguments"})
    public void createReview(final int empStaffNo) throws NotLoggedInException, AuthorisationException, StaffNoException, ReviewersNotAssignedException, NoSuchRecordException, RecordExistsException, ReviewersNotDiffException, NullValueException, StringFormatException {

        // validation
        if (empStaffNo < 0) throw new StaffNoException();

        // contact authorisation to check if permission lvl is sufficient
        authorisation.authoriseCreateReview();

        // make sure HR employee allocated reviewers
        if (!allocations.hasRecord(empStaffNo))
            throw new ReviewersNotAssignedException();

        // fetch the allocation
        final Allocation allocation = allocations.read(empStaffNo);

        // fetch the employee details
        final Employee employee = staffDetails.findEmployee(empStaffNo);

        // fetch personal details
        final PersonalDetailRecord employeePersonalDetails = personalDetails.read(empStaffNo);
        final PersonalDetailRecord reviewer1PersonalDetails = personalDetails.read(allocation.reviewer1StaffNo);
        final PersonalDetailRecord reviewer2PersonalDetails = personalDetails.read(allocation.reviewer2StaffNo);

        // this will thrown an exception is such a "current review" is already stored
        // @formatter:off
        currentReviews.create(new ReviewBuilder(
                empStaffNo,
                format("{0} {1}", employeePersonalDetails.firstName, employeePersonalDetails.lastName),
                employee.getJobTitle(),
                allocation.reviewer1StaffNo,
                format("{0} {1}", reviewer1PersonalDetails.firstName, reviewer1PersonalDetails.lastName),
                allocation.reviewer2StaffNo,
                format("{0} {1}", reviewer2PersonalDetails.firstName, reviewer2PersonalDetails.lastName)
        ));
        // @formatter:on

    }


    /**
     * Sign review employee.
     *
     * @param empStaffNo employee staff no
     * @throws NoSuchRecordException when the record does not exist
     * @throws AlreadySignedException the already signed exception
     * @throws NotLoggedInException when the user is not logged in while trying to perform the action
     * @throws StaffNoException when the staff number is negative or parsing fails
     * @throws ReviewersNotDiffException when reviewers are not two different people
     * @throws AuthorisationException when the user lacks the privileges necessary to perform the action (this is dependent on the permission level with which you log in)
     */
    @SuppressWarnings({"unused", "MethodWithTooExceptionsDeclared"})
    public void signCurrentReviewEmployee(final int empStaffNo) throws NoSuchRecordException, AlreadySignedException, NotLoggedInException, StaffNoException, ReviewersNotDiffException, AuthorisationException {

        // validation
        if (empStaffNo < 0) throw new StaffNoException();

        // contact authorisation to check if permission lvl is sufficient
        authorisation.authoriseSignCurrentReviewEmployee();

        // fetch *current* review
        final ReviewBuilder currentReview = currentReviews.read(empStaffNo);

        // employee signs
        currentReview.signAndDate(empStaffNo);

        logSuccess(format("signed {0} for employee no {1}", currentReview.toString(), empStaffNo));

        // see if ready to mark as read-only
        tryMarkReadOnly(currentReview);
    }

    /**
     * Sign review reviewer.
     *
     * @param empStaffNo employee staff no
     * @param revStaffNo the rev staff number
     * @throws NoSuchRecordException when the record does not exist
     * @throws AlreadySignedException the already signed exception
     * @throws NotLoggedInException when the user is not logged in while trying to perform the action
     * @throws StaffNoException when the staff number is negative or parsing fails
     * @throws AuthorisationException when the user lacks the privileges necessary to perform the action (this is dependent on the permission level with which you log in)
     */
    @SuppressWarnings({"unused", "NewExceptionWithoutArguments", "MethodWithTooExceptionsDeclared"})
    public void signCurrentReviewReviewer(final int empStaffNo, final int revStaffNo) throws NoSuchRecordException, AlreadySignedException, NotLoggedInException, StaffNoException, AuthorisationException {

        // validate staff numbers
        if ((empStaffNo < 0) || (revStaffNo < 0)) throw new StaffNoException();

        // contact authorisation to check if permission lvl is sufficient
        authorisation.authoriseSignCurrentReviewReviewer(empStaffNo);

        // fetch *current* review
        final ReviewBuilder currentReview = currentReviews.read(empStaffNo);

        // reviewer signs
        currentReview.signAndDate(revStaffNo);

        // see if ready to mark as read-only
        tryMarkReadOnly(currentReview);
    }

    /**
     * Read past review current review.
     *
     * @param empStaffNo employee staff no
     * @param year the year
     * @return the current review
     *
     * @throws NoSuchRecordException when the record does not exist
     * @throws AuthorisationException
     * @throws NotLoggedInException
     * @throws StaffNoException
     */
    public CompletedReview readCompletedReview(final int empStaffNo, final int year) throws NoSuchRecordException, NotLoggedInException, StaffNoException, AuthorisationException {
        // contact authorisation to check if permission lvl is sufficient
        authorisation.authoriseReadCompletedReview(empStaffNo);
        return completedReviews.read(empStaffNo, year);
    }

    /**
     * Read past review collection.
     *
     * @param empStaffNo employee staff no
     * @return the collection
     *
     * @throws NotLoggedInException when the user is not logged in while trying to perform the action
     * @throws AuthorisationException when the user lacks the privileges necessary to perform the action (this is dependent on the permission level with which you log in)
     * @throws NoSuchRecordException when the record does not exist
     * @throws StaffNoException when the staff number is negative or parsing fails
     */
    @SuppressWarnings({"WeakerAccess", "MethodWithTooExceptionsDeclared"})
    public Collection<CompletedReview> readCompletedReviews(final int empStaffNo) throws NotLoggedInException, AuthorisationException, NoSuchRecordException, StaffNoException {

        // validation
        if (empStaffNo < 0) throw new StaffNoException();

        // contact authorisation to check if permission lvl is sufficient
        authorisation.authoriseReadCompletedReviews(empStaffNo);

        try {
            // will throw an exception if there is no such record to read
            final Collection<CompletedReview> reviews = completedReviews.read(empStaffNo);

            logSuccess(format("read {0}''s past completed review records", empStaffNo));
            return reviews;

        } catch (final NoSuchRecordException ignored) {
            final String msg = msgFailure(format("read {0}''s past completed review records", empStaffNo), format("no past reviews for employee with staff number: {0}", empStaffNo));
            log.warning(msg);
            throw new NoSuchRecordException(msg);
        }
    }


    /**
     * Allocate reviewers.
     *
     * @param empStaffNo employee staff no
     * @param rev1StaffNo the rev 1 staff number
     * @param rev2StaffNo the rev 2 staff number
     * @throws NotLoggedInException when the user is not logged in while trying to perform the action
     * @throws AuthorisationException when the user lacks the privileges necessary to perform the action (this is dependent on the permission level with which you log in)
     * @throws StaffNoException when the staff number is negative or parsing fails
     * @throws RecordExistsException when the record exists
     * @throws NoSuchRecordException when the record does not exist
     * @throws ReviewerAssignedException the reviewer assigned exception
     * @throws ReviewNotDueException the review not due exception
     * @throws ReviewersNotDiffException when reviewers are not two different people
     */
    @SuppressWarnings("MethodWithTooExceptionsDeclared")
    public void allocateReviewers(final int empStaffNo, final int rev1StaffNo, final int rev2StaffNo) throws NotLoggedInException, AuthorisationException, StaffNoException, RecordExistsException, NoSuchRecordException, ReviewerAssignedException, ReviewNotDueException, ReviewersNotDiffException {

        if ((empStaffNo < 0) || (rev1StaffNo < 0) || (rev2StaffNo < 0))
            throw new StaffNoException();

        // contact authorisation to check if permission level is sufficient
        authorisation.authoriseAllocateReviewers();

        // @formatter:off
        // review not due for this employee
        if (!staffDetails.findEmployee(empStaffNo).isReviewDue()) {
            final String msg = msgFailure("allocate reviewers", "review is not due");
            log.warning(msg);
            throw new ReviewNotDueException(msg);

        // review done even if due
        } else if (completedReviews.reviewedThisYear(empStaffNo)) {
            final String msg = msgFailure("allocate reviewers", format("already reviewed this year", empStaffNo));
            log.warning(msg);
            throw new RecordExistsException(msg);

        // already allocated reviewers for this employee
        } else if (allocations.hasRecord(empStaffNo)) {
            final String msg = msgFailure("allocate reviewers", format("already allocated two reviewers", empStaffNo));
            log.warning(msg);
            throw new ReviewerAssignedException(msg);
        }

        // pass all checks - make a new allocation record
        allocations.create(new Allocation(empStaffNo, rev1StaffNo, rev2StaffNo));

        logSuccess(format("assigned {0} and {1} to review {2}", rev1StaffNo, rev2StaffNo, empStaffNo));
        // @formatter:on
    }


    /**
     * List unallocated reviewees list.
     *
     * @param revStaffNo the rev staff number
     * @return the list
     *
     * @throws StaffNoException when the staff number is negative or parsing fails
     */
    public List<String> listUnallocatedReviewees(final int revStaffNo) throws StaffNoException {
        if (revStaffNo < 0) throw new StaffNoException();

        /* String will be in format "<staffNo> <firstName> <lastName>" with the aim of
        exposing as little personal data as possible while still being able to identify
        yuconz employees using names and staffNo */
        return currentReviews.get()
                             .stream()
                             .filter(review -> (review.reviewer1StaffNo == revStaffNo) || (review.reviewer2StaffNo == revStaffNo))
                             .filter(review -> (review.reviewer1StaffNo == revStaffNo) ? !review.didReviewer1Sign() : !review.didReviewer2Sign())
                             .map(Record::getStaffNo)
                             .filter(personalDetails::hasRecord)
                             .map(this::getStaffNoAndName)
                             .collect(toList());
    }


    /**
     * List unallocated reviewees list.
     *
     * @return the list
     */
    public List<String> listUnallocatedReviewees() {

        /* String will be in format "<staffNo> <firstName> <lastName>" with the aim of
        exposing as little personal data as possible while still being able to identify
        yuconz employees using names and staffNo */
        return staffDetails.employees()
                           .stream()
                           .filter(Employee::isReviewDue)
                           .map(Employee::getStaffNo)
                           .filter(staffNo -> !completedReviews.reviewedThisYear(staffNo) && !currentReviews.hasRecord(staffNo))
                           .filter(personalDetails::hasRecord)
                           .filter(staffNo -> !allocations.hasRecord(staffNo))
                           .map(this::getStaffNoAndName)
                           .collect(toList());
    }


    /**
     * List reviewers list.
     *
     * @return the list
     */
    public List<String> listReviewers() {

        /* String will be in format "<staffNo> <firstName> <lastName>" with the aim of
        exposing as little personal data as possible while still being able to identify
        yuconz employees using names and staffNo */
        return staffDetails.reviewers()
                           .stream()
                           .map(Reviewer::getStaffNo)
                           .filter(personalDetails::hasRecord)
                           .map(this::getStaffNoAndName)
                           .collect(toList());
    }


    /**
     * Did employee sign and date review boolean.
     *
     * @param empStaffNo employee staff no
     * @return true if and only if the conditions aren't violated
     *
     * @throws StaffNoException when the staff number is negative or parsing fails
     */
    public boolean didEmployeeSignAndDateReview(final int empStaffNo) throws StaffNoException {
        try {
            return currentReviews.read(empStaffNo)
                                 .didEmployeeSignAndDate();
        } catch (final NoSuchRecordException ignored) {
            return false;
        }
    }

    /**
     * Did create review boolean.
     *
     * @param empStaffNo employee staff no
     * @return true if and only if the conditions aren't violated
     */
    public boolean didCreateReview(final int empStaffNo) {

        // check if an employee created a review
        return currentReviews.hasRecord(empStaffNo);
    }

    /**
     * Reviewed this year boolean.
     *
     * @param empStaffNo employee staff no
     * @return true if and only if the conditions aren't violated
     */
    public boolean reviewedThisYear(final int empStaffNo) {
        return completedReviews.reviewedThisYear(empStaffNo);
    }

    /**
     * Was allocated reviewers boolean.
     *
     * @param empStaffNo employee staff no
     * @return true if and only if the conditions aren't violated
     */
    public boolean wasAllocatedReviewers(final int empStaffNo) {
        return allocations.hasRecord(empStaffNo);
    }


    private String getStaffNoAndName(final int staffNo) {

        /* String will be in format "<staffNo> <firstName> <lastName>" with the aim of
        exposing as little personal data as possible while still being able to identify
        yuconz employees using names and staffNo */

        try {
            final PersonalDetailRecord record = personalDetails.read(staffNo);
            return String.format("%5d  %s %s", staffNo, record.firstName, record.lastName);
        } catch (final NoSuchRecordException | StaffNoException e) {

            // hopefully will never happen - always check with personalDetails::hasRecord
            e.printStackTrace();
            return null;
        }
    }


    private void tryMarkReadOnly(final ReviewBuilder currentReview) throws NoSuchRecordException, StaffNoException {

        // check if both viewers AND employee signed
        // is so, mark as read-only
        if (currentReview.isSignedAndDated()) try {

            // this will throw an exception if such a "completed review" exists
            completedReviews.create(currentReviews.end(currentReview.employeeStaffNo));

            // remove allocation - review no longer in progress
            allocations.remove(currentReview.employeeStaffNo);

            logSuccess(format("marked {0} as read-only", currentReview.toString()));

        } catch (final NotSignedException | RecordExistsException e) {

            // this should never occur
            e.printStackTrace();
            logFailure(format("mark {0} as read-only", currentReview.toString()));
            exit(1);
        }
    }


    private void logFailure(final String doWhat) {
        log.warning(msgFailure(doWhat));
    }

    private String msgFailure(final String doWhat) {
        return format("Failed to {0}.", doWhat);
    }

    private String msgFailure(final String doWhat, final String because) {
        return format("Failed to {0} because {1}.", doWhat, because);
    }

    private void logSuccess(final String didWhat) {
        log.info(msgSuccess(didWhat));
    }

    private String msgSuccess(final String s) {
        return format(s, "Successfully {0}.");
    }
}
