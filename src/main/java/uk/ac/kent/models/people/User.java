package uk.ac.kent.models.people;

import java.text.MessageFormat;
import java.util.logging.Logger;
import uk.ac.kent.AuthenticationServer;

/**
 * @author Norbert
 */

@SuppressWarnings({"PublicMethodNotExposedInInterface", "AbstractClassWithOnlyOneDirectInheritor", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class User {

    /** Logger for the class */
    protected static final Logger log = Logger.getAnonymousLogger();

    private int id;

    private String username, password;

    public final String getUsername() {
        return username;
    }

    public final void setUsername(final String username) {
        this.username = username;
    }

    public final String getPassword() {
        return password;
    }

    public final void setPassword(final String password) {
        this.password = password;
    }

    public final int getId() {
        return id;
    }

    public final void setId(final int id) {
        this.id = id;
    }

    void login() {
        if (AuthenticationServer.authenticate(username, password)) return;
        else return;
    }

    void logout() {}

    @Override
    public final String toString() {
        return MessageFormat.format("{0}<id={0}, username={1}>", getClass()
                .getName(), id, username);
    }
}
