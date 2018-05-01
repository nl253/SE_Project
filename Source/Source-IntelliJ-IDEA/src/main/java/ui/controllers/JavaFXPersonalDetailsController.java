package ui.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import yuconz.exceptions.AuthorisationException;
import yuconz.exceptions.DateException;
import yuconz.exceptions.NoSuchRecordException;
import yuconz.exceptions.NotLoggedInException;
import yuconz.exceptions.NullValueException;
import yuconz.exceptions.StaffNoException;
import yuconz.exceptions.StringFormatException;
import yuconz.records.personal.PersonalDetailRecord;
import yuconz.records.personal.ValidatingPersonalDetailsBuilder;
import yuconz.records.staff.User;

import static java.lang.Integer.parseInt;
import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;
import static yuconz.DateValidator.isValidDOB;
import static yuconz.StringValidator.*;

/**
 * JavaFX personal details controller. Handles events in the personal details view.
 *
 * @param <U> the type parameter
 */
@SuppressWarnings({"ClassHasNoToStringMethod", "PublicMethodNotExposedInInterface", "ClassWithTooManyFields", "AlibabaClassNamingShouldBeCamel", "ClassWithTooManyTransitiveDependencies"})
public final class JavaFXPersonalDetailsController<U extends User> extends BaseController<U> implements Initializable {

    private static final String BAD_FMT_COLOUR = "#eea584";
    private static final String GOOD_FMT_COLOUR = "white";

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTxtField;
    @FXML
    private DatePicker dateOfBirthPicker;
    @FXML
    private TextField mobileNoTxtField;
    @FXML
    private TextField telNoTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField countyTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField postcodeTxtField;
    @FXML
    private TextField emergencyContactTextField;
    @FXML
    private TextField emergencyContactNoTextField;

    private String safeExtractStr(final TextInputControl field) {
        return Optional.ofNullable(field)
                       .map(TextInputControl::getText)
                       .orElse("");
    }

    /**
     * Handle amend personal details.
     *
     * @param event the event that triggers this handler that triggers this handler
     * @throws AuthorisationException when you lack the necessary privileges to perform the action (may also be thrown when you are logged in with lowered privileges eg user)
     */
    @SuppressWarnings({"FeatureEnvy", "LawOfDemeter", "ConstantConditions"})
    public void handleAmendPersonalDetails(final MouseEvent event) throws AuthorisationException {

        // noinspection ProhibitedExceptionCaught
        try {

            final int staffNo = parseInt(getProperty("reqPerDetStaffNo", String.valueOf(getServer().getLoggedIn()
                                                                                                   .getStaffNo())));

            // @formatter:off

            getDatabase().amendPersonalDetails(staffNo, new ValidatingPersonalDetailsBuilder(staffNo)
                    .setFirstName(safeExtractStr(firstNameTextField))
                    .setLastName(safeExtractStr(lastNameTxtField))
                    .setDateOfBirth(Optional.ofNullable(dateOfBirthPicker).map(ComboBoxBase::getValue).orElse(null))
                    .setAddress(safeExtractStr(addressTextField))
                    .setCity(safeExtractStr(cityTextField))
                    .setCounty(safeExtractStr(countyTextField))
                    .setPostcode(safeExtractStr(postcodeTxtField))
                    .setMobileNo(safeExtractStr(mobileNoTxtField))
                    .setTelNo(safeExtractStr(telNoTextField))
                    .setEmergencyContact(safeExtractStr(emergencyContactTextField))
                    .setEmergencyContactNo(safeExtractStr(emergencyContactNoTextField)).create());

            // @formatter:on
            LOG.info(format("After modification: {0}", getDatabase().readPersonalDetails(staffNo)));

            getDisplayer().displayInfo("Information saved.");
            ((Node) event.getSource()).getScene()
                                      .getWindow()
                                      .hide();
        } catch (final NoSuchRecordException ignored) {
            getDisplayer().displayError("Error while saving.");
        } catch (final NullPointerException | NullValueException ignored) {
            getDisplayer().displayError("Fill in all fields.");
        } catch (final NumberFormatException ignored) {
            getDisplayer().displayError("Bad number format. Check phone.");
        } catch (final NotLoggedInException e) {
            getDisplayer().displayError(e.getMessage());
            ((Node) event.getSource()).getScene()
                                      .getWindow()
                                      .hide();
            getDisplayer().displayLogInDialog();
        } catch (final DateException | StringFormatException | StaffNoException e) {
            getDisplayer().displayError(e.getMessage());
        }
    }

