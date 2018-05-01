package yuconz.records.staff;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;
import yuconz.exceptions.DateException;
import yuconz.exceptions.StaffNoException;
import yuconz.records.MockDataGenerator;
import yuconz.records.Records;

import static java.lang.System.exit;
import static yuconz.records.staff.Section.*;

/**
 * Mock staff details.
 *
 * @param <U> the type parameter
 * @param <A> the type parameter
 */
public final class MockStaffDetails<U extends User, A> extends MockDataGenerator<A> implements StaffDetails<U> {

    private static Records<? extends User> self;
    private final Collection<U> staffDetails;

    @SuppressWarnings("unchecked")
    private MockStaffDetails() {

        staffDetails = new LinkedList<>();

        try {
            final U sde = (U) new Employee(1, SERVICE_DELIVERY, nYearsAgo(4));
            final U sdm = (U) new Manager(2, SERVICE_DELIVERY, nYearsAgo(12));
            final U sdd = (U) new Director(3, SERVICE_DELIVERY, nYearsAgo(6));

            final U hre = (U) new Employee(4, HUMAN_RESOURCES, nYearsAgo(1));
            final U hrm = (U) new Manager(5, HUMAN_RESOURCES, nYearsAgo(4));
            final U hrd = (U) new Director(6, HUMAN_RESOURCES, nYearsAgo(8));

            final U ade = (U) new Employee(7, ADMINISTRATION, nYearsAgo(3));
            final U adm = (U) new Manager(8, ADMINISTRATION, nYearsAgo(9));
            final U add = (U) new Director(9, ADMINISTRATION, nYearsAgo(6));

            final U sme = (U) new Employee(10, SALES_AND_MARKETING, nYearsAgo(2));
            final U smm = (U) new Manager(11, SALES_AND_MARKETING, nYearsAgo(5));
            final U smd = (U) new Director(12, SALES_AND_MARKETING, nYearsAgo(3));
            Stream.of(sde, sdm, sdd, hre, hrm, hrd, ade, adm, add, sme, smm, smd)
                  .forEach(staffDetails::add);
        } catch (final StaffNoException | DateException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    /**
     * Gets instance.
     *
     * @param <U> the type parameter
     * @return the instance
     */
    @SuppressWarnings({"NonThreadSafeLazyInitialization", "StaticVariableUsedBeforeInitialization"})
    public static <U extends User> StaffDetails<U> getInstance() {
        if (self == null) self = new MockStaffDetails<>();
        return (StaffDetails<U>) self;
    }

    @SuppressWarnings("ImplicitNumericConversion")
    private static LocalDate nYearsAgo(final int n) {
        return LocalDate.now()
                        .minusYears(n);
    }

    @SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
    @Override
    public Collection<U> get() {
        return staffDetails;
    }


    @SuppressWarnings("ForLoopThatDoesntUseLoopVariable")
    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder(50);
        final Iterator<U> iterator = staffDetails.iterator();
        builder.append(getClass().getSimpleName());
        builder.append('<');

        if (!staffDetails.isEmpty()) {
            int i = 0;
            final int noRecsToDisplay = 3;
            for (U a = iterator.next(); (i < noRecsToDisplay) && iterator.hasNext(); a = iterator.next()) {
                builder.append(a)
                       .append(", ");
                i++;
            }
            builder.append(" ... ");
        }
        builder.append('>');
        return builder.toString();
    }
}
