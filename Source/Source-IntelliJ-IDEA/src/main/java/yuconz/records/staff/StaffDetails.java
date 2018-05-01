package yuconz.records.staff;

import java.util.Collection;
import java.util.stream.Collectors;
import yuconz.exceptions.NoSuchRecordException;
import yuconz.exceptions.StaffNoException;
import yuconz.records.ReadableRecords;

import static java.lang.String.format;

/**
 * The interface Staff details.
 *
 * @param <U> the type parameter
 */
@SuppressWarnings({"ClassNamePrefixedWithPackageName", "InterfaceWithOnlyOneDirectInheritor"})
@FunctionalInterface
public interface StaffDetails<U extends User> extends ReadableRecords<U> {

    /**
     * Employees collection.
     *
     * @return the collection
     */
    default Collection<Employee> employees() {
        return get().stream()
                    .filter(Employee.class::isInstance)
                    .map(Employee.class::cast)
                    .collect(Collectors.toList());
    }

    /**
     * Reviewers collection.
     *
     * @return the collection
     */
    default Collection<Reviewer> reviewers() {
        return get().stream()
                    .filter(Reviewer.class::isInstance)
                    .map(Reviewer.class::cast)
                    .collect(Collectors.toList());
    }

    /**
     * Find employee employee.
     *
     * @param staffNo the staff number
     * @return the employee
     *
     * @throws NoSuchRecordException when the record you are trying to find does not exist (may also be thrown during record lookup when authenticating)
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    default Employee findEmployee(final int staffNo) throws NoSuchRecordException, StaffNoException {
        final U employee = read(staffNo);
        if (!(employee instanceof Employee))
            throw new NoSuchRecordException(format("There is no employee with staff number %d.", staffNo));
        return (Employee) employee;
    }
}
