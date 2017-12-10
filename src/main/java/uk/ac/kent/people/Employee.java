package uk.ac.kent.people;

import java.text.MessageFormat;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import uk.ac.kent.records.AnnualReviewRecord;
import uk.ac.kent.records.PersonalDetailsRecord;
import uk.ac.kent.records.ProbationRecord;
import uk.ac.kent.records.SalaryIncreaseRecord;
import uk.ac.kent.records.TerminationRecord;

/**
 * @author norbert
 */

@SuppressWarnings({"PublicMethodNotExposedInInterface", "NonBooleanMethodNameMayNotStartWithQuestion", "MethodParameterNamingConvention"})
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    private int id;
    private PersonalDetailsRecord personalDetailsRecord;
    private ProbationRecord probationRecord;
    private SalaryIncreaseRecord salaryIncreaseRecord;
    private AnnualReviewRecord annualReviewRecord;
    private TerminationRecord terminationRecord;

    final int getId() { return id; }

    public final PersonalDetailsRecord getPersonalDetailsRecord() {
        return personalDetailsRecord;
    }

    public final void setPersonalDetailsRecord(final PersonalDetailsRecord personalDetailsRecord) {
        this.personalDetailsRecord = personalDetailsRecord;
    }

    public final Optional<ProbationRecord> getProbationRecord() {
        return Optional.ofNullable(probationRecord);
    }

    public final void setProbationRecord(final ProbationRecord probationRecord) {
        this.probationRecord = probationRecord;
    }

    public final Optional<SalaryIncreaseRecord> getSalaryIncreaseRecord() {
        return Optional.ofNullable(salaryIncreaseRecord);
    }

    public final void setSalaryIncreaseRecord(final SalaryIncreaseRecord salaryIncreaseRecord) {
        this.salaryIncreaseRecord = salaryIncreaseRecord;
    }

    public final Optional<TerminationRecord> getTerminationRecord() {
        return Optional.ofNullable(terminationRecord);
    }

    public final void setTerminationRecord(final TerminationRecord terminationRecord) {
        this.terminationRecord = terminationRecord;
    }

    public final Optional<AnnualReviewRecord> getAnnualReviewRecord() {
        return Optional.ofNullable(annualReviewRecord);
    }

    public final void setAnnualReviewRecord(final AnnualReviewRecord annualReviewRecord) {
        this.annualReviewRecord = annualReviewRecord;
    }


    @SuppressWarnings("DesignForExtension")
    @Override
    public final String toString() {
        return MessageFormat.format("{0}<{1}: {2}>", getClass().getName(), id);
    }

}
