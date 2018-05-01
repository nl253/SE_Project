package yuconz.records.reviews;

/**
 * The enum Recommendation.
 */
@SuppressWarnings("AlibabaClassMustHaveAuthor")
public enum Recommendation {
    /**
     * Salary increase recommendation.
     */
    SALARY_INCREASE, /**
     * Promotion recommendation.
     */
    PROMOTION, /**
     * Remain in post recommendation.
     */
    REMAIN_IN_POST, /**
     * Probation recommendation.
     */
    PROBATION;

    @Override
    public String toString() {
        //noinspection DynamicRegexReplaceableByCompiledPattern
        return name().toLowerCase()
                     .replace("_", " ");
    }
}
