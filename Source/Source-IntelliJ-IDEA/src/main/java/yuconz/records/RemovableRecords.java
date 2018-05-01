package yuconz.records;

import yuconz.exceptions.NoSuchRecordException;

/**
 * The interface Removable records.
 *
 * @param <R> the type parameter
 */
@FunctionalInterface
public interface RemovableRecords<R extends Record> extends Records<R> {

    /**
     * Remove.
     *
     * @param staffNo the staff number
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     */
    default void remove(final int staffNo) throws NoSuchRecordException {
        // check that the record exists
        if (!get().removeIf(x -> x.getStaffNo() == staffNo))
            throw new NoSuchRecordException("No record for staff number " + staffNo + '.');
    }
}
