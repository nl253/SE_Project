package ui.controllers;

import java.util.logging.Logger;
import ui.view.JavaFXViewDisplayer;
import ui.view.ViewDisplayer;
import yuconz.AuthenticationServer;
import yuconz.HRDatabase;
import yuconz.records.staff.User;

import static java.util.logging.Logger.getAnonymousLogger;

/**
 * Base controller.
 *
 * @param <U> the type parameter
 */
@SuppressWarnings({"WeakerAccess", "ClassHasNoToStringMethod", "CyclicClassDependency"})
abstract class BaseController<U extends User> {

    /**
     * Logger for the class.
     */
    protected static final Logger LOG = getAnonymousLogger();
    /**
     * The Displayer.
     */
    @SuppressWarnings("ProtectedField")
    protected final ViewDisplayer displayer = JavaFXViewDisplayer.getInstance();
    private final AuthenticationServer<U> server = AuthenticationServer.getInstance();
    private final HRDatabase<U> database = HRDatabase.getInstance();

    /**
     * Gets displayer.
     *
     * @return the displayer
     */
    protected final ViewDisplayer getDisplayer() {
        return displayer;
    }

    /**
     * Gets server.
     *
     * @return the server
     */
    public final AuthenticationServer<U> getServer() {
        return server;
    }

    /**
     * Gets database.
     *
     * @return the database
     */
    public final HRDatabase<U> getDatabase() {
        return database;
    }
}
