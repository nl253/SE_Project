package yuconz.records;

import java.util.Collection;

/**
 * The interface Records.
 *
 * @param <R> the type parameter
 */
@FunctionalInterface
@SuppressWarnings({"NewClassNamingConvention", "ClassNamePrefixedWithPackageName"})
public interface Records<R extends Record> {

    /**
     * Get collection.
     *
     * @return the collection
     */
    Collection<R> get();

    /**
     * Has record.
     *
     * @param staffNo the staff number
     * @return true if and only if the conditions aren't violated
     */
    default boolean hasRecord(final int staffNo) {
        for (final R record : get())
            if (record.getStaffNo() == staffNo) return true;
        return false;
    }
}
