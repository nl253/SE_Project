import javafx.application.Application;
import javafx.stage.Stage;
import ui.view.JavaFXViewDisplayer;

import static java.lang.System.setProperty;

/**
 * Main - entry point into the application.
 */
public final class Main extends Application {

    /**
     * Main - entry point into the application.
     *
     * @param args the args
     */
    public static void main(final String... args) {
        // initialise pretty formatting
        setProperty("java.util.logging.SimpleFormatter.format", "%1$tH:%1$tM:%1$tS %4$-6s %2$s %5$s%6$s%n");
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        JavaFXViewDisplayer.getInstance()
                           .displayLogInDialog();
    }
}
