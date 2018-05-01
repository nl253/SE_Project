package ui.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import yuconz.PermissionLevel;
import yuconz.exceptions.AlreadySignedException;
import yuconz.exceptions.AuthorisationException;
import yuconz.exceptions.NoSuchRecordException;
import yuconz.exceptions.NotLoggedInException;
import yuconz.exceptions.NullValueException;
import yuconz.exceptions.RecordExistsException;
import yuconz.exceptions.ReviewNotDueException;
import yuconz.exceptions.ReviewerAssignedException;
import yuconz.exceptions.ReviewersNotAssignedException;
import yuconz.exceptions.ReviewersNotDiffException;
import yuconz.exceptions.StaffNoException;
import yuconz.exceptions.StringFormatException;
import yuconz.records.Record;
import yuconz.records.staff.Employee;
import yuconz.records.staff.User;

import static java.lang.Integer.parseInt;
import static java.lang.System.exit;
import static java.lang.System.setProperty;
import static java.text.MessageFormat.format;
import static java.time.LocalDate.now;
import static java.util.regex.Pattern.compile;
import static javafx.collections.FXCollections.observableList;
import static yuconz.DateValidator.MAX_YEAR;
import static yuconz.DateValidator.MIN_YEAR;
import static yuconz.PermissionLevel.REVIEWER;
import static yuconz.PermissionLevel.USER;
import static yuconz.StringValidator.isValidStaffNo;
import static yuconz.StringValidator.isValidYear;
import static yuconz.records.staff.Section.HUMAN_RESOURCES;

/**
 * JavaFX dashboard controller. Handles events in the dashboard view.
 *
 * @param <U> the type parameter
 */
@SuppressWarnings({"ClassHasNoToStringMethod", "FeatureEnvy", "LawOfDemeter", "PublicMethodNotExposedInInterface", "AlibabaClassNamingShouldBeCamel", "ClassWithTooManyDependencies", "ClassWithTooManyTransitiveDependencies", "ClassWithTooManyFields", "WeakerAccess", "ClassWithTooManyMethods", "MethodMayBeStatic", "FieldNamingConvention"})
public final class JavaFXDashboardController<U extends User> extends BaseController<U> implements Initializable {

    private static final Pattern EXTRACT_STAFF_NO_REGEX = compile("^\\s*(?<staffNo>\\d+).*");
    private static final String GOOD_FMT_COLOUR = "rgba(109,237,94,0.67)";
    private static final String BAD_FMT_COLOUR = "#eea584";

    // EMPLOYEE

    @FXML
    private Tab employeeTab;
    @FXML
    private Button accessMyCurrentReviewBtn;
    @FXML
    private Button createReviewBtn;
    @FXML
    private Button signAndDateReviewEmpBtn;
    @FXML
    private Label employeeReviewStateLabel;
    @FXML
    private TextField employeeReadCompletedReviewYearTextField;

    // HR

    @FXML
    private Tab hrTab;
    @FXML
    private TextField hrReadPersonalDetailsStaffNoTextField;

    // HR EMPLOYEE

    @FXML
    private Tab hrEmployeeTab;
    @FXML
    private TextField hrEmployeeReadCompletedReviewYearTextField;
    @FXML
    private ChoiceBox<String> reviewers1stHalfChoiceBox;
    @FXML
    private ChoiceBox<String> reviewers2ndHalfChoiceBox;
    @FXML
    private ChoiceBox<String> unassignedEmployeesChoiceBox;
    @FXML
    private TextField hrCreatePersonalDetailsStaffNoTextField;
    @FXML
    private TextField readPastReviewHrEmployeeStaffNoTextField;

    // USER

    @FXML
    private Label staffNoLabel;
    @FXML
    private Label permissionLevelLabel;
    @FXML
    private Label positionLabel;
    @FXML
    private Label sectionLabel;

    // REVIEWER

    @FXML
    private Tab reviewerTab;
    @FXML
    private ChoiceBox<String> pendingReviewsChoiceBox;
    @FXML
    private TextField reviewerReadCompletedReviewYearTextField;

