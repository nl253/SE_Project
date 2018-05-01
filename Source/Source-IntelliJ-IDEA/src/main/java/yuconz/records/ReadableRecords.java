package yuconz.records;

import yuconz.exceptions.NoSuchRecordException;
import yuconz.exceptions.StaffNoException;

import static java.text.MessageFormat.format;

/**
 * The interface Readable records.
 *
 * @param <R> the type parameter
 */
@FunctionalInterface
public interface ReadableRecords<R extends Record> extends Records<R> {

    /**
     * Read record.
     *
     * @param staffNo the staff number
     * @return the r
     *
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    default R read(final int staffNo) throws NoSuchRecordException, StaffNoException {
        // validate staff number
        if (staffNo < 0) throw new StaffNoException();
            // check that the record exists
        else if (!hasRecord(staffNo))
            throw new NoSuchRecordException(format("No record for staff number {0}.", staffNo));
        else return get().stream()
                         .filter(x -> x.getStaffNo() == staffNo)
                         .findFirst()
                         .orElseThrow(NoSuchRecordException::new);
    }
}
