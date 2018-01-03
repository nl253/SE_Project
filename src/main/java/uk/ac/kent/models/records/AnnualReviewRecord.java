package uk.ac.kent.models.records;

import java.text.MessageFormat;
import java.util.Optional;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import uk.ac.kent.models.records.recommendations.Recommendation;
import uk.ac.kent.models.records.recommendations.RemainRecommendation;

/**
 * @author norbert
 */

@SuppressWarnings({"PublicMethodNotExposedInInterface", "AccessingNonPublicFieldOfAnotherObject"})
@Entity
@Table(name = "annual_reviews")
@Access(AccessType.FIELD)
public final class AnnualReviewRecord extends BaseRecord {

    @OneToOne(targetEntity = AnnualReviewRecord.class)
    // @Column(name = "previous_review")
    private AnnualReviewRecord previousReview;

    @SuppressWarnings("FieldCanBeLocal")
    @OneToOne(targetEntity = Recommendation.class)
    private Recommendation recommendation;

    public AnnualReviewRecord(final AnnualReviewRecord previousAnnualReview, final Recommendation recommendation) {
        this.recommendation = recommendation;
        previousReview = previousAnnualReview;
    }

    public AnnualReviewRecord(final Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    protected AnnualReviewRecord() {}

    /**
     * Produces a fake AnnualReviewRecord. For testing.
     *
     * @return a fake AnnualReviewRecord
     */

    @Transient
    @SuppressWarnings("LocalVariableOfConcreteClass")
    public static AnnualReviewRecord fake() {
        final AnnualReviewRecord review = new AnnualReviewRecord();
        review.recommendation = new RemainRecommendation();
        return review;
    }

    public void setPreviousReview(final AnnualReviewRecord previousAnnualReview) {
        previousReview = previousAnnualReview;
    }

    public void setRecommendation(final Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    public Optional<Recommendation> getRecommendation() {
        return Optional.ofNullable(recommendation);
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
