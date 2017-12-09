package uk.ac.kent.records;

import javax.persistence.Entity;

/**
 * @author norbert
 */

@Entity(name = "AnnualReviewRecord")
@SuppressWarnings({"ClassWithoutLogger", "PublicConstructor", "unused", "MethodReturnOfConcreteClass", "MethodParameterOfConcreteClass", "ParameterHidesMemberVariable", "ClassHasNoToStringMethod", "PublicMethodNotExposedInInterface", "InstanceVariableMayNotBeInitialized", "FieldNamingConvention", "InstanceVariableOfConcreteClass"})
public final class AnnualReviewRecord extends BaseRecord {

    private AnnualReviewRecord previousAnnualReview;

    public AnnualReviewRecord getPreviousAnnualReview() {
        return previousAnnualReview;
    }

    public void setPreviousAnnualReview(final AnnualReviewRecord previousAnnualReview) {
        this.previousAnnualReview = previousAnnualReview;
    }
}
