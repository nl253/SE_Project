package ui.view;

/**
 * The interface View displayer.
 */
@SuppressWarnings("ClassNamePrefixedWithPackageName")
public interface ViewDisplayer {

    /**
     * Display info.
     *
     * @param msg the message
     */
    void displayInfo(final String msg);

    /**
     * Display error.
     *
     * @param msg the message
     */
    void displayError(final String msg);

    /**
     * Display log in dialog.
     */
    void displayLogInDialog();

    /**
     * Display dashboard.
     */
    void displayDashboard();

    /**
     * Display personal details.
     *
     * @param staffNo the staff number
     */
    void displayPersonalDetails(int staffNo);

    /**
     * Display current review.
     *
     * @param staffNo the staff number
     */
    void displayCurrentReview(final int staffNo);

    /**
     * Display past review.
     *
     * @param staffNo the staff number
     * @param year the year
     */
    void displayPastReview(final int staffNo, final int year);
}
