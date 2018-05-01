package ui.controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import yuconz.exceptions.AuthorisationException;
import yuconz.exceptions.NoSuchRecordException;
import yuconz.exceptions.NotLoggedInException;
import yuconz.exceptions.StaffNoException;
import yuconz.records.reviews.CompletedReview;
import yuconz.records.reviews.Recommendation;
import yuconz.records.staff.User;

import static java.lang.Integer.parseInt;
import static java.lang.System.getProperty;

/**
 * JavaFX completed review controller. Handles events in the completed review view.
 *
 * @param <U> the type parameter
 */
@SuppressWarnings({"ClassHasNoToStringMethod", "ClassWithTooManyFields"})
public final class JavaFXCompletedReviewController<U extends User> extends BaseController<U> implements Initializable {

    @FXML
    private Text employeeNameLabel;
    @FXML
    private Text employeeSectionText;
    @FXML
    private Text employeeJobTitleLabel;
    @FXML
    private Text employeeStaffNoLabel;
    @FXML
    private Text reviewer1StaffNoLabel;
    @FXML
    private Text reviewer2StaffNoLabel;
    @FXML
    private Text reviewer2NameLabel;
    @FXML
    private Text reviewer1NameLabel;
    @FXML
    private Text objectivesAndAchievementsText;
    @FXML
    private Text goalsAndPlannedOutcomesText;
    @FXML
    private Text recommendationsText;
    @FXML
    private Text reviewerCommentsText;
    @FXML
    private Text performanceSummaryText;

    @SuppressWarnings({"TryWithIdenticalCatches", "OverlyLongMethod", "FeatureEnvy", "ProhibitedExceptionThrown"})
    @Override
    public void initialize(final URL url, final ResourceBundle bundle) {

        final String reviewYear = getProperty("reqPastRevYear", null);

        if (reviewYear == null) {
            final String msg = "Did not specify which past completed review to display.";
            getDisplayer().displayError(msg);
            //noinspection ProhibitedExceptionThrown
            throw new RuntimeException(msg);
        }

        final String empStaffNo = getProperty("reqPastRevEmpStaffNo", null);

        if (empStaffNo == null) {
            final String msg = "You need to specify the employee staff number.";
            getDisplayer().displayError(msg);
            throw new RuntimeException(msg);
        }

        try {

            final int staffNo = parseInt(empStaffNo);
            final int year = parseInt(reviewYear);
            final CompletedReview review = getDatabase().readCompletedReview(staffNo, year);

            employeeStaffNoLabel.setText(String.valueOf(review.employeeStaffNo));

            reviewer1StaffNoLabel.setText(String.valueOf(review.reviewer1StaffNo));

            reviewer2StaffNoLabel.setText(String.valueOf(review.reviewer2StaffNo));

            goalsAndPlannedOutcomesText.setText(Optional.ofNullable(review.goalsAndPlannedOutcomes)
                                                        .orElse(""));

            objectivesAndAchievementsText.setText(Optional.ofNullable(review.objectivesAndAchievements)
                                                          .orElse(""));

            reviewer1NameLabel.setText(Optional.ofNullable(review.reviewer1Name)
                                               .orElse(""));

            reviewer2NameLabel.setText(Optional.ofNullable(review.reviewer2Name)
                                               .orElse(""));

            employeeNameLabel.setText(Optional.ofNullable(review.employeeName)
                                              .orElse(""));

            employeeJobTitleLabel.setText(Optional.ofNullable(review.employeeJobTitle)
                                                  .orElse(""));

            reviewerCommentsText.setText(Optional.ofNullable(review.reviewerComments)
                                                 .orElse(""));

            //noinspection DynamicRegexReplaceableByCompiledPattern
            recommendationsText.setText(Optional.ofNullable(review.recommendation)
                                                .orElse(Recommendation.REMAIN_IN_POST)
                                                .name()
                                                .toLowerCase()
                                                .replace("_", " "));

            performanceSummaryText.setText(Optional.ofNullable(review.performanceSummary)
                                                   .orElse(""));


        } catch (final NoSuchRecordException e) {
            getDisplayer().displayError(e.getMessage());
            // // close before fully loading if fails
            // employeeNameLabel.getScene()
            //                  .getWindow()
            //                  .hide();
        } catch (final NotLoggedInException e) {
            getDisplayer().displayError(e.getMessage());
            getDisplayer().displayLogInDialog();
        } catch (final AuthorisationException e) {
            getDisplayer().displayError(e.getMessage());
        } catch (final StaffNoException e) {
            getDisplayer().displayError(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Handle close btn clicked.
     *
     * @param event the event that triggers this handler
     */
    @SuppressWarnings("MethodMayBeStatic")
    public void handleCloseBtnClicked(final MouseEvent event) {
        ((Node) event.getSource()).getScene()
                                  .getWindow()
                                  .hide();
    }
}
