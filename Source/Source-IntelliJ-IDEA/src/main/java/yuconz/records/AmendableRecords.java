package yuconz.records;

import yuconz.exceptions.NoSuchRecordException;

import static java.text.MessageFormat.format;

/**
 * The interface Amendable records.
 *
 * @param <R> the type parameter
 */
@FunctionalInterface
public interface AmendableRecords<R extends Record> extends Records<R> {

    /**
     * Amend.
     *
     * @param staffNo the staff number
     * @param amendedRecord the amended record
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     */
    default void amend(final int staffNo, final R amendedRecord) throws NoSuchRecordException {

        // always check that there is something to amend
        if (!hasRecord(staffNo))
            throw new NoSuchRecordException(format("Record for staff number {0} does not exist.", staffNo));

            // always check that there is something to amend
        else if (!get().removeIf(x -> x.getStaffNo() == staffNo))
            throw new NoSuchRecordException();

        get().add(amendedRecord);
    }
}
