package yuconz;

import java.time.LocalDate;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import yuconz.exceptions.AlreadyLoggedInException;
import yuconz.exceptions.AlreadySignedException;
import yuconz.exceptions.AuthorisationException;
import yuconz.exceptions.DateException;
import yuconz.exceptions.NoSuchRecordException;
import yuconz.exceptions.NotLoggedInException;
import yuconz.exceptions.NullValueException;
import yuconz.exceptions.RecordExistsException;
import yuconz.exceptions.ReviewNotDueException;
import yuconz.exceptions.ReviewerAssignedException;
import yuconz.exceptions.ReviewersNotAssignedException;
import yuconz.exceptions.ReviewersNotDiffException;
import yuconz.exceptions.StaffNoException;
import yuconz.exceptions.StringFormatException;
import yuconz.records.MockDataGenerator;
import yuconz.records.Record;
import yuconz.records.credentials.CredentialsRecord;
import yuconz.records.personal.PersonalDetailRecord;
import yuconz.records.personal.ValidatingPersonalDetailsBuilder;
import yuconz.records.reviews.CompletedReview;
import yuconz.records.reviews.ReviewBuilder;
import yuconz.records.staff.Director;
import yuconz.records.staff.Employee;
import yuconz.records.staff.Manager;
import yuconz.records.staff.Section;
import yuconz.records.staff.User;

import static java.time.LocalDate.now;
import static java.util.Objects.requireNonNull;
import static java.util.logging.Logger.getAnonymousLogger;
import static org.junit.jupiter.api.Assertions.*;
import static yuconz.PermissionLevel.*;
import static yuconz.records.staff.Section.*;

/**
 * The type Hr database test.
 *
 * @param <U> the type parameter
 * @param <A> the type parameter
 */
@SuppressWarnings({"MultipleExceptionsDeclaredOnTestMethod", "JUnitTestMethodWithNoAssertions", "MethodWithTooExceptionsDeclared", "MessageMissingOnJUnitAssertion", "ClassWithTooManyTransitiveDependencies", "ClassWithTooManyDependencies", "ReuseOfLocalVariable", "ConstantConditions", "MethodCallInLoopCondition", "rawtypes", "MethodWithMultipleLoops", "OverlyCoupledMethod", "ObjectAllocationInLoop", "OverlyComplexMethod", "OverlyCoupledClass"})
final class HRDatabaseTest<U extends User, A> extends MockDataGenerator<A> {

