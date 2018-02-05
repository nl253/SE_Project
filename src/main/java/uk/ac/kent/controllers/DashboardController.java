package uk.ac.kent.controllers;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Manages the dashboard view.
 *
 * @author Norbert
 */

@SuppressWarnings("PublicMethodNotExposedInInterface")
public class DashboardController extends BaseController {

    /**
     * @param parent a reference to the parent {@link Stage}
     */

    @SuppressWarnings("WeakerAccess")
    public DashboardController(final Stage parent) {
        super(parent);
    }

    /**
     * Display the first, main view associated with this {@link BaseController}.
     *
     * @throws IOException when the fxml could not be found
     */

    private void displayDashboardView() throws IOException {
        final Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/dashboard.fxml"));
        getStage().setScene(new Scene(root));
        getStage().show();
    }

    /**
     * Display the first,  main view associated with this {@link BaseController}.
     *
     * @throws IOException
     */

    @Override
    public final void displayMainView() throws IOException {
        displayDashboardView();
    }
}
