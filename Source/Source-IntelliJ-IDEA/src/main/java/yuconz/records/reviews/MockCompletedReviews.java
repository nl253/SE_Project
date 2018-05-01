package yuconz.records.reviews;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import yuconz.exceptions.DateException;
import yuconz.records.MockDataGenerator;

import static java.lang.System.exit;

/**
 * Mock completed reviews.
 *
 * @param <A> the type parameter
 */
@SuppressWarnings({"Singleton", "StaticVariableOfConcreteClass"})
public final class MockCompletedReviews<A> extends MockDataGenerator<A> implements CompletedReviews {

    private static MockCompletedReviews<?> self;
    private final Collection<CompletedReview> completedReviews;

    @SuppressWarnings({"ObjectAllocationInLoop", "MethodWithMultipleLoops"})
    private MockCompletedReviews() {

        completedReviews = new LinkedList<>();

        // the array corresponds to data in mock staff details
        // years employed for all reviewees (ie employees)
        final int[] yearsEmployed = {4, 12, 1, 4, 3, 9, 2, 5};

        assert yearsEmployed.length == EMPLOYEES.length;

        for (int empNo = 0; empNo < EMPLOYEES.length; empNo++)
            // no review on employment, start from the first year
            for (int yearOfEmp = 1; yearOfEmp < yearsEmployed[empNo]; yearOfEmp++) {
                final CompletedReview completedReview = new CompletedReview(EMPLOYEES[empNo], randFullName(), randJob(), randReviewerStaffNo(), randFullName(), randReviewerStaffNo(), randFullName(), randomParagraph(), randomParagraph(), randomParagraph(), randomParagraph(), randRecommendation());
                try {
                    completedReview.setYearDone((LocalDate.now()
                                                          .getYear() - yearsEmployed[empNo]) + yearOfEmp);
                } catch (final DateException e) {
                    e.printStackTrace();
                    exit(1);
                }
                completedReviews.add(completedReview);
            }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    @SuppressWarnings({"StaticVariableUsedBeforeInitialization", "NonThreadSafeLazyInitialization", "unchecked"})
    public static CompletedReviews getInstance() {
        if (self == null) self = new MockCompletedReviews();
        return self;
    }

    @SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
    @Override
    public final Collection<CompletedReview> get() {
        return completedReviews;
    }

    @SuppressWarnings({"AlibabaUndefineMagicConstant", "ForLoopThatDoesntUseLoopVariable"})
    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder(50);
        final Iterator<CompletedReview> iterator = completedReviews.iterator();
        builder.append(getClass().getSimpleName());
        builder.append('<');

        if (!completedReviews.isEmpty()) {
            int i = 0;
            //noinspection AlibabaUndefineMagicConstant,ForLoopThatDoesntUseLoopVariable
            final int noRecsToDisplay = 3;
            for (CompletedReview a = iterator.next(); (i < noRecsToDisplay) && iterator.hasNext(); a = iterator.next()) {
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
