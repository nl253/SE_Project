package yuconz.records.staff;

/**
 * The enum Section.
 */
@SuppressWarnings("unused")
public enum Section {
    /**
     * Service delivery section.
     */
    SERVICE_DELIVERY, /**
     * Administration section.
     */
    ADMINISTRATION, /**
     * Sales and marketing section.
     */
    SALES_AND_MARKETING, /**
     * Human resources section.
     */
    HUMAN_RESOURCES;

    @Override
    public String toString() {
        //noinspection DynamicRegexReplaceableByCompiledPattern
        return name().replace("_", " ")
                     .toLowerCase();
    }
}
