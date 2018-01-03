package uk.ac.kent.models.people;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
 * {@link Employee} class represents all {@link Employee}s working for the company.
 * This includes {@link Director}s and {@link Manager}s which are subclasses of {@link Employee}.
 * <p>
 * To avoid data duplication, the {@link Employee} doesn't store her personal details.
 * Instead, they are stored in {@link PersonalDetailsRecord} which is one of the
 * documents that the system needs to handle.
 * <p>
 * Therefore, modifying an {@link Employee}'s details requires you to modify their
 * {@link PersonalDetailsRecord}. The same applies to {@link EmploymentDetailsRecord}.
 *
 * @author norbert
 */

@SuppressWarnings({"PublicMethodNotExposedInInterface", "NonBooleanMethodNameMayNotStartWithQuestion", "MethodParameterNamingConvention", "WeakerAccess"})
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
    @JoinColumn(name = "personal_details", unique = true)
    private PersonalDetailsRecord personalDetails;

    @OneToOne(targetEntity = EmploymentDetailsRecord.class, optional = false)
    @JoinColumn(name = "employment_details")
    private EmploymentDetailsRecord employmentDetails;

    @OneToMany(targetEntity = ProbationRecord.class)
    @JoinColumn(name = "probation_record")
    private List<ProbationRecord> probationRecords;

    @OneToMany(targetEntity = SalaryIncreaseRecord.class)
    @JoinColumn(name = "salary_increase_record")
    private List<SalaryIncreaseRecord> salaryIncreaseRecords;

    @JoinColumn(name = "annual_review")
    @OneToMany(targetEntity = AnnualReviewRecord.class)
    private List<AnnualReviewRecord> annualReviewRecords;

    @OneToOne(targetEntity = TerminationRecord.class)
    @JoinColumn(name = "termination_reason")
    private TerminationRecord terminationRecord;

    /**
     * @param personalDetailsRec a {@link PersonalDetailsRecord}
     * @param employmentDetailsRec an {@link EmploymentDetailsRecord}
     */

    @SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod")
    public Employee(final PersonalDetailsRecord personalDetailsRec, final EmploymentDetailsRecord employmentDetailsRec) {
        id = nextId;
        nextId++;
        personalDetails = personalDetailsRec;
        employmentDetails = employmentDetailsRec;
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    public Employee() {}

    /**
     * @return a fake {@link Employee}
     */

    public static Employee fake() {
        return new Employee(new PersonalDetailsRecord(), new EmploymentDetailsRecord());
    }

    public final int getId() {
        return id;
    }

    public final EmploymentDetailsRecord getEmploymentDetails() {
        return employmentDetails;
    }

    public final PersonalDetailsRecord getPersonalDetails() {
        return personalDetails;
    }

    public final Optional<List<ProbationRecord>> getProbationRecords() {
        return Optional.ofNullable(probationRecords);
    }

    public final void setProbationRecords(final List<ProbationRecord> probationRecords) {
        this.probationRecords = probationRecords;
    }

    public final Optional<List<SalaryIncreaseRecord>> getSalaryIncreaseRecords() {
        return Optional.ofNullable(salaryIncreaseRecords);
    }

    public final void setSalaryIncreaseRecords(final List<SalaryIncreaseRecord> salaryIncreaseRecords) {
        this.salaryIncreaseRecords = salaryIncreaseRecords;
    }

    public final Optional<TerminationRecord> getTerminationRecord() {
        return Optional.ofNullable(terminationRecord);
    }

    public final void setTerminationRecord(final TerminationRecord terminationRecord) {
        this.terminationRecord = terminationRecord;
    }

    public final Optional<List<AnnualReviewRecord>> getAnnualReviews() {
        return Optional.ofNullable(annualReviewRecords);
    }

    public final void setAnnualReviewRecords(final List<AnnualReviewRecord> newRecords) {
        annualReviewRecords = newRecords;
    }

    @SuppressWarnings("DesignForExtension")
    @Override
    public String toString() {
        return MessageFormat.format("{0}<id={1}, name={2}>", getClass()
                .getName(), id, getPersonalDetails().getFullName());
    }
}
