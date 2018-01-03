package uk.ac.kent.models.records;

import java.text.MessageFormat;
import java.util.Optional;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import uk.ac.kent.models.records.recommendations.BaseRecommendation;
import uk.ac.kent.models.records.recommendations.RemainBaseRecommendation;

/**
 * Each {@link uk.ac.kent.models.people.Employee} must have an {@link AnnualReviewRecord} created on a yearly basis.
 * A part of it is producing a {@link BaseRecommendation}.
 *
 * @author norbert
 */

@SuppressWarnings({"PublicMethodNotExposedInInterface", "AccessingNonPublicFieldOfAnotherObject"})
@Entity
@Table(name = "annual_reviews")
@Access(AccessType.FIELD)
public final class AnnualReviewRecord extends BaseRecord {

    @OneToOne(targetEntity = AnnualReviewRecord.class)
    @JoinColumn(name = "previous_review")
    private AnnualReviewRecord previousReview;

    @SuppressWarnings("FieldCanBeLocal")
    @OneToOne(targetEntity = BaseRecommendation.class)
    private BaseRecommendation baseRecommendation;

    /**
     * @param previousAnnualReview reference to last year's annual review
     * @param baseRecommendation reference to baseRecommendation associated with this review
     */

    public AnnualReviewRecord(final AnnualReviewRecord previousAnnualReview, final BaseRecommendation baseRecommendation) {
        this.baseRecommendation = baseRecommendation;
        previousReview = previousAnnualReview;
    }

    /**
     * Here we do not pass the previous {@link AnnualReviewRecord} as it is the very firs one.
     *
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

    public void setPreviousReview(final AnnualReviewRecord previousAnnualReview) {
        previousReview = previousAnnualReview;
    }

    public void setBaseRecommendation(final BaseRecommendation baseRecommendation) {
        this.baseRecommendation = baseRecommendation;
    }

    public Optional<BaseRecommendation> getBaseRecommendation() {
        return Optional.ofNullable(baseRecommendation);
    }

    public Optional<AnnualReviewRecord> getPreviousReview() {
        return Optional.ofNullable(previousReview);
    }

    @Override
    public String toString() {
        return MessageFormat
                .format("AnnualReviewRecord<singed={0}>", getSigned());
    }
}
