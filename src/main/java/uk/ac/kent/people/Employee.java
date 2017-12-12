package uk.ac.kent.people;

import java.text.MessageFormat;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import uk.ac.kent.records.AnnualReviewRecord;
import uk.ac.kent.records.EmploymentDetailsRecord;
import uk.ac.kent.records.PersonalDetailsRecord;
import uk.ac.kent.records.ProbationRecord;
import uk.ac.kent.records.SalaryIncreaseRecord;
import uk.ac.kent.records.TerminationRecord;

/**
 * Employee class represents all employess working for the company.
 * This includes Directors and Managers which are subclasses of Employee.
 * <p>
 * To avoid data duplication, the employee doesn't store her personal details.
 * Instead, they are stored in PersonalDetailsRecord which is one of the
 * documents that the system needs to handle.
 * <p>
 * Therefore, modifying an employees details requires you to modify their
 * PersonalDetailsRecord. The same applies to EmploymentDetailsRecord.
 * <p>
 * In some cases someone might not have a certain type of record (eg termination).
 * This is why getters return Optionals rather than the Object itself to
 * prevent returning null which isn't safe.
 *
 * @author norbert
 */

@SuppressWarnings({"PublicMethodNotExposedInInterface", "NonBooleanMethodNameMayNotStartWithQuestion", "MethodParameterNamingConvention"})
@Entity
@Table(name = "employees")
public class Employee {

    private static int nextId;

    @Id
    private final int id;
    private final PersonalDetailsRecord personalDetailsRecord;
    private final EmploymentDetailsRecord employmentDetailsRecord;
    private ProbationRecord probationRecord;
    private SalaryIncreaseRecord salaryIncreaseRecord;
    private AnnualReviewRecord annualReviewRecord;
    private TerminationRecord terminationRecord;

    @SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod")
    Employee(final PersonalDetailsRecord personalDetailsRecord, final EmploymentDetailsRecord employmentDetailsRecord) {
        id = nextId;
        nextId++;
        this.personalDetailsRecord = personalDetailsRecord;
        this.employmentDetailsRecord = employmentDetailsRecord;
    }

    public Employee() {
        this(new PersonalDetailsRecord(), new EmploymentDetailsRecord());
    }

    final int getId() { return id; }

    public final EmploymentDetailsRecord getEmploymentDetailsRecord() {
        return employmentDetailsRecord;
    }

    public final PersonalDetailsRecord getPersonalDetailsRecord() {
        return personalDetailsRecord;
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
