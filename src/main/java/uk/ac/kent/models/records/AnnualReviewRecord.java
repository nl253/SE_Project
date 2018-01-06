package uk.ac.kent.models.records;

import java.text.MessageFormat;
import java.time.LocalDate;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import uk.ac.kent.models.records.recommendations.BaseRecommendation;

/**
 * Each {@link uk.ac.kent.models.people.Employee} must have an {@link AnnualReviewRecord} created on a yearly basis.
 * They are associated with a {@link java.util.List} of such {@link AnnualReviewRecord}s.
 *
 * @author norbert
 */

@SuppressWarnings({"PublicMethodNotExposedInInterface", "AccessingNonPublicFieldOfAnotherObject"})
@Entity
@Table(name = "annual_reviews")
@Access(AccessType.FIELD)
public final class AnnualReviewRecord extends BaseRecord {

    @SuppressWarnings("FieldCanBeLocal")
    @JoinColumn(name = "recommendation")
    @OneToOne
    private BaseRecommendation baseRecommendation;

    /**
     * @param creationDate date of creation ({@link LocalDate})
     * @param modificationDate date of modification ({@link LocalDate})
     * @param baseRecommendation reference to baseRecommendation associated with this review
     */

    public AnnualReviewRecord(final BaseRecommendation baseRecommendation, final LocalDate modificationDate, final LocalDate creationDate) {
        super(creationDate, modificationDate);
        this.baseRecommendation = baseRecommendation;
    }

    /**
     * @param creationDate date of creation ({@link LocalDate})
     * @param baseRecommendation reference to baseRecommendation associated with this review
     */

    public AnnualReviewRecord(final BaseRecommendation baseRecommendation, final LocalDate creationDate) {
        super(creationDate, creationDate);
        this.baseRecommendation = baseRecommendation;
    }

    /**
     * @param baseRecommendation reference to baseRecommendation associated with this review
     */

    public AnnualReviewRecord(final BaseRecommendation baseRecommendation) {
        this.baseRecommendation = baseRecommendation;
    }

    /**
     * Empty constructor for Hibernate.
     */

    public AnnualReviewRecord() {}

    public void setBaseRecommendation(final BaseRecommendation baseRecommendation) {
        this.baseRecommendation = baseRecommendation;
    }

    public BaseRecommendation getBaseRecommendation() {
        return baseRecommendation;
    }

    @Override
    public String toString() {
        return MessageFormat
                .format("AnnualReviewRecord<singed={0}>", getSigned());
    }
}
