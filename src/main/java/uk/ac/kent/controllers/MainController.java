package uk.ac.kent.controllers;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.kent.Database;

/**
 * @author norbert
 */

@SuppressWarnings({"ParameterHidesMemberVariable", "unused", "FieldNamingConvention"})
public class MainController extends BaseController {

    MainController(final Stage stage, Database database) {
        super(stage, database);
    }

    private void displayMainView(final Stage stage) throws IOException {
        final Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/main.fxml"));
        stage.setScene(new Scene(root, 300.0, 200.0));
        stage.show();
    }
}
