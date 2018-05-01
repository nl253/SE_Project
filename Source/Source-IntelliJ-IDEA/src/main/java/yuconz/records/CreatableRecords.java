package yuconz.records;

import yuconz.exceptions.RecordExistsException;

import static java.text.MessageFormat.format;

/**
 * The interface Creatable Records.
 *
 * @param <R> the type parameter
 */
@FunctionalInterface
public interface CreatableRecords<R extends Record> extends Records<R> {

    /**
     * Create record.
     *
     * @param newRec the new record
     * @throws RecordExistsException when the record already exists
     */
    default void create(final R newRec) throws RecordExistsException {
        // always check that the record does not already exist
        if (hasRecord(newRec.getStaffNo()))
            throw new RecordExistsException(format("Record for staff number {0} already exists.", newRec.getStaffNo()));
        else get().add(newRec);
    }
}
