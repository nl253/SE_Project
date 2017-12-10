package uk.ac.kent.records;

import java.text.MessageFormat;
import javax.persistence.Entity;

/**
 * @author norbert
 */

@SuppressWarnings("PublicMethodNotExposedInInterface")
@Entity(name = "AnnualReviewRecord")
final class AnnualReviewRecord extends BaseRecord {

    private AnnualReviewRecord previousAnnualReview;

    public AnnualReviewRecord getPreviousAnnualReview() {
        return previousAnnualReview;
    }

    public void setPreviousAnnualReview(final AnnualReviewRecord previousAnnualReview) {
        this.previousAnnualReview = previousAnnualReview;
    }

    @Override
    public String toString() {
        return MessageFormat
                .format("AnnualReviewRecord<{0}>", getEmployee().toString());
    }
}