    private boolean typedInvalidStaffNo(final StaffNoTextBox textBox) {

        TextField field = null;

        switch (textBox) {
            case ACCESS_PERSONAL_DETAILS:
                field = hrReadPersonalDetailsStaffNoTextField;
                break;

            case CREATE_PERSONAL_DETAILS:
                field = hrCreatePersonalDetailsStaffNoTextField;
                break;

            case READ_PAST_REVIEWS_HR_EMPLOYEE:
                field = readPastReviewHrEmployeeStaffNoTextField;
                break;

        }

        if (field != null) {
            final String staffNo = field.getText();
            return (staffNo == null) || !isValidStaffNo(staffNo) || (parseInt(staffNo) <= 0);
        } else return true;
    }

    /**
     * Handle create personal details.
     *
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     */
    @SuppressWarnings("NewMethodNamingConvention")
    public void handleCreatePersonalDetails() throws NotLoggedInException, AuthorisationException {
        try {
            getDatabase().createPersonalDetails(parseInt(hrCreatePersonalDetailsStaffNoTextField.getText()));
            getDisplayer().displayInfo("New personal details record created.");
        } catch (RecordExistsException | StaffNoException | NumberFormatException e) {
            LOG.severe(e.getMessage());
            getDisplayer().displayError(e.getMessage());
        }
    }

    /**
     * Handle read my personal details.
     *
     * @param event the event that triggers this handler
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     */
    public void handleReadMyPersonalDetails(final MouseEvent event) throws NotLoggedInException {

        // hide dashboard
        ((Node) event.getSource()).getScene()
                                  .getWindow()
                                  .hide();
        // show personal details
        getDisplayer().displayPersonalDetails(getServer().getLoggedIn()
                                                         .getStaffNo());
        // show dashboard
        ((Stage) ((Node) event.getSource()).getScene()
                                           .getWindow()).show();
    }

    /**
     * Handle perform review.
     */
    public void handlePerformReview() {
        if (selectedFromDropDown(Dropdown.PENDING_REVIEWS)) {
            setProperty("reqRevEmpStaffNo", String.valueOf(pendingReviewsChoiceBox.getValue()));
            getDisplayer().displayCurrentReview(getInitialStaffNo(pendingReviewsChoiceBox.getValue()));
        } else getDisplayer().displayError("You must choose an employee.");
    }

    private boolean selectedFromDropDown(final Dropdown dropdown) {

        Predicate<ChoiceBox> predicate = null;
        ChoiceBox choiceBox = null;

        switch (dropdown) {

            case REVIEWERS_1ST_HALF:
                choiceBox = reviewers1stHalfChoiceBox;
                predicate = x -> getInitialStaffNo((String) x.getValue()) > 0;
                break;

            case REVIEWERS_2ND_HALF:
                choiceBox = reviewers2ndHalfChoiceBox;
                predicate = x -> getInitialStaffNo((String) x.getValue()) > 0;
                break;

            case EMPLOYEE_FOR_REVIEW:
                predicate = x -> getInitialStaffNo((String) x.getValue()) > 0;
                choiceBox = unassignedEmployeesChoiceBox;
                break;

            case PENDING_REVIEWS:
                predicate = x -> getInitialStaffNo((String) x.getValue()) > 0;
                choiceBox = pendingReviewsChoiceBox;
                break;
        }

        if (choiceBox != null) {
            final Object value = choiceBox.getValue();
            return (value != null) && predicate.test(choiceBox);
        } else return false;
    }

    /**
     * Handle notify user about incorrect or badly formatted validate year input.
     *
     * @param event the event that triggers this handler
     */
    public void handleNotifyValidateYearInput(final KeyEvent event) {
        colorizeValidate((TextField) event.getSource(), node -> {
            if (node != null) {
                final String text = node.getText();
                if ((text != null) && isValidYear(text)) {
                    final int parsed = parseInt(text);
                    return (parsed < MAX_YEAR) && (parsed > MIN_YEAR);
                }
                return false;
            }
            return false;
        });
    }

    /**
     * Handle notify user about incorrect or badly formatted validate staff number input.
     *
     * @param event the event that triggers this handler
     */
    public void handleNotifyValidateStaffNoInput(final KeyEvent event) {
        colorizeValidate((TextField) event.getSource(), node -> (node != null) && (node.getText() != null) && isValidStaffNo(node.getText()));
    }

