package yuconz;

import java.util.Collection;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import yuconz.exceptions.AlreadyLoggedInException;
import yuconz.exceptions.DateException;
import yuconz.exceptions.NoSuchRecordException;
import yuconz.exceptions.NotLoggedInException;
import yuconz.exceptions.NullValueException;
import yuconz.exceptions.StaffNoException;
import yuconz.exceptions.StringFormatException;
import yuconz.exceptions.ValidationException;
import yuconz.records.MockDataGenerator;
import yuconz.records.credentials.CredentialsRecord;
import yuconz.records.staff.Director;
import yuconz.records.staff.Employee;
import yuconz.records.staff.Manager;
import yuconz.records.staff.Section;
import yuconz.records.staff.User;

import static org.junit.jupiter.api.Assertions.*;
import static yuconz.PermissionLevel.*;

/**
 * The type Authentication server test.
 *
 * @param <U> the type parameter
 * @param <A> the type parameter
 */
@SuppressWarnings({"MessageMissingOnJUnitAssertion", "MethodWithTooExceptionsDeclared", "MultipleExceptionsDeclaredOnTestMethod", "OverlyBroadThrowsClause", "ClassWithTooManyDependencies", "ClassWithTooManyMethods", "ConstantConditions", "OverlyCoupledClass"})
final class AuthenticationServerTest<U extends User, A> extends MockDataGenerator<A> {

