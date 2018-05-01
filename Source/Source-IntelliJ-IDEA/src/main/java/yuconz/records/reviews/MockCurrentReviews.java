package yuconz.records.reviews;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Mock current reviews.
 */
public final class MockCurrentReviews implements CurrentReviews {

    private static CurrentReviews self;
    private final Collection<ReviewBuilder> reviews;

    private MockCurrentReviews() {
        reviews = new LinkedList<>();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CurrentReviews getInstance() {
        if (self == null) self = new MockCurrentReviews();
        return self;
    }

    @Override
    public final Collection<ReviewBuilder> get() {
        return reviews;
    }

    @SuppressWarnings("ForLoopThatDoesntUseLoopVariable")
    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder(50);
        final Iterator<ReviewBuilder> iterator = reviews.iterator();
        builder.append(getClass().getSimpleName());
        builder.append('<');

        if (!reviews.isEmpty()) {
            int i = 0;
            //noinspection ForLoopThatDoesntUseLoopVariable,AlibabaUndefineMagicConstant
            final int noRecsToDisplay = 3;
            for (ReviewBuilder a = iterator.next(); (i < noRecsToDisplay) && iterator.hasNext(); a = iterator.next()) {
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
