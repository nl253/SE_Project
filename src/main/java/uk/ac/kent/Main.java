package uk.ac.kent;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The entry point into the application.
 *
 * @author norbert
 */

@SuppressWarnings({"PublicMethodNotExposedInInterface", "ClassHasNoToStringMethod", "ConstantConditions"})
public final class Main extends Application {

    private static final double WIDTH = 300.0;
    private static final double HEIGHT = 200.0;

    /** Logger for the class */
    private static final Logger log = Logger.getAnonymousLogger();

    private Stage stage;

    @Override
    public final void start(final Stage stage) throws java.io.IOException {
        this.stage = stage;
        displayLoginView();
    }

    @FXML
    private void displayLoginView() throws IOException {
        final Parent root = FXMLLoader.load(getClass().getClassLoader()
                                                    .getResource("views/login.fxml"));
        final Scene scene = new Scene(root, 300.0, 200.0);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void displayMainView() throws IOException {
        final Parent root = FXMLLoader.load(getClass().getClassLoader()
                                                    .getResource("views/main.fxml"));
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
    }

    public static void main(final String[] args) {
        new Database().populate();
        launch(args);
    }
}