    private void colorizeValidate(final TextField node, final Predicate<TextField> predicate) {
        node.setStyle("-fx-control-inner-background: " + (predicate.test(node) ? GOOD_FMT_COLOUR : BAD_FMT_COLOUR) + ';');
    }

    /**
     * Handle read personal details.
     *
     * @param event the event that triggers this handler
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     */
    public void handleReadPersonalDetails(final MouseEvent event) throws NotLoggedInException {

        // hide the dashboard
        ((Node) event.getSource()).getScene()
                                  .getWindow()
                                  .hide();

        if (typedInvalidStaffNo(StaffNoTextBox.ACCESS_PERSONAL_DETAILS)) {
            getDisplayer().displayError("Bad format of staff number.");
            return;
        }

        try {
            final int staffNo = parseInt(hrReadPersonalDetailsStaffNoTextField.getText());

            // workaround MUST BE HERE
            getDatabase().readPersonalDetails(staffNo);

            getDisplayer().displayPersonalDetails(staffNo);


        } catch (final NoSuchRecordException ignored) {
            getDisplayer().displayError("No personal details for this staff number.");
        } catch (final AuthorisationException ignored) {
            getDisplayer().displayError("You cannot access this record.");
        } catch (final StaffNoException ignored) {
            getDisplayer().displayError("Bad staff number format.");
        }

        // on close, show the dashboard
        ((Stage) ((Node) event.getSource()).getScene()
                                           .getWindow()).show();
    }

