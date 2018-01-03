package uk.ac.kent.controllers;

import java.util.logging.Logger;
import javafx.stage.Stage;
import javax.persistence.Transient;
import uk.ac.kent.Database;

/**
 * @author norbert
 */

@SuppressWarnings({"ClassHasNoToStringMethod", "AbstractClassWithoutAbstractMethods"})
public abstract class BaseController {

    @Transient
    private static final Logger log = Logger.getAnonymousLogger();

    @SuppressWarnings("PackageVisibleField")
    private Database database;

    private Stage stage;

    @SuppressWarnings({"ConstructorNotProtectedInAbstractClass", "PublicConstructorInNonPublicClass"})
    public BaseController() {}

    @SuppressWarnings({"PublicConstructorInNonPublicClass", "ConstructorNotProtectedInAbstractClass"})
    public BaseController(final Stage stage, final Database database) {
        this.stage = stage;
        this.database = database;
    }

    final Database getDatabase() {
        return database;
    }

    final Stage getStage() {
        return stage;
    }

    final void setStage(final Stage stage) {
        this.stage = stage;
    }
}
