package yuconz.records.credentials;

import yuconz.exceptions.StaffNoException;
import yuconz.exceptions.StringFormatException;
import yuconz.records.Record;

import static java.text.MessageFormat.format;
import static yuconz.StringValidator.isValidUsername;

/**
 * Credentials record.
 */
@SuppressWarnings("ClassNamePrefixedWithPackageName")
public final class CredentialsRecord implements Record {

    private final int staffNo;
    private final String username;
    private final String password;

    /**
     * Instantiates a new Credentials record.
     *
     * @param staffNo the staff number
     * @param username the username
     * @param password the password
     * @throws StringFormatException on badly formatted input
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    public CredentialsRecord(final int staffNo, final String username, final String password) throws StringFormatException, StaffNoException {
        if ((username == null) || !isValidUsername(username))
            throw new StringFormatException("Invalid username.");
        else if ((password == null) || password.isEmpty())
            throw new StringFormatException("Invalid password.");
        else if (staffNo < 0) throw new StaffNoException();
        this.staffNo = staffNo;
        this.username = username;
        this.password = password;
    }

    @Override
    public int getStaffNo() {
        return staffNo;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return format("CredentialsRecord<staffNo={0}, username={1}>", staffNo, username);
    }
}