    /**
     * Handle close.
     *
     * @param event the event that triggers this handler that triggers this handler
     */
    @SuppressWarnings("MethodMayBeStatic")
    public void handleClose(final MouseEvent event) {
        ((Node) event.getSource()).getScene()
                                  .getWindow()
                                  .hide();
    }

    @SuppressWarnings("CallToSystemExit")
    @Override
    public void initialize(final URL url, final ResourceBundle bundle) {
        // @formatter:off
        try {

            final int staffNo = parseInt(getProperty("reqPerDetStaffNo", String.valueOf(getServer().getLoggedIn().getStaffNo())));

            final PersonalDetailRecord record = getDatabase().readPersonalDetails(staffNo);

            LOG.info(format("Initialising {0}.", record.toString()));

            firstNameTextField.setText(Optional.ofNullable(record.firstName).orElse(""));
            lastNameTxtField.setText(Optional.ofNullable(record.lastName).orElse(""));
            addressTextField.setText(Optional.ofNullable(record.address).orElse(""));
            countyTextField.setText(Optional.ofNullable(record.county).orElse(""));
            postcodeTxtField.setText(Optional.of(record.postcode).orElse(""));
            cityTextField.setText(Optional.ofNullable(record.city).orElse(""));
            dateOfBirthPicker.setValue(Optional.of(record.dob).orElse(LocalDate.now().minusYears(30)));
            mobileNoTxtField.setText(Optional.ofNullable(record.mobileNo).orElse(""));
            telNoTextField.setText(Optional.ofNullable(record.telNo).orElse(""));
            emergencyContactTextField.setText(Optional.ofNullable(record.emergencyContact).orElse(""));
            emergencyContactNoTextField.setText(Optional.ofNullable(record.emergencyContactNo).orElse(""));

        } catch (final NoSuchRecordException e) {
            getDisplayer().displayError(e.getMessage());
        } catch (final NotLoggedInException e) {
            e.printStackTrace();
            getDisplayer().displayError(e.getMessage());
            getDisplayer().displayLogInDialog();
        } catch (final AuthorisationException e) {
            LOG.severe(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (final StaffNoException e) {
            getDisplayer().displayError(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        // @formatter:on
    }

    /**
     * Handle validate and notify postcode.
     *
     * @param event the event that triggers this handler that triggers this handler
     */
    public void handleValidateAndNotifyPostcode(final KeyEvent event) {
        colorizeWhenFalse(((TextField) event.getSource()), field -> isValidPostcode(field.getText()));
    }

    /**
     * Handle validate and notify date of birth.
     *
     * @param event the event that triggers this handler that triggers this handler
     */
    public void handleValidateAndNotifyDateOfBirth(final Event event) {
        colorizeWhenFalse(((DatePicker) event.getSource()), picker -> (picker != null) && (picker.getValue() != null) && isValidDOB(picker.getValue()));
    }

    /**
     * Handle validate and notify phone number.
     *
     * @param event the event that triggers this handler that triggers this handler
     */
    public void handleValidateAndNotifyPhoneNo(final KeyEvent event) {
        colorizeWhenFalse(((TextField) event.getSource()), field -> isValidPhoneNo(field.getText()));
    }

    private void colorizeWhenFalse(final TextField textField, final Predicate<TextField> predicate) {
        textField.setStyle("-fx-control-inner-background: " + (predicate.test(textField) ? GOOD_FMT_COLOUR : BAD_FMT_COLOUR) + ';');
    }

    private void colorizeWhenFalse(final DatePicker datePicker, final Predicate<DatePicker> predicate) {
        datePicker.setStyle("-fx-control-inner-background: " + (predicate.test(datePicker) ? GOOD_FMT_COLOUR : BAD_FMT_COLOUR) + ';');
    }

    /**
     * Handle validate and notify name.
     *
     * @param event the event that triggers this handler that triggers this handler
     */
    public void handleValidateAndNotifyName(final KeyEvent event) {
        colorizeWhenFalse(((TextField) event.getSource()), field -> isValidName(field.getText()));
    }

    /**
     * Handle validate and notify cap word.
     *
     * @param event the event that triggers this handler that triggers this handler
     */
    @SuppressWarnings("MethodMayBeStatic")
    public void handleValidateAndNotifyCapWord(final KeyEvent event) {
        colorizeWhenFalse(((TextField) event.getSource()), field -> allWordsCap(field.getText()));
    }
}
