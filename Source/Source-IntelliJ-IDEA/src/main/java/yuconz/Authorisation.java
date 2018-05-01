package yuconz;

import java.util.logging.Logger;
import yuconz.exceptions.AuthorisationException;
import yuconz.exceptions.NotLoggedInException;
import yuconz.exceptions.ReviewersNotDiffException;
import yuconz.exceptions.StaffNoException;
import yuconz.records.staff.Employee;
import yuconz.records.staff.Reviewer;
import yuconz.records.staff.User;

import static java.text.MessageFormat.format;
import static yuconz.PermissionLevel.REVIEWER;
import static yuconz.PermissionLevel.USER;
import static yuconz.records.staff.Section.HUMAN_RESOURCES;

/**
 * Authorisation.
 *
 * @param <U> the type parameter
 */
@SuppressWarnings({"Singleton", "ClassHasNoToStringMethod"})
final class Authorisation<U extends User> {

    @SuppressWarnings("StaticVariableOfConcreteClass")
    private static Authorisation<? extends User> self = new Authorisation<>();
    private final Logger log = Logger.getAnonymousLogger();
    private final AuthenticationServer<U> authServer = AuthenticationServer.getInstance();

    private Authorisation() {}

    /**
     * Gets instance.
     *
     * @param <U> the type parameter
     * @return the instance
     */
    @SuppressWarnings("NonThreadSafeLazyInitialization")
    public static <U extends User> Authorisation<U> getInstance() {
        if (self == null) self = new Authorisation<>();
        return (Authorisation<U>) self;
    }


    /**
     * Authorise read personal details.
     *
     * @param staffNo the staff number
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    @SuppressWarnings({"FeatureEnvy", "AlibabaAvoidComplexCondition"})
    public void authReadPersonalDetails(final int staffNo) throws AuthorisationException, NotLoggedInException, StaffNoException {
        // @formatter:off

        // validation
        if (staffNo < 0) throw new StaffNoException();

        // retrieve the person logged in
        final U loggedIn = authServer.getLoggedIn();

        // requesting own personal details or from HR and not logged in as a user or a reviewer
        if ((loggedIn.getStaffNo() == staffNo) || ((loggedIn.getSection() == HUMAN_RESOURCES) && (authServer.getPermissionLevel() != USER) && (authServer.getPermissionLevel() != REVIEWER)))
            logGrant(format("read {0}''s personal details record", staffNo));

        else {
            final String msg = msgDeny(
                    format("read {0}'s personal details", staffNo),
                    format("{0} is not from HR and it''s not their record", loggedIn));
            log.warning(msg);
            throw new AuthorisationException(msg);
        }
        // @formatter:on
    }

    /**
     * Authorise amend personal details.
     *
     * @param staffNo the staff number
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    public void authoriseAmendPersonalDetails(final int staffNo) throws AuthorisationException, NotLoggedInException, StaffNoException {
        // permissions for read same as for amend
        authReadPersonalDetails(staffNo);
    }

    /**
     * Authorise create personal details.
     *
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     */
    @SuppressWarnings("NewMethodNamingConvention")
    public void authoriseCreatePersonalDetails() throws AuthorisationException, NotLoggedInException {
        // @formatter:off

        // HR employee not logged in as user or reviewer
        // so manager as well but not director as prof Barnes insisted directors aren't employees
        if ((authServer.getLoggedIn() instanceof Employee) && (authServer.getLoggedIn()
                                                                        .getSection() == HUMAN_RESOURCES) && (authServer.getPermissionLevel() != USER) && (authServer.getPermissionLevel() != REVIEWER))
            logGrant("create a personal details record");

        else {
            final String msg = msgDeny("create personal details record", "only HR employees can create personal details records.");
            log.warning(msg);
            throw new AuthorisationException(msg);
        }
        // @formatter:on
    }