    private static final Logger log = getAnonymousLogger();
    private static final AuthenticationServer server = AuthenticationServer.getInstance();
    private static final HRDatabase database = HRDatabase.getInstance();

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        try {
            // logout before next test
            server.logout();
            // server.credentials = LinkedList::new;
        } catch (final NotLoggedInException ignored) {}
    }

    /**
     * Tear down.
     */
    @AfterEach
    void tearDown() {
        try {
            // logout before next test
            server.logout();
        } catch (final NotLoggedInException ignored) {}
    }

    /**
     * Gets instance.
     */
    @Test
    void getInstance() {
        assertNotNull(AuthenticationServer.getInstance());
    }

    /**
     * Read own personal details.
     *
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws StringFormatException on badly formatted input
     * @throws NullValueException when a value is null
     * @throws DateException when the date is invalid
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    void readOwnPersonalDetails() throws AuthorisationException, AlreadyLoggedInException, StaffNoException, NotLoggedInException, NoSuchRecordException, StringFormatException, NullValueException, DateException {
        // any user
        final CredentialsRecord record = inject(USER, randSection());
        // login with injected mock data
        server.login(record.getUsername(), record.getPassword(), USER);
        assertNotNull(database.readPersonalDetails(record.getStaffNo()));
    }

    /**
     * Read personal details.
     *
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws StringFormatException on badly formatted input
     * @throws NullValueException when a value is null
     * @throws DateException when the date is invalid
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    void readPersonalDetails() throws AuthorisationException, AlreadyLoggedInException, StaffNoException, NotLoggedInException, NoSuchRecordException, StringFormatException, NullValueException, DateException {

        // check for HR MANAGER and EMPLOYEE and DIRECTOR
        for (final PermissionLevel position : new PermissionLevel[]{EMPLOYEE, MANAGER, DIRECTOR}) {
            // inject an HR
            final CredentialsRecord hr = inject(position, HUMAN_RESOURCES);
            final int staffNo = getValidStaffNo();
            // login with injected mock data
            server.login(hr.getUsername(), hr.getPassword(), position);
            assertNotNull(database.readPersonalDetails(staffNo));
            // logout before next test
            server.logout();
        }
    }


    /**
     * Unauthorised read personal details.
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    void unauthorisedReadPersonalDetails() {
        // check for all NON-HR sections
        for (final Section section : new Section[]{SERVICE_DELIVERY, SALES_AND_MARKETING, ADMINISTRATION})
            // check for all positions ie USER, EMPLOYEE, MANAGER and DIRECTOR
            for (final PermissionLevel position : PermissionLevel.values())
                assertThrows(AuthorisationException.class, () -> {
                    try {
                        // logout before next test
                        server.logout();
                    } catch (final NotLoggedInException ignored) {}
                    final CredentialsRecord credentials = inject(position, section);
                    // login with injected mock data
                    server.login(credentials.getUsername(), credentials.getPassword(), position);
                    int staffNo = randStaffNo();
                    while (staffNo == credentials.getStaffNo())
                        staffNo = randStaffNo();
                    database.readPersonalDetails(staffNo);
                    // logout before next test
                    server.logout();
                });
    }

    /**
     * Amend own personal details.
     *
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws StringFormatException on badly formatted input
     * @throws NullValueException when a value is null
     * @throws DateException when the date is invalid
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    void amendOwnPersonalDetails() throws StaffNoException, AuthorisationException, AlreadyLoggedInException, NotLoggedInException, NoSuchRecordException, StringFormatException, NullValueException, DateException {
        // any user
        final CredentialsRecord user = inject(USER, randSection());
        // login with injected mock data
        server.login(user.getUsername(), user.getPassword(), USER);
        database.amendPersonalDetails(user.getStaffNo(), new ValidatingPersonalDetailsBuilder(user.getStaffNo()).create());
    }

    /**
     * Amend personal details.
     *
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws StringFormatException on badly formatted input
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws NullValueException when a value is null
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws DateException when the date is invalid
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    void amendPersonalDetails() throws StaffNoException, StringFormatException, AlreadyLoggedInException, NoSuchRecordException, NullValueException, NotLoggedInException, AuthorisationException, DateException {

        for (final PermissionLevel position : new PermissionLevel[]{EMPLOYEE, MANAGER, DIRECTOR}) {

            // fetch valid staff number that refers to an existing user
            final int existingStaffNo = getValidStaffNo();
            final PersonalDetailRecord amendedRecord = new ValidatingPersonalDetailsBuilder(existingStaffNo).create();
            // inject this HR
            final CredentialsRecord hr = inject(position, HUMAN_RESOURCES);
            // login with injected mock data
            server.login(hr.getUsername(), hr.getPassword(), position);
            database.amendPersonalDetails(amendedRecord.staffNo, amendedRecord);
            // logout before next test
            server.logout();

        }
    }

    /**
     * Unauthorised amend personal details.
     *
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws StringFormatException on badly formatted input
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NullValueException when a value is null
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws DateException when the date is invalid
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    void unauthorisedAmendPersonalDetails() throws NoSuchRecordException, StringFormatException, AlreadyLoggedInException, NullValueException, StaffNoException, NotLoggedInException, DateException {

        // check for all NON-HR sections
        for (final Section section : new Section[]{SALES_AND_MARKETING, SERVICE_DELIVERY, ADMINISTRATION})

            // check for all positions ie USER, EMPLOYEE, MANAGER and DIRECTOR
            for (final PermissionLevel position : PermissionLevel.values()) {

                // fetch valid staff number that refers to an existing user
                final int existingStaffNo = getValidStaffNo();
                final PersonalDetailRecord amendedRecord = new ValidatingPersonalDetailsBuilder(existingStaffNo).create();

                final CredentialsRecord unAuthUser = inject(position, section);
                // login with injected mock data
                server.login(unAuthUser.getUsername(), unAuthUser.getPassword(), position);
                assertThrows(AuthorisationException.class, () -> database.amendPersonalDetails(existingStaffNo, amendedRecord));
                // logout before next test
                server.logout();
            }
    }

    /**
     * Create personal details.
     *
     * @throws NullValueException when a value is null
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws StringFormatException on badly formatted input
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws RecordExistsException when the record already exists
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws DateException when the date is invalid
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    void createPersonalDetails() throws NullValueException, NoSuchRecordException, AlreadyLoggedInException, StringFormatException, NotLoggedInException, RecordExistsException, StaffNoException, AuthorisationException, DateException {
        // check for HR MANAGER and EMPLOYEE
        for (final PermissionLevel position : new PermissionLevel[]{MANAGER, EMPLOYEE}) {
            // inject this HR manager / employee
            final CredentialsRecord hrEmp = inject(position, HUMAN_RESOURCES);
            // login with injected mock data
            server.login(hrEmp.getUsername(), hrEmp.getPassword(), position);
            int staffNo = randStaffNo();
            // make sure staff number does not correspond to an existing personal details record
            while (database.personalDetails.hasRecord(staffNo))
                staffNo = randStaffNo();
            database.createPersonalDetails(staffNo);
            // logout before next test
            server.logout();
        }
    }

    /**
     * Unauthorised create personal details.
     *
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws StringFormatException on badly formatted input
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws NullValueException when a value is null
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws DateException when the date is invalid
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    void unauthorisedCreatePersonalDetails() throws AlreadyLoggedInException, StringFormatException, NoSuchRecordException, NullValueException, NotLoggedInException, StaffNoException, DateException {
        // check for all NON-HR sections
        for (final Section section : new Section[]{SALES_AND_MARKETING, SERVICE_DELIVERY, ADMINISTRATION})
            // check for all positions ie USER, EMPLOYEE, MANAGER and DIRECTOR
            for (final PermissionLevel position : PermissionLevel.values()) {
                final CredentialsRecord unAuthUser = inject(position, section);
                // login with injected mock data
                server.login(unAuthUser.getUsername(), unAuthUser.getPassword(), position);
                assertThrows(AuthorisationException.class, () -> database.createPersonalDetails(randStaffNo()));
                // logout before next test
                server.logout();
            }
    }

    /**
     * Read current review.
     *
     * @throws NullValueException when a value is null
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws StringFormatException on badly formatted input
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws ReviewersNotDiffException the reviewers not diff exception
     * @throws RecordExistsException when the record already exists
     * @throws ReviewersNotAssignedException when reviewers have not been assigned to review this employee
     * @throws ReviewNotDueException when a review is not due for the employee
     * @throws DateException when the date is invalid
     * @throws ReviewerAssignedException when reviewers are already assigned
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void readCurrentReview() throws NullValueException, NoSuchRecordException, AlreadyLoggedInException, StringFormatException, NotLoggedInException, StaffNoException, AuthorisationException, ReviewersNotDiffException, RecordExistsException, ReviewersNotAssignedException, ReviewNotDueException, DateException, ReviewerAssignedException {
        // inject an employee
        final CredentialsRecord emp = inject(EMPLOYEE, randSection());
        // inject reviewers
        final CredentialsRecord rev1 = inject(REVIEWER, randSection());
        final CredentialsRecord rev2 = inject(REVIEWER, randSection());
        // allocate the injected employee to injected reviewers
        injectAllocation(emp, rev1, rev2);
        // check employee
        // login with injected mock data
        server.login(emp.getUsername(), emp.getPassword(), EMPLOYEE);
        database.createReview(emp.getStaffNo());
        database.readCurrentReview(emp.getStaffNo());
        // logout before next test
        server.logout();
        // check reviewer 1
        // login with injected mock data
        server.login(rev1.getUsername(), rev1.getPassword(), REVIEWER);
        database.readCurrentReview(emp.getStaffNo());
        // logout before next test
        server.logout();
        // check reviewer 2
        // login with injected mock data
        server.login(rev2.getUsername(), rev2.getPassword(), REVIEWER);
        database.readCurrentReview(emp.getStaffNo());
    }

    /**
     * Amend current review.
     *
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws NullValueException when a value is null
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws ReviewersNotDiffException the reviewers not diff exception
     * @throws StringFormatException on badly formatted input
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws AlreadySignedException when the review has already been signed
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws RecordExistsException when the record already exists
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws DateException when the date is invalid
     * @throws ReviewNotDueException when a review is not due for the employee
     * @throws ReviewerAssignedException when reviewers are already assigned
     * @throws ReviewersNotAssignedException when reviewers have not been assigned to review this employee
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void amendCurrentReview() throws NoSuchRecordException, NullValueException, StaffNoException, ReviewersNotDiffException, StringFormatException, NotLoggedInException, AlreadySignedException, AuthorisationException, RecordExistsException, AlreadyLoggedInException, DateException, ReviewNotDueException, ReviewerAssignedException, ReviewersNotAssignedException {
        // inject an employee
        final CredentialsRecord emp = inject(EMPLOYEE, randSection());
        // inject reviewers
        final CredentialsRecord rev1 = inject(REVIEWER, randSection());
        final CredentialsRecord rev2 = inject(REVIEWER, randSection());
        // allocate the injected employee to injected reviewers
        injectAllocation(emp, rev1, rev2);
        // check employee
        // login with injected mock data
        server.login(emp.getUsername(), emp.getPassword(), EMPLOYEE);
        database.createReview(emp.getStaffNo());
        final ReviewBuilder amendedRev = new ReviewBuilder(emp.getStaffNo(), getName(emp.getStaffNo()), randJob(), rev1.getStaffNo(), getName(rev1.getStaffNo()), rev2.getStaffNo(), getName(rev2.getStaffNo()));
        database.amendCurrentReview(emp.getStaffNo(), amendedRev);
        // logout before next test
        server.logout();
        // check reviewer 1
        // login with injected mock data
        server.login(rev1.getUsername(), rev1.getPassword(), REVIEWER);
        database.amendCurrentReview(emp.getStaffNo(), amendedRev);
        // logout before next test
        server.logout();
        // check reviewer 2
        // login with injected mock data
        server.login(rev2.getUsername(), rev2.getPassword(), REVIEWER);
        database.amendCurrentReview(emp.getStaffNo(), amendedRev);
    }

    private String getName(final int staffNo) throws NoSuchRecordException {
        final PersonalDetailRecord personalDetailRecord = database.personalDetails.get()
                                                                                  .stream()
                                                                                  .filter(x -> x.staffNo == staffNo)
                                                                                  .findFirst()
                                                                                  .orElseThrow(NoSuchRecordException::new);
        return personalDetailRecord.firstName + ' ' + personalDetailRecord.lastName;
    }

    /**
     * Sign current review employee.
     *
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws ReviewersNotDiffException the reviewers not diff exception
     * @throws AlreadySignedException when the review has already been signed
     * @throws StringFormatException on badly formatted input
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NullValueException when a value is null
     * @throws RecordExistsException when the record already exists
     * @throws ReviewersNotAssignedException when reviewers have not been assigned to review this employee
     * @throws DateException when the date is invalid
     * @throws ReviewNotDueException when a review is not due for the employee
     * @throws ReviewerAssignedException when reviewers are already assigned
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void signCurrentReviewEmployee() throws AuthorisationException, NotLoggedInException, NoSuchRecordException, StaffNoException, ReviewersNotDiffException, AlreadySignedException, StringFormatException, AlreadyLoggedInException, NullValueException, RecordExistsException, ReviewersNotAssignedException, DateException, ReviewNotDueException, ReviewerAssignedException {
        // inject an employee
        final CredentialsRecord emp = inject(EMPLOYEE, randSection());
        // allocate the injected employee to injected reviewers
        injectAllocation(emp, inject(REVIEWER, randSection()), inject(REVIEWER, randSection()));
        // login with injected mock data
        server.login(emp.getUsername(), emp.getPassword(), EMPLOYEE);
        database.createReview(emp.getStaffNo());
        database.signCurrentReviewEmployee(emp.getStaffNo());
    }

    /**
     * Sign non existent current review employee.
     *
     * @throws NullValueException when a value is null
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws StringFormatException on badly formatted input
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws DateException when the date is invalid
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws RecordExistsException when the record already exists
     * @throws ReviewNotDueException when a review is not due for the employee
     * @throws ReviewersNotDiffException the reviewers not diff exception
     * @throws ReviewerAssignedException when reviewers are already assigned
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void signNonExistentCurrentReviewEmployee() throws NullValueException, NoSuchRecordException, AlreadyLoggedInException, StringFormatException, StaffNoException, DateException, AuthorisationException, NotLoggedInException, RecordExistsException, ReviewNotDueException, ReviewersNotDiffException, ReviewerAssignedException {
        // inject an employee
        final CredentialsRecord emp = inject(EMPLOYEE, randSection());
        // allocate the injected employee to injected reviewers
        injectAllocation(emp, inject(REVIEWER, randSection()), inject(REVIEWER, randSection()));
        // login with injected mock data
        server.login(emp.getUsername(), emp.getPassword(), EMPLOYEE);
        assertThrows(NoSuchRecordException.class, () -> database.signCurrentReviewEmployee(emp.getStaffNo()));
    }

    /**
     * Sign current review reviewer.
     *
     * @throws NullValueException when a value is null
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws StringFormatException on badly formatted input
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws AlreadySignedException when the review has already been signed
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws DateException when the date is invalid
     * @throws RecordExistsException when the record already exists
     * @throws ReviewersNotDiffException the reviewers not diff exception
     * @throws ReviewNotDueException when a review is not due for the employee
     * @throws ReviewerAssignedException when reviewers are already assigned
     * @throws ReviewersNotAssignedException when reviewers have not been assigned to review this employee
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void signCurrentReviewReviewer() throws NullValueException, NoSuchRecordException, AlreadyLoggedInException, StringFormatException, NotLoggedInException, StaffNoException, AlreadySignedException, AuthorisationException, DateException, RecordExistsException, ReviewersNotDiffException, ReviewNotDueException, ReviewerAssignedException, ReviewersNotAssignedException {
        // inject an employee
        final CredentialsRecord emp = inject(EMPLOYEE, randSection());
        // inject reviewers
        final CredentialsRecord rev1 = inject(REVIEWER, randSection());
        // allocate the injected employee to injected reviewers
        injectAllocation(emp, rev1, inject(REVIEWER, randSection()));
        // login with injected mock data
        server.login(emp.getUsername(), emp.getPassword(), EMPLOYEE);
        database.createReview(emp.getStaffNo());
        // logout before next test
        server.logout();
        // login with injected mock data
        server.login(rev1.getUsername(), rev1.getPassword(), REVIEWER);
        database.signCurrentReviewReviewer(emp.getStaffNo(), rev1.getStaffNo());
    }

    /**
     * Sign non existent current review reviewer.
     *
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws StringFormatException on badly formatted input
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NullValueException when a value is null
     * @throws DateException when the date is invalid
     * @throws RecordExistsException when the record already exists
     * @throws ReviewersNotDiffException the reviewers not diff exception
     * @throws ReviewNotDueException when a review is not due for the employee
     * @throws ReviewerAssignedException when reviewers are already assigned
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void signNonExistentCurrentReviewReviewer() throws NotLoggedInException, StaffNoException, NoSuchRecordException, AuthorisationException, StringFormatException, AlreadyLoggedInException, NullValueException, DateException, RecordExistsException, ReviewersNotDiffException, ReviewNotDueException, ReviewerAssignedException {
        // inject an employee
        final CredentialsRecord emp = inject(EMPLOYEE, randSection());
        // inject reviewers
        final CredentialsRecord rev1 = inject(REVIEWER, randSection());
        // allocate the injected employee to injected reviewers
        injectAllocation(emp, rev1, inject(REVIEWER, randSection()));
        // login with injected mock data
        server.login(rev1.getUsername(), rev1.getPassword(), REVIEWER);
        assertThrows(NoSuchRecordException.class, () -> database.signCurrentReviewReviewer(emp.getStaffNo(), rev1.getStaffNo()));
    }

    /**
     * Unauthorised sign review.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void unauthorisedSignReview() {
        // FIXME not the right error thrown
        // check for all positions ie USER, EMPLOYEE, MANAGER and DIRECTOR
        for (final Section section : Section.values()) {
            for (final PermissionLevel position : new PermissionLevel[]{USER, EMPLOYEE})
                assertThrows(AuthorisationException.class, () -> {
                    try {
                        // logout before next test
                        server.logout();
                    } catch (final NotLoggedInException ignored) {}
                    final CredentialsRecord unAuthEmp = inject(position, section);
                    // login with injected mock data
                    server.login(unAuthEmp.getUsername(), unAuthEmp.getPassword(), position);
                    database.signCurrentReviewReviewer(unAuthEmp.getStaffNo(), randStaffNo());
                    // logout before next test
                    server.logout();
                });

            for (final PermissionLevel position : new PermissionLevel[]{MANAGER, DIRECTOR})
                assertThrows(AuthorisationException.class, () -> {
                    try {
                        // logout before next test
                        server.logout();
                    } catch (final NotLoggedInException ignored) {}
                    final CredentialsRecord unAuthRev = inject(position, section);
                    // login with injected mock data
                    server.login(unAuthRev.getUsername(), unAuthRev.getPassword(), position);
                    database.signCurrentReviewEmployee(unAuthRev.getStaffNo());
                    // logout before next test
                    server.logout();
                });
        }
    }

    /**
     * Create review.
     *
     * @throws NullValueException when a value is null
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws StringFormatException on badly formatted input
     * @throws RecordExistsException when the record already exists
     * @throws ReviewersNotAssignedException when reviewers have not been assigned to review this employee
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws ReviewersNotDiffException the reviewers not diff exception
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws DateException when the date is invalid
     * @throws ReviewNotDueException when a review is not due for the employee
     * @throws ReviewerAssignedException when reviewers are already assigned
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void createReview() throws NullValueException, AuthorisationException, NotLoggedInException, StringFormatException, RecordExistsException, ReviewersNotAssignedException, StaffNoException, ReviewersNotDiffException, AlreadyLoggedInException, NoSuchRecordException, DateException, ReviewNotDueException, ReviewerAssignedException {
        // inject an employee
        final CredentialsRecord emp = inject(EMPLOYEE, randSection());
        // allocate the injected employee to injected reviewers
        injectAllocation(emp, inject(REVIEWER, randSection()), inject(REVIEWER, randSection()));
        // login with injected mock data
        server.login(emp.getUsername(), emp.getPassword(), EMPLOYEE);
        database.createReview(emp.getStaffNo());
    }

    /**
     * Create existing review.
     *
     * @throws StringFormatException on badly formatted input
     * @throws DateException when the date is invalid
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws NullValueException when a value is null
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws RecordExistsException when the record already exists
     * @throws ReviewersNotDiffException the reviewers not diff exception
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void createExistingReview() throws StringFormatException, DateException, StaffNoException, NullValueException, AuthorisationException, NotLoggedInException, RecordExistsException, ReviewersNotDiffException, NoSuchRecordException, AlreadyLoggedInException {
        try {
            // check for all positions ie USER, EMPLOYEE, MANAGER and DIRECTOR
            for (final Section section : Section.values()) {
                // inject an employee
                final CredentialsRecord emp = inject(EMPLOYEE, section);
                // login with injected mock data
                server.login(emp.getUsername(), emp.getPassword(), EMPLOYEE);
                database.createReview(emp.getStaffNo());
                assertThrows(RecordExistsException.class, () -> database.createReview(emp.getStaffNo()));
                // logout before next test
                server.logout();
            }
        } catch (final ReviewersNotAssignedException ignored) {}
    }

    /**
     * Read completed review employee.
     *
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws NullValueException when a value is null
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws StringFormatException on badly formatted input
     * @throws DateException when the date is invalid
     * @throws RecordExistsException when the record already exists
     * @throws ReviewersNotDiffException the reviewers not diff exception
     * @throws ReviewNotDueException when a review is not due for the employee
     * @throws ReviewerAssignedException when reviewers are already assigned
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void readCompletedReviewEmployee() throws NotLoggedInException, AuthorisationException, StaffNoException, NullValueException, NoSuchRecordException, AlreadyLoggedInException, StringFormatException, DateException, RecordExistsException, ReviewersNotDiffException, ReviewNotDueException, ReviewerAssignedException {
        // inject an employee
        final CredentialsRecord emp = inject(EMPLOYEE, randSection());
        // allocate the injected employee to injected reviewers
        injectAllocation(emp, inject(REVIEWER, randSection()), inject(REVIEWER, randSection()));
        // login with injected mock data
        server.login(emp.getUsername(), emp.getPassword(), EMPLOYEE);
        try {
            // check last year
            database.readCompletedReview(emp.getStaffNo(), now().getYear() - 1);
        } catch (final NoSuchRecordException ignored) {}
    }

    /**
     * Read completed review hr employee.
     *
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws NullValueException when a value is null
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws StringFormatException on badly formatted input
     * @throws DateException when the date is invalid
     * @throws RecordExistsException when the record already exists
     * @throws ReviewersNotDiffException the reviewers not diff exception
     * @throws ReviewNotDueException when a review is not due for the employee
     * @throws ReviewerAssignedException when reviewers are already assigned
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void readCompletedReviewHREmployee() throws NotLoggedInException, AuthorisationException, StaffNoException, NullValueException, NoSuchRecordException, AlreadyLoggedInException, StringFormatException, DateException, RecordExistsException, ReviewersNotDiffException, ReviewNotDueException, ReviewerAssignedException {
        // inject an employee
        final CredentialsRecord emp = inject(EMPLOYEE, randSection());
        // allocate the injected employee to injected reviewers
        injectAllocation(emp, inject(REVIEWER, randSection()), inject(REVIEWER, randSection()));
        // inject this HR employee or Manager
        final CredentialsRecord hrEmp = inject(EMPLOYEE, HUMAN_RESOURCES);
        // login with injected mock data
        server.login(hrEmp.getUsername(), hrEmp.getPassword(), EMPLOYEE);
        try {
            // check last year
            database.readCompletedReview(emp.getStaffNo(), now().getYear() - 1);
        } catch (final NoSuchRecordException ignored) {}
    }

    /**
     * Read completed review reviewer.
     *
     * @throws StringFormatException on badly formatted input
     * @throws DateException when the date is invalid
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws ReviewNotDueException when a review is not due for the employee
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws RecordExistsException when the record already exists
     * @throws ReviewerAssignedException when reviewers are already assigned
     * @throws NullValueException when a value is null
     * @throws ReviewersNotDiffException the reviewers not diff exception
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void readCompletedReviewReviewer() throws StringFormatException, DateException, StaffNoException, NoSuchRecordException, ReviewNotDueException, AuthorisationException, AlreadyLoggedInException, NotLoggedInException, RecordExistsException, ReviewerAssignedException, NullValueException, ReviewersNotDiffException {
        // inject reviewers
        final CredentialsRecord rev2 = inject(REVIEWER, randSection());
        // inject an employee
        final CredentialsRecord emp = inject(EMPLOYEE, randSection());
        // allocate the injected employee to injected reviewers
        injectAllocation(emp, inject(REVIEWER, randSection()), rev2);
        // login with injected mock data
        server.login(rev2.getUsername(), rev2.getPassword(), REVIEWER);
        try {
            // check last year
            database.readCompletedReview(emp.getStaffNo(), now().getYear() - 1);
        } catch (final NoSuchRecordException ignored) {}
    }

    /**
     * Inject allocation.
     *
     * @param emp the emp
     * @param rev1 the rev 1
     * @param rev2 the rev 2
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws ReviewersNotDiffException the reviewers not diff exception
     * @throws RecordExistsException when the record already exists
     * @throws ReviewNotDueException when a review is not due for the employee
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws ReviewerAssignedException when reviewers are already assigned
     * @throws StringFormatException on badly formatted input
     * @throws DateException when the date is invalid
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NullValueException when a value is null
     */
    private void injectAllocation(final Record emp, final Record rev1, final Record rev2) throws AuthorisationException, NotLoggedInException, ReviewersNotDiffException, RecordExistsException, ReviewNotDueException, NoSuchRecordException, StaffNoException, ReviewerAssignedException, StringFormatException, DateException, AlreadyLoggedInException, NullValueException {
        // inject an HR employee
        final CredentialsRecord hrEmp = inject(EMPLOYEE, HUMAN_RESOURCES);
        // login with injected mock data
        server.login(hrEmp.getUsername(), hrEmp.getPassword(), EMPLOYEE);
        // make the allocation
        database.allocateReviewers(emp.getStaffNo(), rev1.getStaffNo(), rev2.getStaffNo());
        // logout before next test
        server.logout();
    }


    /**
     * Read non existent completed review.
     *
     * @throws NullValueException when a value is null
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws StringFormatException on badly formatted input
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws DateException when the date is invalid
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void readNonExistentCompletedReview() throws NullValueException, NoSuchRecordException, AlreadyLoggedInException, StringFormatException, StaffNoException, DateException, NotLoggedInException {
        // check for all managers and directors from all sections
        for (final PermissionLevel position : new PermissionLevel[]{MANAGER, DIRECTOR}) {
            final CredentialsRecord rev = inject(position, randSection());
            // login with injected mock data
            server.login(rev.getUsername(), rev.getPassword(), position);
            assertThrows(NoSuchRecordException.class, () -> {
                int staffNo = randStaffNo();
                // make sure staff number does not correspond to an existing personal details record
                while (database.staffDetails.hasRecord(staffNo))
                    staffNo = randStaffNo();
                database.readCompletedReview(staffNo, now().minusYears(RAND.nextInt(10))
                                                           .getYear());
            });
            // logout before next test
            server.logout();
        }
    }

    /**
     * Unauthorised read completed review.
     *
     * @throws StringFormatException on badly formatted input
     * @throws DateException when the date is invalid
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws NullValueException when a value is null
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void unauthorisedReadCompletedReview() throws StringFormatException, DateException, StaffNoException, NullValueException, AlreadyLoggedInException, NoSuchRecordException, NotLoggedInException {
        // neither employees nor users should be able to do it
        for (final PermissionLevel position : new PermissionLevel[]{EMPLOYEE, USER}) {
            // inject a user / employee
            final CredentialsRecord user = inject(position, randSection());
            // login with injected mock data
            server.login(user.getUsername(), user.getPassword(), position);
            assertThrows(AuthorisationException.class, () -> {
                try {
                    // fetch valid staff number that refers to an existing user
                    final int staffNo = getValidStaffNo();
                    // check last year
                    database.readCompletedReview(staffNo, now().getYear() - 1);
                } catch (final NoSuchRecordException ignored) {}
            });
            // logout before next test
            server.logout();
        }
    }

    private int getValidStaffNo() throws NoSuchRecordException {
        return database.personalDetails.get()
                                       .stream()
                                       .findFirst()
                                       .orElseThrow(NoSuchRecordException::new).staffNo;
    }

    /**
     * Allocate reviewers.
     *
     * @throws NullValueException when a value is null
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws StringFormatException on badly formatted input
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws DateException when the date is invalid
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws ReviewersNotDiffException the reviewers not diff exception
     * @throws RecordExistsException when the record already exists
     * @throws ReviewNotDueException when a review is not due for the employee
     * @throws ReviewerAssignedException when reviewers are already assigned
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void allocateReviewers() throws NullValueException, NoSuchRecordException, AlreadyLoggedInException, StringFormatException, StaffNoException, DateException, AuthorisationException, NotLoggedInException, ReviewersNotDiffException, RecordExistsException, ReviewNotDueException, ReviewerAssignedException {

        // check that it works for managers and directors from HR
        for (final PermissionLevel position : new PermissionLevel[]{MANAGER, EMPLOYEE}) {

            try {
                // logout before next test
                server.logout();
            } catch (final NotLoggedInException ignored) {}

            // inject reviewers
            final CredentialsRecord rev1 = inject(MANAGER, randSection());
            final CredentialsRecord rev2 = inject(DIRECTOR, randSection());

            // inject this HR employee or Manager
            final CredentialsRecord hrEmp = inject(position, HUMAN_RESOURCES);
            // login with injected mock data
            server.login(hrEmp.getUsername(), hrEmp.getPassword(), position);
            database.allocateReviewers(inject(EMPLOYEE, randSection()).getStaffNo(), rev1.getStaffNo(), rev2.getStaffNo());
            // logout before next test
            server.logout();
        }
    }

    /**
     * Allocate non existent reviewers.
     *
     * @throws NullValueException when a value is null
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws StringFormatException on badly formatted input
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws ReviewersNotDiffException the reviewers not diff exception
     * @throws RecordExistsException when the record already exists
     * @throws ReviewNotDueException when a review is not due for the employee
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws ReviewerAssignedException when reviewers are already assigned
     * @throws DateException when the date is invalid
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void allocateNonExistentReviewers() throws NullValueException, NoSuchRecordException, AlreadyLoggedInException, StringFormatException, AuthorisationException, NotLoggedInException, ReviewersNotDiffException, RecordExistsException, ReviewNotDueException, StaffNoException, ReviewerAssignedException, DateException {
        // check for HR MANAGER and EMPLOYEE
        for (final PermissionLevel position : new PermissionLevel[]{MANAGER, EMPLOYEE}) {
            // inject this HR employee or Manager
            final CredentialsRecord hrEmp = inject(position, HUMAN_RESOURCES);
            // login with injected mock data
            server.login(hrEmp.getUsername(), hrEmp.getPassword(), position);
            int staffNo1 = randStaffNo(), staffNo2 = randStaffNo();
            // make sure staff number does not correspond to an existing personal details record
            while (database.staffDetails.hasRecord(staffNo1))
                staffNo1 = randStaffNo();
            // make sure staff number does not correspond to an existing personal details record
            while (database.staffDetails.hasRecord(staffNo2))
                staffNo2 = randStaffNo();
            database.allocateReviewers(inject(EMPLOYEE, randSection()).getStaffNo(), staffNo1, staffNo2);
            // logout before next test
            server.logout();
        }
    }

    /**
     * Allocate reviewers to non existent employee.
     *
     * @throws StringFormatException on badly formatted input
     * @throws DateException when the date is invalid
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws NullValueException when a value is null
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void allocateReviewersToNonExistentEmployee() throws StringFormatException, DateException, StaffNoException, NullValueException, AlreadyLoggedInException, NoSuchRecordException, NotLoggedInException {
        // check for HR MANAGER and EMPLOYEE
        for (final PermissionLevel position : new PermissionLevel[]{MANAGER, EMPLOYEE}) {
            // check for both cases: when reviewer is a MANAGER or a DIRECTOR
            for (final PermissionLevel revPosition : new PermissionLevel[]{MANAGER, DIRECTOR}) {
                // inject reviewers
                final CredentialsRecord rev1 = inject(revPosition, randSection());
                final CredentialsRecord rev2 = inject(revPosition, randSection());
                final CredentialsRecord hrEmp = inject(position, HUMAN_RESOURCES);
                // login with injected mock data
                server.login(hrEmp.getUsername(), hrEmp.getPassword(), position);
                assertThrows(NoSuchRecordException.class, () -> {
                    int staffNo = randStaffNo();
                    // make sure staff number does not correspond to an existing personal details record
                    while (database.staffDetails.hasRecord(staffNo))
                        staffNo = randStaffNo();
                    database.allocateReviewers(staffNo, rev1.getStaffNo(), rev2.getStaffNo());
                });
                // logout before next test
                server.logout();
            }
        }
    }

    /**
     * Unauthorised allocate reviewers.
     *
     * @throws StringFormatException on badly formatted input
     * @throws DateException when the date is invalid
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws NullValueException when a value is null
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void unauthorisedAllocateReviewers() throws StringFormatException, DateException, StaffNoException, NullValueException, AlreadyLoggedInException, NoSuchRecordException, NotLoggedInException {
        // check for all positions ie USER, EMPLOYEE, MANAGER and DIRECTOR
        for (final PermissionLevel position : PermissionLevel.values()) {
            // check for all NON-HR sections
            for (final Section section : new Section[]{ADMINISTRATION, SALES_AND_MARKETING, SERVICE_DELIVERY}) {
                // check for both cases: when reviewer is a MANAGER or a DIRECTOR
                for (final PermissionLevel revPosition : new PermissionLevel[]{MANAGER, DIRECTOR}) {
                    // inject reviewers
                    final CredentialsRecord rev1 = inject(revPosition, randSection());
                    final CredentialsRecord rev2 = inject(revPosition, randSection());
                    final CredentialsRecord unAuthUser = inject(position, section);
                    // login with injected mock data
                    server.login(unAuthUser.getUsername(), unAuthUser.getPassword(), position);
                    assertThrows(AuthorisationException.class, () -> {
                        int staffNo = randStaffNo();
                        // make sure staff number does not correspond to an existing personal details record
                        while (database.staffDetails.hasRecord(staffNo))
                            staffNo = randStaffNo();
                        database.allocateReviewers(staffNo, rev1.getStaffNo(), rev2.getStaffNo());
                    });
                    // logout before next test
                    server.logout();
                }
            }
        }
    }

    /**
     * List reviewers.
     *
     * @throws NullValueException when a value is null
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws StringFormatException on badly formatted input
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws DateException when the date is invalid
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void listReviewers() throws NullValueException, NoSuchRecordException, AlreadyLoggedInException, StringFormatException, StaffNoException, DateException, NotLoggedInException {
        for (final PermissionLevel position : new PermissionLevel[]{EMPLOYEE, MANAGER, DIRECTOR}) {
            // HR employee
            final CredentialsRecord hr = inject(position, HUMAN_RESOURCES);
            // login with injected mock data
            server.login(hr.getUsername(), hr.getPassword(), position);
            assertNotNull(database.listReviewers());
            // logout before next test
            server.logout();
        }
    }

    /**
     * List unallocated reviewees.
     *
     * @throws NullValueException when a value is null
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws StringFormatException on badly formatted input
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws DateException when the date is invalid
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void listUnallocatedReviewees() throws NullValueException, NoSuchRecordException, AlreadyLoggedInException, StringFormatException, NotLoggedInException, StaffNoException, DateException {
        // check for HR MANAGER and EMPLOYEE
        for (final PermissionLevel position : new PermissionLevel[]{EMPLOYEE, MANAGER}) {
            // inject this HR employee or Manager
            final CredentialsRecord hrEmp = inject(position, HUMAN_RESOURCES);
            // login with injected mock data
            server.login(hrEmp.getUsername(), hrEmp.getPassword(), position);
            assertNotNull(database.listUnallocatedReviewees());
            // logout before next test
            server.logout();
        }
    }

    /**
     * Test inject.
     *
     * @throws StringFormatException on badly formatted input
     * @throws DateException when the date is invalid
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    @RepeatedTest(10)
    void testInject() throws StringFormatException, DateException, StaffNoException {
        // check for all positions ie USER, EMPLOYEE, MANAGER and DIRECTOR
        for (final PermissionLevel position : PermissionLevel.values()) {
            final CredentialsRecord injectedRec = inject(position, randSection());
            assertNotNull(injectedRec);
            assertTrue(database.personalDetails.hasRecord(injectedRec.getStaffNo()));
            assertTrue(server.credentials.hasRecord(injectedRec.getStaffNo()));
            assertTrue(database.staffDetails.hasRecord(injectedRec.getStaffNo()));
        }
    }

    private CredentialsRecord inject(final PermissionLevel position, final Section section) throws StaffNoException, DateException, StringFormatException {
        int staffNo = randStaffNo();
        while (server.credentials.hasRecord(staffNo) || database.staffDetails.hasRecord(staffNo))
            staffNo = randStaffNo();
        final String username = randUsername();
        assertNotNull(username);
        final String password = randPassword();
        assertNotNull(password);
        final LocalDate employmentStart = randEmploymentDate();
        assertNotNull(employmentStart);
        final int currentYear = now().getYear();
        final int yearsEmployed = currentYear - employmentStart.getYear();
        final PersonalDetailRecord personalDetailRecord = requireNonNull(randPersonalDetailsBuilder(staffNo)).create();
        assertNotNull(personalDetailRecord);
        database.personalDetails.get()
                                .add(personalDetailRecord);
        U user = null;
        switch (position) {
            case EMPLOYEE:
                user = (U) new Employee(staffNo, section, employmentStart);
                break;
            case MANAGER:
                user = (U) new Manager(staffNo, section, employmentStart);
                break;
            case DIRECTOR:
                user = (U) new Director(staffNo, section, employmentStart);
                break;
            case REVIEWER:
                user = (U) new Manager(staffNo, section, employmentStart);
                break;
            case USER:
                user = (U) new Employee(staffNo, section, employmentStart);
                break;
        }

        assertNotNull(user);

        database.staffDetails.get()
                             .add(user);

        // make past completed annual review records
        // but not for directors
        if (user instanceof Employee) {
            for (int yearOfEmp = 1; yearOfEmp < yearsEmployed; yearOfEmp++) {
                @SuppressWarnings("ObjectAllocationInLoop")
                final CompletedReview completedReview = new CompletedReview(staffNo, personalDetailRecord.firstName + ' ' + personalDetailRecord.lastName, randJob(), randReviewerStaffNo(), randFullName(), randReviewerStaffNo(), randFullName(), randomParagraph(), randomParagraph(), randomParagraph(), randomParagraph(), randRecommendation());
                try {
                    completedReview.setYearDone((currentYear - yearsEmployed) + yearOfEmp);
                } catch (final DateException e) {
                    e.printStackTrace();
                    log.severe(e.getMessage());
                    return null;
                }
                database.completedReviews.get()
                                         .add(completedReview);
            }
        }
        final CredentialsRecord credentialsRecord = new CredentialsRecord(staffNo, username, password);
        assertNotNull(credentialsRecord);
        server.credentials.get()
                          .add(credentialsRecord);
        return credentialsRecord;
    }
}
