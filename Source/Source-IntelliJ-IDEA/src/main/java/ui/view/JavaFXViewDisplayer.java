package ui.view;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import static java.lang.System.exit;
import static java.lang.System.setProperty;
import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;
import static java.util.logging.Logger.getAnonymousLogger;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.control.ButtonType.CLOSE;
import static javafx.scene.control.ButtonType.OK;

/**
 * JavaFX view displayer.
 */
@SuppressWarnings({"Singleton", "AlibabaClassNamingShouldBeCamel"})
public final class JavaFXViewDisplayer implements ViewDisplayer {

    private static final double REVIEW_X = 940.0;
    private static final double REVIEW_Y = 640.0;
    @SuppressWarnings("StaticVariableOfConcreteClass")
    private static JavaFXViewDisplayer displayer;
    private final Logger log;

    private JavaFXViewDisplayer() {
        log = getAnonymousLogger();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    @SuppressWarnings({"NonThreadSafeLazyInitialization", "StaticVariableUsedBeforeInitialization"})
    public static ViewDisplayer getInstance() {
        if (displayer == null) displayer = new JavaFXViewDisplayer();
        return displayer;
    }

    @Override
    public void displayInfo(final String msg) {
        final Alert alert = new Alert(INFORMATION, msg, OK);
        alert.showAndWait();
        if (alert.getResult()
                 .equals(OK)) alert.close();
    }

    @Override
    public void displayError(final String msg) {
        final Alert alert = new Alert(ERROR, msg, CLOSE);
        alert.showAndWait();
        if (alert.getResult()
                 .equals(CLOSE)) alert.close();
    }

    @Override
    public void displayLogInDialog() {
        display("loginDialog", "Yuconz Records Storage System").showAndWait();
    }

    private Parent loadParent(final String fxmlFile) {
        final String path = Paths.get("Source","Source-IntelliJ-IDEA", "src", "main", "resources", "views", format("{0}.fxml", fxmlFile)).toString();
        try {
            final Parent parent = FXMLLoader.load(Paths.get(System.getProperty("user.dir"), path).toFile().getAbsoluteFile().toURI().toURL());
            log.info(format("Successfully loaded {0}.", path));
            log.info(format("Displaying {0} view.", fxmlFile));
            return parent;
        } catch (final IOException e) {
            log.severe(format("Failed to load {0}.", path));
            displayError(e.getMessage());
            //noinspection CallToSystemExit
        }
        exit(1);
        return null;
    }

    @Override
    public void displayDashboard() {
        display("dashboard", "Yuconz Dashboard").show();
    }

    @SuppressWarnings("AccessOfSystemProperties")
    @Override
    public void displayPersonalDetails(final int staffNo) {

        // workaround
        setProperty("reqPerDetStaffNo", String.valueOf(staffNo));

        display("personalDetails", "Personal Details").showAndWait();
    }

    @Override
    public void displayCurrentReview(final int staffNo) {

        // workaround
        setProperty("reqRevEmpStaffNo", String.valueOf(staffNo));

        final Stage stage = display("currentReview", "Current Annual Performance Review");

        // DO NOT TOUCH
        stage.setMaxHeight(REVIEW_Y);
        stage.setMaxWidth(REVIEW_X);
        stage.setMinWidth(REVIEW_X);
        stage.setMaxWidth(REVIEW_X);
        stage.setWidth(REVIEW_X);
        stage.setHeight(REVIEW_Y);

        // freeze when displaying *current* review
        stage.showAndWait();
        stage.toFront();
    }

    @Override
    public void displayPastReview(final int staffNo, final int year) {

        setProperty("reqPastRevYear", String.valueOf(year));
        setProperty("reqPastRevEmpStaffNo", String.valueOf(staffNo));

        final Stage stage = display("completedReview", "Past Completed Annual Performance Review Record");

        // DO NOT TOUCH
        stage.setMaxHeight(REVIEW_Y);
        stage.setMaxWidth(REVIEW_X);
        stage.setMinWidth(REVIEW_X);
        stage.setMaxWidth(REVIEW_X);
        stage.setWidth(REVIEW_X);
        stage.setHeight(REVIEW_Y);

        // don't freeze when displaying *past* reviews
        // you need to be able to open others in parallel
        stage.show();

        stage.toFront();
    }

    private Stage display(final String file, final String title) {
        final Stage stage = new Stage();

        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(new Scene(requireNonNull(loadParent(file))));

        return stage;
    }
}
