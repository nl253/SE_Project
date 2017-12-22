package uk.ac.kent.models.people;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.logging.Logger;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import uk.ac.kent.models.records.AnnualReviewRecord;
import uk.ac.kent.models.records.EmploymentDetailsRecord;
import uk.ac.kent.models.records.PersonalDetailsRecord;
import uk.ac.kent.models.records.ProbationRecord;
import uk.ac.kent.models.records.SalaryIncreaseRecord;
import uk.ac.kent.models.records.TerminationRecord;

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
@Access(AccessType.FIELD)
public class Employee {

    /** Logger for the class */
    @Transient
    protected static final Logger log = Logger.getAnonymousLogger();

    @Transient
    private static int nextId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(targetEntity = PersonalDetailsRecord.class, optional = false)
    @Column(name = "personal_details")
    private PersonalDetailsRecord personalDetails;

    @OneToOne(targetEntity = EmploymentDetailsRecord.class, optional = false)
    @Column(name = "employment_details")
    private EmploymentDetailsRecord employmentDetails;

    @OneToOne(targetEntity = ProbationRecord.class)
    @Column(name = "probation_record")
    private ProbationRecord probationRecord;

    @OneToOne(targetEntity = SalaryIncreaseRecord.class)
    @Column(name = "salary_increase_record")
    private SalaryIncreaseRecord salaryIncreaseRecord;

    @OneToOne(targetEntity = AnnualReviewRecord.class)
    @Column(name = "annual_review")
    private AnnualReviewRecord annualReview;

    @OneToOne(targetEntity = TerminationRecord.class)
    @Column(name = "termination_reason")
    private TerminationRecord terminationRecord;

    @SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod")
    Employee(final PersonalDetailsRecord personalDetailsRec, final EmploymentDetailsRecord employmentDetailsRec) {
        id = nextId;
        nextId++;
        personalDetails = personalDetailsRec;
        employmentDetails = employmentDetailsRec;
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    protected Employee() {}

    /**
     * Generate a fake {@link Employee}.
     *
     * @return a fake Employee
     */

    public static Employee fake() {
        return new Employee(new PersonalDetailsRecord(), new EmploymentDetailsRecord());
    }

    final int getId() {
        return id;
    }

    public final EmploymentDetailsRecord getEmploymentDetails() {
        return employmentDetails;
    }

    public final PersonalDetailsRecord getPersonalDetails() {
        return personalDetails;
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

    public final Optional<AnnualReviewRecord> getAnnualReview() {
        return Optional.ofNullable(annualReview);
    }

    public final void setAnnualReview(final AnnualReviewRecord newReview) {
        annualReview = newReview;
    }

    @SuppressWarnings("DesignForExtension")
    @Override
    public final String toString() {
        return MessageFormat.format("{0}<{1}: {2}>", getClass().getName(), id);
    }
}
