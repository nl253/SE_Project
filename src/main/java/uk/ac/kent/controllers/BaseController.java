package uk.ac.kent.controllers;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;
import javafx.stage.Stage;

/**
 * Base class for all controllers.
 *
 * @author Norbert
 */

@SuppressWarnings({"ConstructorNotProtectedInAbstractClass", "PublicMethodNotExposedInInterface", "WeakerAccess"})
public abstract class BaseController {

    private final Stage stage;
    private Stage parent;

    /**
     * Display the first,  main view associated with this {@link BaseController}.
     *
     * @throws IOException when fxml file cannot be found
     */

    public abstract void displayMainView() throws IOException;

    /**
     * @param parent a reference to the parent {@link Stage}
     */

    public BaseController(final Stage parent) {
        this.parent = parent;
        stage = new Stage();
    }

    public final Optional<Stage> getParent() {
        return Optional.ofNullable(parent);
    }

    public final void setParent(final Stage parent) {
        this.parent = parent;
    }

    public final Stage getStage() {
        return stage;
    }

    @SuppressWarnings("DesignForExtension")
    @Override
    public String toString() {
        return MessageFormat.format("{0}", getClass().getName());
    }
}
