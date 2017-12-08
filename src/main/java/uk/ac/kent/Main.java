package uk.ac.kent;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The entry point into the application.
 */

@SuppressWarnings({"PublicMethodNotExposedInInterface", "ClassNamingConvention", "PublicConstructor", "ClassWithoutLogger"})
public final class Main extends Application {

    private static final double HEIGHT = 275.0;
    private static final double WIDTH = 300.0;

    @Override
    public final void start(final Stage stage) throws java.io.IOException {
        final Parent root = FXMLLoader
                .load(getClass().getResource("main.fxml"));
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