    /**
     * Authorise read review.
     *
     * @param empStaffNo employee staff number
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    public void authoriseReadCurrentReview(final int empStaffNo) throws NotLoggedInException, AuthorisationException, StaffNoException {
        authoriseReadCompletedReviews(empStaffNo);
    }

    /**
     * Authorise read completed review.
     *
     * @param empStaffNo employee staff number
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    public void authoriseReadCompletedReview(final int empStaffNo) throws NotLoggedInException, AuthorisationException, StaffNoException {
        authoriseReadCompletedReviews(empStaffNo);
    }

    /**
     * Authorise read past reviews.
     *
     * @param empStaffNo employee staff number
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    @SuppressWarnings("unchecked")
    public void authoriseReadCompletedReviews(final int empStaffNo) throws NotLoggedInException, AuthorisationException, StaffNoException {
        // @formatter:off

        // validation
        if (empStaffNo < 0) throw new StaffNoException();

        // retrieve the person logged in
        final U loggedIn = authServer.getLoggedIn();
        final PermissionLevel permissionLevel = authServer.getPermissionLevel();

        // reading reviewee's past reviews
        if (loggedIn instanceof Reviewer)
            logGrant("read past completed reviews");

        // reading your own past reviews
        else if (((loggedIn instanceof Employee) && (permissionLevel != USER) && (loggedIn.getStaffNo() == empStaffNo))||((loggedIn.getSection() == HUMAN_RESOURCES) && (permissionLevel != USER) && (loggedIn instanceof Employee)))
            logGrant("read review");

        // HR employee not logged in as user or reviewer
        // so manager as well but not director as prof Barnes insisted directors aren't employees
        else {
            final String msg = msgDeny("read review", format("{0} is not a reviewer and not an employee requesting their own data", loggedIn));
            log.warning(msg);
            throw new AuthorisationException(msg);
        }
        // @formatter:on
    }

    /**
     * Authorise amend review.
     *
     * @param empStaffNo employee staff number
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    @SuppressWarnings({"WeakerAccess", "ChainOfInstanceofChecks"})
    public void authoriseAmendCurrentReview(final int empStaffNo) throws NotLoggedInException, AuthorisationException, StaffNoException {

        // validation
        if (empStaffNo < 0) throw new StaffNoException();

        // retrieve the person logged in
        final U loggedIn = authServer.getLoggedIn();

        final PermissionLevel permissionLevel = authServer.getPermissionLevel();

        // either reviewer or employee amending their own record but not logged in as user
        if ((loggedIn instanceof Reviewer) || ((loggedIn instanceof Employee) && (loggedIn.getStaffNo() == empStaffNo) && (permissionLevel != USER)))
            logGrant(format("amend {0}''s review", empStaffNo));

        else {
            final String msg = msgDeny(format("amend {0}''s review", empStaffNo), format("{0} is not a reviewer reviewing {1} and you not employee with staff number {1}", loggedIn, empStaffNo));
            log.warning(msg);
            throw new AuthorisationException(msg);
        }
    }

    /**
     * Authorise sign and date review reviewer.
     *
     * @param empStaffNo employee staff number
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     */
    public void authoriseSignCurrentReviewReviewer(final int empStaffNo) throws NotLoggedInException, StaffNoException, AuthorisationException {

        // validation
        if (empStaffNo < 0) throw new StaffNoException();

        if (authServer.getLoggedIn() instanceof Reviewer)
            logGrant(format("sign off {0}'s review", empStaffNo));

        else {
            final String msg = msgDeny(format("sign off {0}''s review", empStaffNo), format("{0} is not a reviewer", authServer.getLoggedIn()
                                                                                                                               .toString()));
            log.warning(msg);
            throw new AuthorisationException(msg);
        }
    }

    /**
     * Authorise sign and date review employee.
     *
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws ReviewersNotDiffException when reviewers are not two different people
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     */
    public void authoriseSignCurrentReviewEmployee() throws NotLoggedInException, ReviewersNotDiffException, AuthorisationException {
        // @formatter:off

        // retrieve the person logged in
        final U loggedIn = authServer.getLoggedIn();

        if (loggedIn instanceof Employee) {

            // employee signing their own review
            if (((Employee) loggedIn).isReviewDue())
                logGrant("sign off their review");

            // employee but no review is due
            else {
                final String msg = msgDeny("sign off their review", "their review is not due.");
                log.warning(msg);
                throw new ReviewersNotDiffException(msg);
            }

        // not employee
        } else {
            final String msg = msgDeny("sign off their review", format("{0} is not an employee", loggedIn.toString()));
            log.warning(msg);
            throw new AuthorisationException(msg);
        }
        // @formatter:on
    }

    /**
     * Authorise create review.
     *
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     */
    public void authoriseCreateReview() throws NotLoggedInException, AuthorisationException {

        // retrieve the person logged in
        final U loggedIn = authServer.getLoggedIn();

        // check that it's an employee
        if ((loggedIn instanceof Employee)) logGrant("create a review");

        else {
            final String msg = msgDeny("create a review", "only employees can create review records.");
            log.warning(msg);
            throw new AuthorisationException(msg);
        }
    }

    /**
     * Authorise allocate reviewers.
     *
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     */
    public void authoriseAllocateReviewers() throws NotLoggedInException, AuthorisationException {

        // retrieve the person logged in
        final U loggedIn = authServer.getLoggedIn();
        final PermissionLevel permissionLevel = authServer.getPermissionLevel();

        // HR employee not logged in as user or reviewer
        // so manager as well but not director as prof Barnes insisted directors aren't employees
        if (((loggedIn instanceof Employee)) && (loggedIn.getSection() == HUMAN_RESOURCES) && (permissionLevel != USER) && (permissionLevel != REVIEWER))
            logGrant("allocate reviewer");

        else {
            final String msg = msgDeny("allocate reviewer", "you are not not logged in as an HR employee");
            log.warning(msg);
            throw new AuthorisationException(msg);
        }
    }

    private String msgDeny(final String accessToWhat, final String reason) throws NotLoggedInException {
        return format("Denied authorisation to {0} to {1} because {2}.", accessToWhat, authServer.getLoggedIn()
                                                                                                 .toString(), reason);
    }

    private void logGrant(final String accessToWhat) throws NotLoggedInException {
        log.info(msgGrant(accessToWhat));
    }

    private String msgGrant(final String accessToWhat) throws NotLoggedInException {
        return format("Successfully granted authorisation to {0} to {1}.", accessToWhat, authServer.getLoggedIn()
                                                                                                   .toString());
    }
}
