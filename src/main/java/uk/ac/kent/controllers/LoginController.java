package uk.ac.kent.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uk.ac.kent.Database;

/**
 * @author norbert
 */

@SuppressWarnings("ClassHasNoToStringMethod")
public final class LoginController extends BaseController {

    @FXML
    private PasswordField password;
    @FXML
    private TextField username;
    @FXML
    private Button btn;

    public LoginController() {}

    public LoginController(final Stage stage, final Database database) {
        super(stage, database);
    }

    private void displayMainView() {}

    void displayLoginView() throws IOException {
        final Parent root = FXMLLoader.load(getClass().getClassLoader()
                                                    .getResource("views/login.fxml"));
        final Scene scene = new Scene(root, 300.0, 200.0);
        getStage().setScene(scene);
        getStage().show();
    }

    @SuppressWarnings({"LawOfDemeter", "PublicMethodNotExposedInInterface"})
    public void handleLoginButtonClicked(final MouseEvent mouseEvent) {
        getDatabase()
                .query("SELECT COUNT(*) FROM users WHERE username = :username AND PASSWORD = :password", username, password);

    }
}
