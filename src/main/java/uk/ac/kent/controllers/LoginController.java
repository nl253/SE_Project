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

/**
 * Manages the login view.
 *
 * @author Norbert
 */

@SuppressWarnings("ClassHasNoToStringMethod")
public final class LoginController extends BaseController {

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Button btn;

    /**
     * @param parent parent {@link Stage}
     */

    public LoginController(final Stage parent) {
        super(parent);
    }

    /**
     * @throws IOException
     */

    @SuppressWarnings("ConstantConditions")
    private void displayLoginView() throws IOException {
        final Parent root = FXMLLoader.load(getClass().getClassLoader()
                                                    .getResource("views/login.fxml"));
        final Scene scene = new Scene(root);
        getStage().setScene(scene);
        getStage().show();
    }

    /**
     * @throws IOException
     */

    @SuppressWarnings("ConstantConditions")
    private void displayBadCredentialsView() throws IOException {
        final Parent root = FXMLLoader.load(getClass().getClassLoader()
                                                    .getResource("views/badCredentials.fxml"));
        final Scene scene = new Scene(root);
        getStage().setScene(scene);
        getStage().show();
    }

    /**
     * @param mouseEvent mouse event (click)
     * @throws IOException
     */

    @SuppressWarnings({"LawOfDemeter", "PublicMethodNotExposedInInterface"})
    @FXML
    public void handleLoginButtonClicked(final MouseEvent mouseEvent) throws IOException {
        if (authenticate()) displayDashboardView();
        else displayBadCredentialsView();
    }

    /**
     * @throws IOException
     */

    private void displayDashboardView() throws IOException {
        new DashboardController(getStage()).displayMainView();
        getStage().close();
    }

    /**
     * Authenticate a user based on credentials in the fields (username and password).
     *
     * @return true if a user record matching these credentials exists
     */

    private boolean authenticate() {
        // return !HRDatabase
        //         .query("SELECT e FROM Employee e WHERE CONCAT(e.personalDetails.lastName, e.id) = ?0 AND e.password = ?1", username, password)
        //         .isEmpty();
        return false;
    }

    /**
     * Display the first,  main view associated with this {@link BaseController}.
     *
     * @throws IOException
     */

    @Override
    public void displayMainView() throws IOException {
        displayLoginView();
    }
}
