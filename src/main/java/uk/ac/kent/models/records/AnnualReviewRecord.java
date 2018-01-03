package uk.ac.kent.models.records;

import java.text.MessageFormat;
import java.util.Optional;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import uk.ac.kent.models.records.recommendations.BaseRecommendation;
import uk.ac.kent.models.records.recommendations.RemainBaseRecommendation;

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
    @OneToOne(targetEntity = BaseRecommendation.class, optional = false, orphanRemoval = true, cascade = CascadeType.ALL)
    private BaseRecommendation baseRecommendation;

    /**
     * @param baseRecommendation reference to baseRecommendation associated with this review
     */

    public AnnualReviewRecord(final BaseRecommendation baseRecommendation) {
        this.baseRecommendation = baseRecommendation;
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    protected AnnualReviewRecord() {}

    /**
     * @return a fake AnnualReviewRecord
     */

    @Transient
    @SuppressWarnings("LocalVariableOfConcreteClass")
    public static AnnualReviewRecord fake() {
        final AnnualReviewRecord review = new AnnualReviewRecord();
        review.baseRecommendation = new RemainBaseRecommendation();
        return review;
    }

    public void setBaseRecommendation(final BaseRecommendation baseRecommendation) {
        this.baseRecommendation = baseRecommendation;
    }

    public Optional<BaseRecommendation> getBaseRecommendation() {
        return Optional.ofNullable(baseRecommendation);
    }

    @Override
    public String toString() {
        return MessageFormat
                .format("AnnualReviewRecord<singed={0}>", getSigned());
    }
}
