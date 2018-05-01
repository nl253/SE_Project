package yuconz.records.reviews;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.stream.Collectors;
import yuconz.exceptions.NoSuchRecordException;
import yuconz.records.CreatableRecords;

import static com.sun.javafx.binding.Logging.getLogger;
import static java.time.LocalDate.now;

/**
 * The interface Completed reviews.
 */
@FunctionalInterface
public interface CompletedReviews extends CreatableRecords<CompletedReview> {

    /**
     * Read collection.
     *
     * @param empStaffNo employee staff number
     * @return the collection
     *
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     */
    default Collection<CompletedReview> read(final int empStaffNo) throws NoSuchRecordException {
        if (hasRecord(empStaffNo)) return get().stream()
                                               .filter(rev -> rev.employeeStaffNo == empStaffNo)
                                               .collect(Collectors.toList());
        else
            throw new NoSuchRecordException("This employee doesn't have any reviews.");
    }

    /**
     * Read current review.
     *
     * @param empStaffNo employee staff number
     * @param year the year
     * @return the current review
     *
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     */
    default CompletedReview read(final int empStaffNo, final int year) throws NoSuchRecordException {
        // TODO remove logging once the bug is fixed
        if (hasRecord(empStaffNo, year)) {
            final CompletedReview result = get().stream()
                                                .filter(rev -> rev.employeeStaffNo == empStaffNo)
                                                .findFirst()
                                                .orElseThrow(() -> new NoSuchRecordException(String.format("No review for %d for year %d.", empStaffNo, year)));
            getLogger().info(MessageFormat.format("Found {0}.", result.toString()));
            return result;
        } else
            throw new NoSuchRecordException(String.format("No review for employee with staff number: %d for year: %d.", empStaffNo, year));
    }

    default boolean hasRecord(final int empStaffNo, final int year) {
        return get().stream()
                    .anyMatch(x -> (x.employeeStaffNo == empStaffNo) && (x.getYearDone() == year));
    }

    /**
     * Reviewed this year.
     *
     * @param empStaffNo employee staff number
     * @return true if and only if the conditions aren't violated
     */
    default boolean reviewedThisYear(final int empStaffNo) {
        return get().stream()
                    .filter(x -> x.employeeStaffNo == empStaffNo)
                    .map(CompletedReview::getYearDone)
                    .anyMatch(x -> x == now().getYear());
    }
}
