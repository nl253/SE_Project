package ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import yuconz.PermissionLevel;
import yuconz.exceptions.AlreadyLoggedInException;
import yuconz.exceptions.NoSuchRecordException;
import yuconz.exceptions.NullValueException;
import yuconz.exceptions.StringFormatException;
import yuconz.records.staff.User;

import static java.lang.System.exit;
import static yuconz.StringValidator.isValidUsername;

/**
 * JavaFX login dialog controller. Handles events in the login dialog view.
 *
 * @param <U> the type parameter
 */
@SuppressWarnings({"ClassHasNoToStringMethod", "AlibabaClassNamingShouldBeCamel", "ClassWithTooManyTransitiveDependencies", "WeakerAccess"})
public final class JavaFXLoginDialogController<U extends User> extends BaseController<U> implements Initializable {

    private static final String BAD_FMT_COLOUR = "#eea584";
    private static final String GOOD_FMT_COLOUR = "rgba(109,237,94,0.67)";

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox<PermissionLevel> permissionLevelChoiceBox;

    /**
     * Handle exit btn clicked.
     */
    @SuppressWarnings({"MethodMayBeStatic", "CallToSystemExit"})
    @FXML
    public void handleExitBtnClicked() {
        LOG.info("Exiting");
        exit(0);
    }

    /**
     * Handle notify user about incorrect or badly formatted user about incorrect or badly formatted username input.
     */
    public void handleNotifyUsernameInput() {
        usernameTextField.setStyle("-fx-control-inner-background: " + (isValidUsername(usernameTextField.getText()) ? GOOD_FMT_COLOUR : BAD_FMT_COLOUR) + ';');
    }

    @SuppressWarnings("FeatureEnvy")
    @FXML
    private void handleLogInBtnClicked(final MouseEvent event) {
        if ((permissionLevelChoiceBox == null) || (permissionLevelChoiceBox.getValue() == null) || (usernameTextField == null)) {
            getDisplayer().displayInfo("You must choose a permissionLevelChoiceBox.");
            return;
        }
        try {
            getServer().login(usernameTextField.getText(), passwordField.getText(), permissionLevelChoiceBox.getValue());

            // hide the login view if successful
            ((Node) event.getSource()).getScene()
                                      .getWindow()
                                      .hide();

            // FIXME
            // getDisplayer().displayInfo(format("Logged in as {0}.", getServer()
            //         .getLoggedIn().getClass().getSimpleName()));

            getDisplayer().displayDashboard();

        } catch (final StringFormatException e) {
            LOG.warning(e.getMessage());
            getDisplayer().displayError(e.getMessage());
        } catch (final ClassCastException ignored) {
            LOG.warning("Privilege lvl not selected.");
            getDisplayer().displayError("Choose a privileged level.");
        } catch (final AlreadyLoggedInException e) {
            LOG.severe(e.getMessage());
            getDisplayer().displayError(e.getMessage());
        } catch (final NoSuchRecordException ignored) {
            final String msg = "Incorrect credentials.";
            LOG.warning(msg);
            getDisplayer().displayError(msg);
        } catch (final NullValueException ignored) {
            final String msg = "Lack of input.";
            LOG.warning(msg);
            getDisplayer().displayError(msg);
        }
    }

    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        // set an initial value
        permissionLevelChoiceBox.setValue(PermissionLevel.USER);
    }
}
