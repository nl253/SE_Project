package yuconz;

import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;
import yuconz.exceptions.AlreadyLoggedInException;
import yuconz.exceptions.NoSuchRecordException;
import yuconz.exceptions.NotLoggedInException;
import yuconz.exceptions.NullValueException;
import yuconz.exceptions.StringFormatException;
import yuconz.records.Records;
import yuconz.records.credentials.CredentialsRecord;
import yuconz.records.credentials.MockCredentials;
import yuconz.records.staff.Director;
import yuconz.records.staff.Employee;
import yuconz.records.staff.Manager;
import yuconz.records.staff.Reviewer;
import yuconz.records.staff.User;

import static java.text.MessageFormat.format;
import static yuconz.StringValidator.isValidUsername;

/**
 * Authentication server.
 *
 * @param <U> the type parameter
 */
@SuppressWarnings({"ClassHasNoToStringMethod", "NewExceptionWithoutArguments", "unchecked", "Singleton", "ClassWithTooManyDependencies", "PublicMethodNotExposedInInterface", "OptionalUsedAsFieldOrParameterType", "CyclicClassDependency"})
public final class AuthenticationServer<U extends User> {

    @SuppressWarnings("StaticVariableOfConcreteClass")
    private static final AuthenticationServer<? extends User> SELF = new AuthenticationServer<>();
    private final Logger log;

    /**
     * The Credentials.
     */
    @SuppressWarnings("PackageVisibleField")
    Records<CredentialsRecord> credentials;

    private Optional<U> loggedIn;
    private Optional<PermissionLevel> permLvl;

    @SuppressWarnings("unused")
    private AuthenticationServer() {
        loggedIn = Optional.empty();
        permLvl = Optional.empty();
        log = Logger.getAnonymousLogger();
        credentials = MockCredentials.getInstance();
        log.info(format("{0} initialised.", getClass().getSimpleName()));
    }

    /**
     * Gets instance.
     *
     * @param <U> the type parameter
     * @return the instance
     */
    @SuppressWarnings({"WeakerAccess", "unchecked"})
    public static <U extends User> AuthenticationServer<U> getInstance() {
        return (AuthenticationServer<U>) SELF;
    }

    /**
     * Logout.
     *
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     */
    public void logout() throws NotLoggedInException {
        if (!loggedIn.isPresent() || !permLvl.isPresent())
            throw new NotLoggedInException();
        else {
            loggedIn = Optional.empty();
            permLvl = Optional.empty();
            log.info("Successfully logged out.");
        }
    }

    /**
     * Login.
     *
     * @param username the username
     * @param password the password
     * @param permissionLevel the permission level
     * @throws StringFormatException on badly formatted input
     * @throws AlreadyLoggedInException when you are attempting to log in without prior logging out
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws NullValueException when a value is null
     */
    @SuppressWarnings({"AlibabaSwitchStatement", "OverlyComplexMethod"})
    public void login(final String username, final String password, final PermissionLevel permissionLevel) throws StringFormatException, AlreadyLoggedInException, NoSuchRecordException, NullValueException {

        if (permissionLevel == null)
            throw new NullValueException("Received a null value for permission level.");

        for (final String s : new String[]{username, password})
            if (s == null)
                throw new NullValueException("Received a null value.");
            else if (s.isEmpty())
                throw new StringFormatException("Received an empty value.");

        if (loggedIn.isPresent() || permLvl.isPresent()) {
            final String msg = format("Error, user \"{0}\" is already logged in.", loggedIn.map(Object::toString)
                                                                                           .orElse(username));
            log.severe(msg);
            throw new AlreadyLoggedInException(msg);
        }

        if (!isValidUsername(username)) {
            final String msg = "Bad username format.";
            log.warning(msg);
            throw new StringFormatException(msg);
        }

        if (password.length() <= 1) {
            final String msg = "Password is too short.";
            log.warning(msg);
            throw new StringFormatException(msg);
        }

        final int staffNo = lookupStaffNo(username, password);

        Stream<U> matchingUsers = ((Stream<U>) HRDatabase.getInstance().staffDetails.get()
                                                                                    .stream()).filter(user -> user.getStaffNo() == staffNo);

        log.info(format("Privilege level selected: {0}.", permissionLevel));

        switch (permissionLevel) {

            case MANAGER:
                matchingUsers = matchingUsers.filter(Manager.class::isInstance);
                break;

            case DIRECTOR:
                matchingUsers = matchingUsers.filter(Director.class::isInstance);
                break;

            case REVIEWER:
                matchingUsers = matchingUsers.filter(Reviewer.class::isInstance);
                break;

            case EMPLOYEE:
                matchingUsers = matchingUsers.filter(Employee.class::isInstance);
                break;

            case USER:
                break;
        }
        // @formatter:on

        loggedIn = Optional.of(matchingUsers.findFirst()
                                            .orElseThrow(() -> {
                                                final String msg = "No user with these credentials.";
                                                log.warning(msg);
                                                return new NoSuchRecordException(msg);
                                            }));

        permLvl = Optional.of(permissionLevel);

        log.info(format("Successfully logged in as: {0} with permission: {1}.", loggedIn.map(Object::toString)
                                                                                        .orElse("user"), permLvl.map(Object::toString)
                                                                                                                .orElse("unknown")));
    }

    private int lookupStaffNo(final String username, final String password) throws NoSuchRecordException {
        final int staffNo = credentials.get()
                                       .stream()
                                       .filter(cred -> cred.getUsername()
                                                           .equals(username) && cred.getPassword()
                                                                                    .equals(password))
                                       .findAny()
                                       .orElseThrow(() -> {
                                           final String msg = "No user with the credentials.";
                                           log.warning(msg);
                                           return new NoSuchRecordException(msg);
                                       })
                                       .getStaffNo();
        log.info(format("Successfully matched credentials with staff no: {0}.", staffNo));
        return staffNo;
    }

    /**
     * Gets permission level.
     *
     * @return the permission level
     *
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     */
    public PermissionLevel getPermissionLevel() throws NotLoggedInException {
        return permLvl.orElseThrow(() -> {
            final NotLoggedInException e = new NotLoggedInException();
            log.severe(e.getMessage());
            return e;
        });
    }

    /**
     * Gets logged in.
     *
     * @return the logged in
     *
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     */
    public U getLoggedIn() throws NotLoggedInException {
        return loggedIn.orElseThrow(() -> {
            final NotLoggedInException e = new NotLoggedInException();
            log.severe(e.getMessage());
            return e;
        });
    }
}