    @SuppressWarnings({"StringToUpperCaseOrToLowerCaseWithoutLocale", "DynamicRegexReplaceableByCompiledPattern"})
    @Override
    public void initialize(final URL url, final ResourceBundle bundle) {
        try {
            initUser();
            initReviewer();
            initEmployee();
            initHR();
            initHREmployee();
        } catch (final NotLoggedInException e) {
            LOG.severe("Trying to initialise dashboard without being logged in.");
            e.printStackTrace();
            getDisplayer().displayError(e.getMessage());
            getDisplayer().displayLogInDialog();
        } catch (final StaffNoException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    @SuppressWarnings("DynamicRegexReplaceableByCompiledPattern")
    private void initUser() throws NotLoggedInException {

        LOG.info("Updating UI for a user.");

        positionLabel.setText(getServer().getLoggedIn()
                                         .getClass()
                                         .getSimpleName()
                                         .toLowerCase());

        staffNoLabel.setText(String.valueOf(getServer().getLoggedIn()
                                                       .getStaffNo()));
        //noinspection DynamicRegexReplaceableByCompiledPattern
        permissionLevelLabel.setText(getServer().getPermissionLevel()
                                                .toString()
                                                .toLowerCase()
                                                .replace("_", " "));
        //noinspection DynamicRegexReplaceableByCompiledPattern
        sectionLabel.setText(String.valueOf(getServer().getLoggedIn()
                                                       .getSection())
                                   .toLowerCase()
                                   .replace("_", " "));
    }

    @SuppressWarnings("MethodWithMultipleReturnPoints")
    private void initHREmployee() throws NotLoggedInException {

        final PermissionLevel permissionLevel = getServer().getPermissionLevel();
        final U loggedIn = getServer().getLoggedIn();

        /* HR employee (so implicit manager as well) not logged in as just user
        or reviewer but not HR director because they are not an employee
        which prof Barnes insisted on */
        if (!(loggedIn instanceof Employee) || (loggedIn.getSection() != HUMAN_RESOURCES) || (permissionLevel == USER) || (permissionLevel == REVIEWER)) {
            hrEmployeeTab.setDisable(true);
            return; // this return is crucial
        }

        LOG.info("Updating UI for an HR employee.");

        final List<String> allReviewees = getDatabase().listUnallocatedReviewees();

        unassignedEmployeesChoiceBox.setItems(observableList(allReviewees));

        if (!allReviewees.isEmpty())
            unassignedEmployeesChoiceBox.setValue(allReviewees.get(0));

        final List<String> allReviewers = getDatabase().listReviewers();

        // prevent from assigning the same reviewer twice
        final int half = Math.floorDiv(allReviewers.size(), 2);

        // split into 2 disjoint lists
        final List<String> firstHalf = allReviewers.subList(0, half);
        final List<String> secondHalf = allReviewers.subList(half, allReviewers.size());

        // fill both dropdowns
        reviewers1stHalfChoiceBox.setItems(observableList(firstHalf));
        reviewers2ndHalfChoiceBox.setItems(observableList(secondHalf));

        // select the first element iff there is something to choose from
        if (!firstHalf.isEmpty())
            reviewers1stHalfChoiceBox.setValue(firstHalf.get(0));

        // select the first element iff there is something to choose from
        if (!secondHalf.isEmpty())
            reviewers2ndHalfChoiceBox.setValue(secondHalf.get(0));

        LOG.info("Logged in as an HR employee.");
    }

    private void initHR() throws NotLoggedInException {
        final PermissionLevel perm = getServer().getPermissionLevel();
        if ((getServer().getLoggedIn()
                        .getSection() == HUMAN_RESOURCES) && (perm != USER) && (perm != REVIEWER)) {
            LOG.info("Updating UI for an HR staff member.");
        } else hrTab.setDisable(true);
    }

    private void initEmployee() throws NotLoggedInException {

        final PermissionLevel perm = getServer().getPermissionLevel();
        final U loggedIn = getServer().getLoggedIn();

        // employee but not logged in as user
        if ((!(loggedIn instanceof Employee)) || (perm == USER) || (perm == REVIEWER)) {
            employeeTab.setDisable(true);
            return;
        }

        final Employee employee = ((Employee) loggedIn);

        LOG.info("Updating UI for an employee.");

        // enable all at the beginning
        createReviewBtn.setDisable(false);
        accessMyCurrentReviewBtn.setDisable(false);
        signAndDateReviewEmpBtn.setDisable(false);

        /* Here I need to consider the following cases:

        a) not due for a review (not within 2 weeks or already had it this year)

        b) due for a review
            I. needs an HR employee to allocate reviewers
            II. already created their review record
            III. not created their review record
            IV. signed and dated their review */

        // not due to have a review - block all
        if (!employee.isReviewDue() || getDatabase().reviewedThisYear(employee.getStaffNo())) {

            LOG.info("Review not due.");

            // cannot do anything
            createReviewBtn.setDisable(true);
            accessMyCurrentReviewBtn.setDisable(true);
            signAndDateReviewEmpBtn.setDisable(true);

            employeeReviewStateLabel.setText("Not due to have a review.");

            return; // this return is crucial

        } else try {

            if (!getDatabase().wasAllocatedReviewers(employee.getStaffNo())) {

                LOG.info("Employee is due to have a review.");
                LOG.info("An HR employee needs to allocate reviewers.");

                // cannot create
                createReviewBtn.setDisable(true);

                // cannot amend
                accessMyCurrentReviewBtn.setDisable(true);

                // cannot sign and date
                signAndDateReviewEmpBtn.setDisable(true);

                employeeReviewStateLabel.setText("You are due to have a review. An HR Employee needs to allocate two reviewers.");

                return; // this return is crucial

            } else if (getDatabase().didCreateReview(employee.getStaffNo())) {

                LOG.info("Review already created.");

                // cannot create
                createReviewBtn.setDisable(true);

                employeeReviewStateLabel.setText("You are in the process of a review. You have already created a review record and made it accessible to your reviewers. You can amend it and after that sign and date it.");

                if (getDatabase().didEmployeeSignAndDateReview(employee.getStaffNo())) {

                    LOG.info("Already signed and dated current review.");

                    // cannot do anything
                    accessMyCurrentReviewBtn.setDisable(true);
                    signAndDateReviewEmpBtn.setDisable(true);

                    employeeReviewStateLabel.setText("You have signed and dated your current review. When your reviewers sign and date it as well it will become read-only.");
                }

            } else {

                LOG.info("Review needs to be created.");

                // can create
                createReviewBtn.setDisable(false);

                // cannot amend
                accessMyCurrentReviewBtn.setDisable(true);

                // cannot sign and date
                signAndDateReviewEmpBtn.setDisable(true);

                employeeReviewStateLabel.setText("You have been assigned reviewers. You are due to have a review. You must now create the review and make it accessible for your reviewers.");

            }
        } catch (final StaffNoException e) {
            e.printStackTrace();
            getDisplayer().displayError(e.getMessage());
            LOG.severe(e.getMessage());
            exit(1);
        }
    }

    private void initReviewer() throws NotLoggedInException, StaffNoException {


        if (getServer().getPermissionLevel() != REVIEWER) {
            reviewerTab.setDisable(true);
            return; // the return must be here
        }

        LOG.info("Updating UI for a reviewer.");

        final Record reviewer = getServer().getLoggedIn();

        pendingReviewsChoiceBox.setItems(observableList(getDatabase().listUnallocatedReviewees(reviewer.getStaffNo())));

        // set the value to the first pending review
        if (pendingReviewsChoiceBox.getItems()
                                   .isEmpty()) {
            LOG.info("No reviews to perform.");
            pendingReviewsChoiceBox.setDisable(true);
        } else
            pendingReviewsChoiceBox.setValue(pendingReviewsChoiceBox.getItems()
                                                                    .get(0));

        LOG.info("Logged in as a reviewer.");
    }

    /**
     * Handle create new review.
     *
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws RecordExistsException when the record already exists
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws ReviewersNotDiffException when reviewers are not two different people
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws NullValueException when a value is null
     * @throws StringFormatException on badly formatted input
     */
    public void handleCreateNewReview() throws StaffNoException, RecordExistsException, NoSuchRecordException, NotLoggedInException, ReviewersNotDiffException, AuthorisationException, StringFormatException, NullValueException {
        try {
            getDatabase().createReview(getServer().getLoggedIn()
                                                  .getStaffNo());
            getDisplayer().displayInfo("Review successfully created.");
            // re-init buttons in the employee tab
            initEmployee();
        } catch (final ReviewersNotAssignedException e) {
            LOG.severe(e.getMessage());
            getDisplayer().displayError(e.getMessage());
        }
    }

    /**
     * Handle allocate reviewers.
     *
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws RecordExistsException when the record already exists
     * @throws ReviewNotDueException when a review is not due for the employee
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     */
    @SuppressWarnings("TypeMayBeWeakened")
    public void handleAllocateReviewers() throws NotLoggedInException, NoSuchRecordException, StaffNoException, RecordExistsException, ReviewNotDueException, AuthorisationException {

        // even though I am selecting default values those boxes *can be empty*
        if (!selectedFromDropDown(Dropdown.EMPLOYEE_FOR_REVIEW) || !selectedFromDropDown(Dropdown.REVIEWERS_1ST_HALF) || !selectedFromDropDown(Dropdown.REVIEWERS_2ND_HALF)) {
            getDisplayer().displayInfo("Select employee, reviewer 1 and reviewer 2.");
            return;
        }

        // get staff numbers
        final int empStaffNo = getInitialStaffNo(unassignedEmployeesChoiceBox.getValue());
        final int rev1StaffNo = getInitialStaffNo(reviewers1stHalfChoiceBox.getValue());
        final int rev2StaffNo = getInitialStaffNo(reviewers2ndHalfChoiceBox.getValue());

        try {
            getDatabase().allocateReviewers(empStaffNo, rev1StaffNo, rev2StaffNo);

            getDisplayer().displayInfo("Successfully assigned reviewers.");

            // re-init buttons in the employee tab (a bit of special case for HR employee allocating reviewers to herself)
            // convenience so that changes are reflected in the UI automatically
            initEmployee();

            // re-init buttons in the HR employee tab
            initHREmployee();

        } catch (ReviewerAssignedException | ReviewersNotDiffException e) {
            getDisplayer().displayError(e.getMessage());
        }
    }

    private Integer getInitialStaffNo(final String str) {
        final Matcher matcher = EXTRACT_STAFF_NO_REGEX.matcher(str);
        //noinspection ResultOfMethodCallIgnored
        matcher.find();
        return parseInt(matcher.group("staffNo"));
    }

    /**
     * Handle log out btn clicked.
     *
     * @param event the event that triggers this handler
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     */
    @FXML
    public void handleLogOutBtnClicked(final MouseEvent event) throws NotLoggedInException {

        // LOG the user out of the system
        getServer().logout();

        // close the dashboard
        ((Node) event.getSource()).getScene()
                                  .getWindow()
                                  .hide();

        // show an info pop-up
        getDisplayer().displayInfo("Logged out.");

        // re-display the login view
        getDisplayer().displayLogInDialog();
    }

    /**
     * Handle access my current review.
     *
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     */
    public void handleAccessMyCurrentReview() throws NotLoggedInException {
        // FIXME is this necessary at all?
        // hide the dashboard
        // ((Node) event.getSource()).getScene().getWindow().hide();
        // display current review
        getDisplayer().displayCurrentReview(getServer().getLoggedIn()
                                                       .getStaffNo());
        // on close, show the dashboard
        // ((Stage) ((Node) event.getSource()).getScene().getWindow()).show();
    }

    /**
     * Handle sign and date review employee.
     *
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws ReviewersNotDiffException when reviewers are not two different people
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     */
    public void handleSignAndDateReviewEmployee() throws NotLoggedInException, StaffNoException, ReviewersNotDiffException, AuthorisationException {
        try {
            getDatabase().signCurrentReviewEmployee(getServer().getLoggedIn()
                                                               .getStaffNo());
            getDisplayer().displayInfo(String.format("Signed and dated review for year %d.", now().getYear()));
            // re-init buttons in the employee tab
            initEmployee();
        } catch (NoSuchRecordException | AlreadySignedException e) {
            LOG.severe(e.getMessage());
            getDisplayer().displayError(e.getMessage());
        }
    }

    /**
     * Handle sign and date review reviewer.
     *
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws AlreadySignedException when the review has already been signed
     */
    public void handleSignAndDateReviewReviewer() throws NoSuchRecordException, NotLoggedInException, StaffNoException, AuthorisationException, AlreadySignedException {
        if (selectedFromDropDown(Dropdown.PENDING_REVIEWS)) {
            getDatabase().signCurrentReviewReviewer(getInitialStaffNo(pendingReviewsChoiceBox.getValue()), getServer().getLoggedIn()
                                                                                                                      .getStaffNo());
            getDisplayer().displayInfo(String.format("Signed and dated review for year %d.", now().getYear()));
            initReviewer();
        } else getDisplayer().displayError("You must choose an employee.");
    }

    /**
     * Handle read past review hr employee.
     *
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    public void handleReadPastReviewHrEmployee() throws NotLoggedInException, AuthorisationException, StaffNoException {

        if (typedInvalidCompletedReviewYear(CompletedReviewYearTextField.HR_EMPLOYEE)) {
            final String errMsg = "Invalid year.";
            getDisplayer().displayError(errMsg);
            return;
        } else if (typedInvalidStaffNo(StaffNoTextBox.READ_PAST_REVIEWS_HR_EMPLOYEE)) {
            final String errMsg = "Invalid staff number.";
            getDisplayer().displayError(errMsg);
            return;
        }

        try {
            final Integer year = parseInt(hrEmployeeReadCompletedReviewYearTextField.getText());
            final Integer empStaffNo = getInitialStaffNo(readPastReviewHrEmployeeStaffNoTextField.getText());

            // don't remove! this needs to be called before displayPastReview
            getDatabase().readCompletedReview(empStaffNo, year);

            getDisplayer().displayPastReview(empStaffNo, year);

        } catch (final NoSuchRecordException e) {
            LOG.severe(e.getMessage());
            getDisplayer().displayError("Review for this year could not be found.");
        }
    }

    /**
     * Handle read past review reviewer.
     *
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    public void handleReadPastReviewReviewer() throws NotLoggedInException, AuthorisationException, StaffNoException {
        if (!selectedFromDropDown(Dropdown.PENDING_REVIEWS)) {
            final String errMsg = "Employee for review not selected.";
            LOG.warning(errMsg);
            getDisplayer().displayError(errMsg);
            return;
        } else if (typedInvalidCompletedReviewYear(CompletedReviewYearTextField.REVIEWER)) {
            final String errMsg = "Invalid year.";
            getDisplayer().displayError(errMsg);
            return;
        }

        try {
            final Integer year = parseInt(reviewerReadCompletedReviewYearTextField.getText());
            final Integer empStaffNo = getInitialStaffNo(pendingReviewsChoiceBox.getValue());

            // don't remove! this needs to be called before displayPastReview
            getDatabase().readCompletedReview(empStaffNo, year);

            getDisplayer().displayPastReview(empStaffNo, year);

        } catch (final NoSuchRecordException e) {
            LOG.severe(e.getMessage());
            getDisplayer().displayError("Review for this year could not be found.");
        }
    }

    /**
     * Handle read past reviews employee.
     *
     * @throws NotLoggedInException when you attempt to perform the action without prior authentication
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    public void handleReadPastReviewEmployee() throws NotLoggedInException, AuthorisationException, StaffNoException {

        if (typedInvalidCompletedReviewYear(CompletedReviewYearTextField.EMPLOYEE)) {
            final String errMsg = "Invalid year.";
            getDisplayer().displayError(errMsg);
            return;
        }

        final String yearText = employeeReadCompletedReviewYearTextField.getText();
        final U loggedIn = getServer().getLoggedIn();
        final int staffNo = loggedIn.getStaffNo();

        // TODO remove logging here once bug fixed

        LOG.info("Trying to handle reading of past review.");

        LOG.info("Parsing year.");
        final int year = parseInt(yearText);

        try {
            // don't remove! this needs to be called before displayPastReview
            getDatabase().readCompletedReview(staffNo, year);

        } catch (final NoSuchRecordException ignored) {
            getDisplayer().displayInfo("No record for this year.");
            return;
        }

        LOG.info(format("Handing displaying of past review for employee {0} for year {1}.", staffNo, year));

        getDisplayer().displayPastReview(staffNo, year);
    }

    private boolean typedInvalidCompletedReviewYear(final CompletedReviewYearTextField chooser) {
        TextField field = null;

        switch (chooser) {

            case EMPLOYEE:
                field = employeeReadCompletedReviewYearTextField;
                break;

            case REVIEWER:
                field = reviewerReadCompletedReviewYearTextField;
                break;

            case HR_EMPLOYEE:
                field = hrEmployeeReadCompletedReviewYearTextField;
                break;

        }

        if ((field == null) || (field.getText() == null) || field.getText()
                                                                 .isEmpty()) {
            LOG.warning("Valid year not selected.");
            return true;
        } else try {
            final int year = parseInt(field.getText());
            final int currentYear = now().getYear();
            if ((year < MIN_YEAR) || (year > currentYear)) {
                LOG.warning(String.format("Selected invalid year: %d.", year));
                return true;
            } else return false;
        } catch (final NumberFormatException ignored) {
            LOG.warning(String.format("Selected invalid year: %s.", field.getText()));
            return true;
        }
    }

    @SuppressWarnings("AlibabaAvoidCommentBehindStatement")
    private enum StaffNoTextBox {
        /**
         * Access personal details staff number text box.
         */
        ACCESS_PERSONAL_DETAILS, /**
         * Create personal details staff number text box.
         */
        CREATE_PERSONAL_DETAILS, READ_PAST_REVIEWS_REVIEWER, READ_PAST_REVIEWS_EMPLOYEE, /**
         * Read past reviews staff number text box.
         */
        READ_PAST_REVIEWS_HR_EMPLOYEE
    }

    @SuppressWarnings("AlibabaAvoidCommentBehindStatement")
    private enum Dropdown {
        /**
         * Reviewers 1 st half dropdown.
         */
        REVIEWERS_1ST_HALF, /**
         * Reviewers 2 nd half dropdown.
         */
        REVIEWERS_2ND_HALF, /**
         * Employee for review dropdown.
         */
        EMPLOYEE_FOR_REVIEW, /**
         * Pending reviews dropdown.
         */
        PENDING_REVIEWS
    }

    @SuppressWarnings("AlibabaAvoidCommentBehindStatement")
    private enum CompletedReviewYearTextField {
        /**
         * Employee year text box.
         */
        EMPLOYEE, /**
         * Reviewer year text box.
         */
        REVIEWER, /**
         * Hr employee year text box.
         */
        HR_EMPLOYEE
    }
}

