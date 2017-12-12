package uk.ac.kent.records;

import java.text.MessageFormat;
import java.util.Optional;
import javax.persistence.Entity;
import uk.ac.kent.records.recommendations.BaseRecommendation;
import uk.ac.kent.records.recommendations.RemainRecommendation;

/**
 * @author norbert
 */

@SuppressWarnings("PublicMethodNotExposedInInterface")
@Entity(name = "AnnualReviewRecord")
public final class AnnualReviewRecord extends BaseRecord {

    private AnnualReviewRecord previousAnnualReview;

    @SuppressWarnings("FieldCanBeLocal")
    private BaseRecommendation recommendation;

    public AnnualReviewRecord(final AnnualReviewRecord previousAnnualReview, final BaseRecommendation recommendation) {
        this.recommendation = recommendation;
        this.previousAnnualReview = previousAnnualReview;
    }

    public AnnualReviewRecord(final BaseRecommendation recommendation) {
        this.recommendation = recommendation;
    }

    public AnnualReviewRecord() {
        recommendation = new RemainRecommendation();
    }

    public void setPreviousAnnualReview(final AnnualReviewRecord previousAnnualReview) {
        this.previousAnnualReview = previousAnnualReview;
    }

    public void setRecommendation(final BaseRecommendation recommendation) {
        this.recommendation = recommendation;
    }

    public Optional<BaseRecommendation> getRecommendation() {
        return Optional.ofNullable(recommendation);
    }

    public Optional<AnnualReviewRecord> getPreviousAnnualReview() {
        return Optional.ofNullable(previousAnnualReview);
    }

    @Override
    public String toString() {
        return MessageFormat
                .format("AnnualReviewRecord<singed={0}>", isSigned());
    }
}
