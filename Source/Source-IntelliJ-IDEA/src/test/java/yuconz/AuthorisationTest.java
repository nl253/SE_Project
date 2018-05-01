package yuconz;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import yuconz.exceptions.NotLoggedInException;
import yuconz.exceptions.StaffNoException;
import yuconz.records.MockDataGenerator;
import yuconz.records.personal.ValidatingPersonalDetailsBuilder;
import yuconz.records.staff.User;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The type Authorisation test.
 *
 * @param <A> the type parameter
 * @param <U> the type parameter
 */
final class AuthorisationTest<A, U extends User> extends MockDataGenerator<A> {

    private final AuthenticationServer<U> authenticationServer = AuthenticationServer.getInstance();
    private HRDatabase<U> database;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        database = HRDatabase.getInstance();
        try {
            database.personalDetails.get()
                                    .add(new ValidatingPersonalDetailsBuilder(randStaffNo()).create());
        } catch (final StaffNoException ignored) {}
    }


    /**
     * Tear down.
     */
    @AfterEach
    void tearDown() {
        database = null;
        try {
            authenticationServer.logout();
        } catch (final NotLoggedInException ignored) {}
    }

    /**
     * Create personal details without logging in.
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    void createPersonalDetailsWithoutLoggingIn() {
        assertThrows(NotLoggedInException.class, () -> database.createPersonalDetails(randStaffNo()));
    }

    /**
     * Amend personal details without logging in.
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    void amendPersonalDetailsWithoutLoggingIn() {
        assertThrows(NotLoggedInException.class, () -> database.amendPersonalDetails(randStaffNo(), new ValidatingPersonalDetailsBuilder(randStaffNo()).create()));
    }

    /**
     * Read personal details without logging in.
     */
    @RepeatedTest(10)
    @Tag("PersonalDetails")
    void readPersonalDetailsWithoutLoggingIn() {
        assertThrows(NotLoggedInException.class, () -> database.readPersonalDetails(randStaffNo()));
    }

    /**
     * Read current review without logging in.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void readCurrentReviewWithoutLoggingIn() {
        assertThrows(NotLoggedInException.class, () -> database.readCurrentReview(randStaffNo()));
    }

    /**
     * Amend current review without logging in.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void amendCurrentReviewWithoutLoggingIn() {
        assertThrows(NotLoggedInException.class, () -> database.amendCurrentReview(randStaffNo(), randReviewBuilder()));
    }

    /**
     * Create review without logging in.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void createReviewWithoutLoggingIn() {
        assertThrows(NotLoggedInException.class, () -> database.createReview(randStaffNo()));
    }

    /**
     * Read completed review without logging in.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void readCompletedReviewWithoutLoggingIn() {
        assertThrows(NotLoggedInException.class, () -> database.readCompletedReview(randStaffNo(), now().minusYears(RAND.nextInt(5) + 1)
                                                                                                        .getYear()));
    }

    /**
     * Allocate reviewers without logging in.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void allocateReviewersWithoutLoggingIn() {
        assertThrows(NotLoggedInException.class, () -> database.allocateReviewers(randStaffNo(), randStaffNo(), randStaffNo()));
    }
}
