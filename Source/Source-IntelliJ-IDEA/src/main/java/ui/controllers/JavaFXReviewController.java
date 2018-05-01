package ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import yuconz.exceptions.AuthorisationException;
import yuconz.exceptions.NoSuchRecordException;
import yuconz.exceptions.NotLoggedInException;
import yuconz.exceptions.StaffNoException;
import yuconz.exceptions.ValidationException;
import yuconz.records.reviews.Recommendation;
import yuconz.records.reviews.ReviewBuilder;
import yuconz.records.staff.User;

import static java.lang.Integer.parseInt;
import static java.lang.System.exit;
import static java.lang.System.getProperty;
import static java.util.Optional.ofNullable;
import static yuconz.records.reviews.Recommendation.REMAIN_IN_POST;

/**
 * JavaFX review controller. Handles events in the review view.
 *
 * @param <U> the type parameter
 */
@SuppressWarnings({"ClassHasNoToStringMethod", "ClassWithTooManyFields", "AlibabaClassNamingShouldBeCamel"})
public final class JavaFXReviewController<U extends User> extends BaseController<U> implements Initializable {

    @FXML
    private Text reviewer2NameLabel;
    @FXML
    private Text reviewer1NameLabel;
    @FXML
    private Text employeeJobTitleLabel;
    @FXML
    private Text employeeNameLabel;
    @FXML
    private Text employeeStaffNoLabel;
    @FXML
    private ChoiceBox<Recommendation> recommendationChoiceBox;
    @FXML
    private TextArea objectivesAndAchievementsTextArea;
    @FXML
    private TextArea reviewerCommentsTextArea;
    @FXML
    private TextArea performanceSummaryTextArea;
    @FXML
    private TextArea goalsAndPlannedOutcomesTextArea;
    @FXML
    private Text reviewer2StaffNoLabel;
    @FXML
    private Text reviewer1StaffNoLabel;

    /**
     * Handle amend review.
     *
     * @param event the event that triggers this handler
     */
    public void handleAmendReview(final MouseEvent event) {

        try {
            final int employeeStaffNo = parseInt(employeeStaffNoLabel.getText());
            final String employeeName = ofNullable(employeeNameLabel).map(Text::getText)
                                                                     .orElse("");
            final String employeeJobTitle = employeeJobTitleLabel.getText();
            final int reviewer1StaffNo = parseInt(reviewer1StaffNoLabel.getText());
            final int reviewer2StaffNo = parseInt(reviewer2StaffNoLabel.getText());
            final String reviewer1Name = ofNullable(reviewer1NameLabel).map(Text::getText)
                                                                       .orElse("");
            final String reviewer2Name = ofNullable(reviewer2NameLabel).map(Text::getText)
                                                                       .orElse("");

            // @formatter:off
            //noinspection LocalVariableNamingConvention
            final String goalsAndPlannedOutcomes = ofNullable(goalsAndPlannedOutcomesTextArea).map(TextInputControl::getText).orElse("");
            final String performanceSummary = ofNullable(performanceSummaryTextArea).map(TextInputControl::getText).orElse("");
            final String objectivesAndAchievements = ofNullable(objectivesAndAchievementsTextArea).map(TextInputControl::getText).orElse("");
            final String reviewerComments = ofNullable(reviewerCommentsTextArea).map(TextInputControl::getText).orElse("");

            Recommendation recommendation  ;

            try {
                recommendation = ofNullable(recommendationChoiceBox)
                                         .map(ChoiceBox::getValue)
                                         .orElse(REMAIN_IN_POST);
            } catch (final ClassCastException ignored) {
                recommendation = REMAIN_IN_POST;
                getDisplayer().displayInfo("Please select recommendation.");
            }

            getDatabase().amendCurrentReview(employeeStaffNo, new ReviewBuilder(
                    employeeStaffNo, employeeName, employeeJobTitle,
                    reviewer1StaffNo, reviewer1Name, reviewer2StaffNo,
                    reviewer2Name)
                    .setGoalsAndPlannedOutcomes(goalsAndPlannedOutcomes)
                    .setPerformanceSummary(performanceSummary)
                    .setObjectivesAndAchievements(objectivesAndAchievements)
                    .setRecommendation(recommendation)
                    .setReviewerComments(reviewerComments));
            // @formatter:on

            getDisplayer().displayInfo("Successfully amended the review.");

            ((Node) event.getSource()).getScene()
                                      .getWindow()
                                      .hide();

        } catch (final ValidationException e) {
            e.printStackTrace();
            getDisplayer().displayError(e.getMessage());
        } catch (final NotLoggedInException e) {
            getDisplayer().displayError(e.getMessage());
            e.printStackTrace();
            getDisplayer().displayLogInDialog();
        } catch (final AuthorisationException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({"TryWithIdenticalCatches", "ProhibitedExceptionThrown"})
    @Override
    public void initialize(final URL url, final ResourceBundle bundle) {
        try {
            final String staffNo = getProperty("reqRevEmpStaffNo", null);
            if (staffNo == null)
                throw new RuntimeException("No value set for requestedReview.");
            final ReviewBuilder review = getDatabase().readCurrentReview(parseInt(staffNo));
            goalsAndPlannedOutcomesTextArea.setText(ofNullable(review.getGoalsAndPlannedOutcomes()).orElse(""));
            performanceSummaryTextArea.setText(ofNullable(review.getPerformanceSummary()).orElse(""));
            reviewerCommentsTextArea.setText(ofNullable(review.getReviewerComments()).orElse(""));
            objectivesAndAchievementsTextArea.setText(ofNullable(review.getObjectivesAndAchievements()).orElse(""));
            reviewer1StaffNoLabel.setText(String.valueOf(review.reviewer1StaffNo));
            reviewer2StaffNoLabel.setText(String.valueOf(review.reviewer2StaffNo));
            employeeJobTitleLabel.setText(ofNullable(review.employeeJobTitle).orElse(""));
            employeeNameLabel.setText(ofNullable(review.employeeName).orElse(""));
            employeeStaffNoLabel.setText(String.valueOf(review.employeeStaffNo));
            reviewer1NameLabel.setText(ofNullable(review.getReviewer1Name()).orElse(""));
            reviewer2NameLabel.setText(ofNullable(review.getReviewer2Name()).orElse(""));
            recommendationChoiceBox.setValue(ofNullable(review.getRecommendation()).orElse(REMAIN_IN_POST));
            return;
        } catch (final NoSuchRecordException e) {
            e.printStackTrace();
            getDisplayer().displayError(e.getMessage());
        } catch (final AuthorisationException e) {
            e.printStackTrace();
            getDisplayer().displayError(e.getMessage());
        } catch (final NotLoggedInException e) {
            e.printStackTrace();
            getDisplayer().displayError(e.getMessage());
            getDisplayer().displayLogInDialog();
        } catch (final StaffNoException e) {
            getDisplayer().displayError(e.getMessage());
            e.printStackTrace();
            exit(1);
        }

        // close before fully loading if fails
        employeeJobTitleLabel.getScene()
                             .getWindow()
                             .hide();
    }
}