    private final HRDatabase<U> database = HRDatabase.getInstance();
    @SuppressWarnings("rawtypes")
    private final AuthenticationServer server = AuthenticationServer.getInstance();

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        try {
            server.logout();
        } catch (final NotLoggedInException ignored) {}
    }

    /**
     * Logout without login.
     */
    @RepeatedTest(10)
    void logoutWithoutLogin() {
        assertThrows(NotLoggedInException.class, server::logout);
    }

    /**
     * Login without logout.
     *
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws StringFormatException on badly formatted input
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws DateException when the date is invalid
     * @throws NullValueException when a value is null
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    void loginWithoutLogout() throws AlreadyLoggedInException, StringFormatException, StaffNoException, NoSuchRecordException, DateException, NullValueException {
        final CredentialsRecord record = injectRandUser();
        server.login(record.getUsername(), record.getPassword(), USER);
        final CredentialsRecord cred2 = injectRandUser();
        assertThrows(AlreadyLoggedInException.class, () -> server.login(cred2.getUsername(), cred2.getPassword(), USER));
    }

    @SuppressWarnings({"unchecked", "SwitchStatement"})
    private CredentialsRecord injectRandUser() throws StaffNoException, StringFormatException, DateException {
        final int staffNo = randStaffNo();
        final Section sect = randSection();
        U user = null;
        final CredentialsRecord cred = randCredentialsRecord(staffNo);

        //noinspection SwitchStatementWithoutDefaultBranch
        switch (getRandom().nextInt(3)) {
            case 0:
                user = (U) new Employee(staffNo, sect);
                break;
            case 1:
                user = (U) new Manager(staffNo, sect);
                break;
            case 2:
                user = (U) new Director(staffNo, sect);
                break;
        }
        database.staffDetails.get()
                             .add(user);
        server.credentials.get()
                          .add(cred);
        return cred;
    }

    /**
     * Gets credentials.
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    void getCredentials() {
        assertNotNull(server.credentials.get());
        assertTrue(() -> server.credentials.get() instanceof Collection);
    }

    /**
     * Gets logged in.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    @Tag("Authentication")
    void getLoggedIn() {
        assertThrows(NotLoggedInException.class, server::getLoggedIn);
    }

    /**
     * Gets permission level.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    @Tag("Authentication")
    void getPermissionLevel() {
        assertThrows(NotLoggedInException.class, server::getPermissionLevel);
    }

    /**
     * Login employee.
     *
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws StringFormatException on badly formatted input
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws DateException when the date is invalid
     * @throws NullValueException when a value is null
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    void loginEmployee() throws StaffNoException, StringFormatException, NotLoggedInException, AlreadyLoggedInException, NoSuchRecordException, DateException, NullValueException {
        final CredentialsRecord record = injectEmployee();
        server.login(record.getUsername(), record.getPassword(), EMPLOYEE);
        assertNotNull(server.getPermissionLevel());
        assertNotNull(server.getLoggedIn());
    }

    private CredentialsRecord injectEmployee() throws StaffNoException, StringFormatException, DateException {
        final int staffNo = randStaffNo();
        final CredentialsRecord record = randCredentialsRecord(staffNo);
        final Employee emp = new Employee(staffNo, randSection());

        database.staffDetails.get()
                             .add((U) emp);
        server.credentials.get()
                          .add(record);

        return record;
    }

    /**
     * Login director as employee.
     *
     * @throws StringFormatException on badly formatted input
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws DateException when the date is invalid
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    void loginDirectorAsEmployee() throws StringFormatException, StaffNoException, DateException {
        final CredentialsRecord user = injectDir();
        assertThrows(NoSuchRecordException.class, () -> server.login(user.getUsername(), user.getPassword(), EMPLOYEE));
    }

    private CredentialsRecord injectDir() throws StaffNoException, StringFormatException, DateException {
        final int staffNo = randStaffNo();
        final CredentialsRecord cred = randCredentialsRecord(staffNo);
        final Director dir = new Director(staffNo, randSection());

        database.staffDetails.get()
                             .add((U) dir);
        server.credentials.get()
                          .add(cred);

        return cred;
    }

    /**
     * Login manager.
     *
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws StringFormatException on badly formatted input
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws DateException when the date is invalid
     * @throws NullValueException when a value is null
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    void loginManager() throws NotLoggedInException, StaffNoException, AlreadyLoggedInException, StringFormatException, NoSuchRecordException, DateException, NullValueException {
        final CredentialsRecord cred = injectManager();
        server.login(cred.getUsername(), cred.getPassword(), MANAGER);
        assertNotNull(server.getPermissionLevel());
        assertNotNull(server.getLoggedIn());

    }

    private CredentialsRecord injectManager() throws StaffNoException, StringFormatException, DateException {
        final int staffNo = randStaffNo();
        final CredentialsRecord cred = randCredentialsRecord(staffNo);
        final Manager man = new Manager(staffNo, randSection());

        database.staffDetails.get()
                             .add((U) man);
        server.credentials.get()
                          .add(cred);

        return cred;
    }

    /**
     * Login director.
     *
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws StringFormatException on badly formatted input
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws DateException when the date is invalid
     * @throws NullValueException when a value is null
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    void loginDirector() throws StaffNoException, StringFormatException, NotLoggedInException, AlreadyLoggedInException, NoSuchRecordException, DateException, NullValueException {
        final CredentialsRecord cred = injectDir();
        server.login(cred.getUsername(), cred.getPassword(), DIRECTOR);
        assertNotNull(server.getPermissionLevel());
        assertNotNull(server.getLoggedIn());
    }

    /**
     * Login employee as director.
     *
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws StringFormatException on badly formatted input
     * @throws DateException when the date is invalid
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    void loginEmployeeAsDirector() throws StaffNoException, StringFormatException, DateException {
        final CredentialsRecord user = injectEmployee();
        assertThrows(NoSuchRecordException.class, () -> server.login(user.getUsername(), user.getPassword(), DIRECTOR));
    }

    /**
     * Login director as user.
     *
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws StringFormatException on badly formatted input
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws DateException when the date is invalid
     * @throws NullValueException when a value is null
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    void loginDirectorAsUser() throws StaffNoException, StringFormatException, NotLoggedInException, AlreadyLoggedInException, NoSuchRecordException, DateException, NullValueException {
        final CredentialsRecord record = injectDir();
        server.login(record.getUsername(), record.getPassword(), USER);
        assertNotNull(server.getPermissionLevel());
        assertNotNull(server.getLoggedIn());
    }

    /**
     * Login manager as user.
     *
     * @throws ValidationException the validation exception
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    void loginManagerAsUser() throws ValidationException, NotLoggedInException, AlreadyLoggedInException {
        final CredentialsRecord cred = injectManager();
        server.login(cred.getUsername(), cred.getPassword(), USER);
        assertNotNull(server.getPermissionLevel());
        assertNotNull(server.getLoggedIn());
    }

    /**
     * Login manager as employee.
     *
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws StringFormatException on badly formatted input
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws DateException when the date is invalid
     * @throws NullValueException when a value is null
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    void loginManagerAsEmployee() throws NotLoggedInException, StringFormatException, AlreadyLoggedInException, StaffNoException, NoSuchRecordException, DateException, NullValueException {
        final CredentialsRecord cred = injectManager();
        server.login(cred.getUsername(), cred.getPassword(), EMPLOYEE);
        assertNotNull(server.getPermissionLevel());
        assertNotNull(server.getLoggedIn());
    }

    /**
     * Login manager as reviewer.
     *
     * @throws StringFormatException on badly formatted input
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws DateException when the date is invalid
     * @throws NullValueException when a value is null
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    void loginManagerAsReviewer() throws StringFormatException, StaffNoException, AlreadyLoggedInException, NotLoggedInException, NoSuchRecordException, DateException, NullValueException {
        final CredentialsRecord cred = injectManager();
        server.login(cred.getUsername(), cred.getPassword(), REVIEWER);
        assertNotNull(server.getPermissionLevel());
        assertNotNull(server.getLoggedIn());
    }

    /**
     * Login director as reviewer.
     *
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws StringFormatException on badly formatted input
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws DateException when the date is invalid
     * @throws NullValueException when a value is null
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    void loginDirectorAsReviewer() throws NotLoggedInException, StringFormatException, StaffNoException, AlreadyLoggedInException, NoSuchRecordException, DateException, NullValueException {
        final CredentialsRecord cred = injectDir();
        server.login(cred.getUsername(), cred.getPassword(), REVIEWER);
        assertNotNull(server.getPermissionLevel());
        assertNotNull(server.getLoggedIn());
    }

    /**
     * Login manager as director.
     *
     * @throws StringFormatException on badly formatted input
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws DateException when the date is invalid
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    void loginManagerAsDirector() throws StringFormatException, StaffNoException, DateException {
        final CredentialsRecord record = injectManager();
        assertThrows(NoSuchRecordException.class, () -> server.login(record.getUsername(), record.getPassword(), DIRECTOR));
    }

    /**
     * Login employee as user.
     *
     * @throws StringFormatException on badly formatted input
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws DateException when the date is invalid
     * @throws NullValueException when a value is null
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    void loginEmployeeAsUser() throws StringFormatException, StaffNoException, AlreadyLoggedInException, NotLoggedInException, NoSuchRecordException, DateException, NullValueException {
        final CredentialsRecord record = injectEmployee();
        server.login(record.getUsername(), record.getPassword(), USER);
        assertNotNull(server.getPermissionLevel());
        assertNotNull(server.getLoggedIn());
    }

    /**
     * Login employee as manager.
     *
     * @throws StringFormatException on badly formatted input
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws DateException when the date is invalid
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    void loginEmployeeAsManager() throws StringFormatException, StaffNoException, DateException {
        final CredentialsRecord record = injectEmployee();
        assertThrows(NoSuchRecordException.class, () -> server.login(record.getUsername(), record.getPassword(), MANAGER));
    }

    /**
     * Empty username.
     */
    @RepeatedTest(10)
    @Tag("Validation")
    void emptyUsername() {
        assertThrows(StringFormatException.class, () -> server.login("", randPassword(), randPermissionLevel()));
    }

    /**
     * Null username.
     */
    @RepeatedTest(10)
    @Tag("Validation")
    void nullUsername() {
        assertThrows(NullValueException.class, () -> server.login(null, randPassword(), randPermissionLevel()));
    }

    /**
     * Bad username format.
     */
    @SuppressWarnings("StringConcatenationMissingWhitespace")
    @RepeatedTest(10)
    @Tag("Validation")
    void badUsernameFormat() {
        IntStream.range(1, 4)
                 .forEach((int i) -> assertThrows(StringFormatException.class, () -> server.login("emp123".substring(0, i), randPassword(), randPermissionLevel())));

        IntStream.range(1, 6)
                 .forEach((int i) -> assertThrows(StringFormatException.class, () -> server.login("emp123" + "emp123".substring(0, i), randPassword(), randPermissionLevel())));
    }

    /**
     * Empty password.
     */
    @Tag("Validation")
    @RepeatedTest(10)
    void emptyPassword() {
        assertThrows(StringFormatException.class, () -> server.login(randUsername(), "", randPermissionLevel()));
    }

    /**
     * Null password.
     */
    @Tag("Validation")
    @RepeatedTest(10)
    void nullPassword() {
        assertThrows(NullValueException.class, () -> server.login(randUsername(), null, randPermissionLevel()));
    }

    /**
     * Bad credentials.
     */
    @Tag("Authentication")
    @RepeatedTest(10)
    void badCredentials() {
        assertThrows(NoSuchRecordException.class, () -> server.login("dir123", "pa", randPermissionLevel()));
        assertThrows(NoSuchRecordException.class, () -> server.login("som123", randPassword(), randPermissionLevel()));
    }

    /**
     * Null permission level.
     */
    @RepeatedTest(10)
    void nullPermLvl() {
        assertThrows(NullValueException.class, () -> server.login(randUsername(), randPassword(), null));
    }
}

