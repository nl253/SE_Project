package uk.ac.kent.records;

import java.text.MessageFormat;
import javax.persistence.Entity;
import uk.ac.kent.recommendations.BaseRecommendation;

/**
 * @author norbert
 */

@SuppressWarnings("PublicMethodNotExposedInInterface")
@Entity(name = "AnnualReviewRecord")
public final class AnnualReviewRecord extends BaseRecord {

    private AnnualReviewRecord previousAnnualReview;

    @SuppressWarnings("FieldCanBeLocal")
    private final BaseRecommendation recommendation;

    public AnnualReviewRecord(final BaseRecommendation recommendation) {
        this.recommendation = recommendation;
    }

    public AnnualReviewRecord getPreviousAnnualReview() {
        return previousAnnualReview;
    }

    @Override
    public String toString() {
        return MessageFormat
                .format("AnnualReviewRecord<singed={0}>", isSigned());
    }
}
