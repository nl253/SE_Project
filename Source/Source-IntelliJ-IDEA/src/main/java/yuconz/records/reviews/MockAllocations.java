package yuconz.records.reviews;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import yuconz.exceptions.ReviewersNotDiffException;
import yuconz.exceptions.StaffNoException;

import static java.lang.System.exit;

/**
 * Mock allocations.
 */
public final class MockAllocations implements Allocations {

    private static Allocations self;
    private final Collection<Allocation> allocations;

    @SuppressWarnings("MagicNumber")
    private MockAllocations() {
        // for why those number see MockStaffDetails
        allocations = new LinkedList<>();

        try {
            // NOT ASSIGNED ON PURPOSE
            // see README.md
            //------------------------
            // allocReviewers(sde, hrm, add);
            allocations.add(new Allocation(2, 8, 9));
            // allocReviewers(hre, sdd, sdm);
            allocations.add(new Allocation(5, 2, 3));
            allocations.add(new Allocation(7, 12, 9));
            // allocReviewers(adm, smm, smd);
            // allocReviewers(sme, hrm, smd);
            allocations.add(new Allocation(11, 12, 9));
        } catch (final StaffNoException | ReviewersNotDiffException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    @SuppressWarnings("NonThreadSafeLazyInitialization")
    public static Allocations getInstance() {
        if (self == null) self = new MockAllocations();
        return self;
    }

    @SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
    @Override
    public final Collection<Allocation> get() {
        return allocations;
    }

    @SuppressWarnings("ForLoopThatDoesntUseLoopVariable")
    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder(50);
        final Iterator<Allocation> iterator = allocations.iterator();
        builder.append(getClass().getSimpleName());
        builder.append('<');

        if (!allocations.isEmpty()) {
            int i = 0;
            //noinspection ForLoopThatDoesntUseLoopVariable,ForLoopThatDoesntUseLoopVariable
            final int noRecsToDisplay = 3;
            for (Allocation a = iterator.next(); (i < noRecsToDisplay) && iterator.hasNext(); a = iterator.next()) {
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
