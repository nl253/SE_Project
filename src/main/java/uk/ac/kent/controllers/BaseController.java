package uk.ac.kent.controllers;

import java.io.IOException;
import java.text.MessageFormat;
import javafx.stage.Stage;
import uk.ac.kent.Database;

/**
 * Base class for all controllers.
 *
 * @author Norbert
 */

@SuppressWarnings({"ConstructorNotProtectedInAbstractClass", "PublicMethodNotExposedInInterface", "WeakerAccess"})
public abstract class BaseController {

    private Stage stage;
    private Stage parent;
    private Database database;

    /**
     * Display the first,  main view associated with this {@link BaseController}.
     *
     * @throws IOException when fxml file cannot be found
     */

    public abstract void displayMainView() throws IOException;

    /**
     * @param database a reference to the {@link Database}
     * @param parent a reference to the parent {@link Stage}
     */

    public BaseController(final Database database, final Stage parent) {
        this.database = database;
        this.parent = parent;
        stage = new Stage();
    }

    /**
     * A version of the constructor with parameters supplied.
     */

    public BaseController() {
        this(new Database(), new Stage());
    }

    public final Stage getParent() {
        return parent;
    }

    public final void setParent(final Stage parent) {
        this.parent = parent;
    }

    public final Stage getStage() {
        return stage;
    }

    public final Database getDatabase() {
        return database;
    }

    @SuppressWarnings("DesignForExtension")
    @Override
    public String toString() {
        return MessageFormat.format("{0}", getClass().getName());
    }
}
