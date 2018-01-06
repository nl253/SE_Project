package uk.ac.kent.models.people;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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

@SuppressWarnings({"PublicMethodNotExposedInInterface", "NonBooleanMethodNameMayNotStartWithQuestion", "MethodParameterNamingConvention", "WeakerAccess", "DesignForExtension", "ClassWithTooManyFields"})
@Entity
@Table(name = "employees")
@Access(AccessType.FIELD)
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee {

    /** Logger for the class */
    @Transient
    protected static final Logger log = Logger.getAnonymousLogger();

    @Transient
    private static final Random random = new SecureRandom();

    private String password = IntStream.range(0, 10)
            .collect(String::new, (String s, int i) -> s += ((char) i), (String s, String s2) -> s += s2);

    @Id
    @GeneratedValue
    protected int id;

    @OneToOne
    @JoinColumn(name = "personal_details")
    private PersonalDetailsRecord personalDetails;

    @OneToOne
    @JoinColumn(name = "employment_details")
    private EmploymentDetailsRecord employmentDetails;

    @OneToMany
    @JoinColumn(name = "probation_record")
    private List<ProbationRecord> probationRecords;

    @OneToMany
    @JoinColumn(name = "salary_increase_record")
    private List<SalaryIncreaseRecord> salaryIncreaseRecords;

    @JoinColumn(name = "annual_review")
    @OneToMany
    private List<AnnualReviewRecord> annualReviewRecords;

    @OneToOne
    @JoinColumn(name = "termination_reason")
    private TerminationRecord terminationRecord;

    // /**
    //  * @param personalDetailsRec a {@link PersonalDetailsRecord}
    //  * @param employmentDetailsRec an {@link EmploymentDetailsRecord}
    //  */

    // @SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod")
    // public Employee(final PersonalDetailsRecord personalDetailsRec, final EmploymentDetailsRecord employmentDetailsRec) {
    //     id = nextId;
    //     nextId++;
    //     personalDetails = personalDetailsRec;
    //     employmentDetails = employmentDetailsRec;
    // }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    public Employee() {}


    /**
     * @return a fake {@link Employee}
     */

    public static Employee fake() {
        final Employee e = new Employee();
        e.setPersonalDetails(PersonalDetailsRecord.fake());
        e.setEmploymentDetails(EmploymentDetailsRecord.fake());
        return e;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public EmploymentDetailsRecord getEmploymentDetails() {
        return employmentDetails;
    }

    public PersonalDetailsRecord getPersonalDetails() {
        return personalDetails;
    }

    public List<ProbationRecord> getProbationRecords() {
        return probationRecords;
    }

    public void setProbationRecords(final List<ProbationRecord> probationRecords) {
        this.probationRecords = probationRecords;
    }

    public void setPersonalDetails(final PersonalDetailsRecord personalDetails) {
        this.personalDetails = personalDetails;
    }

    public void setEmploymentDetails(final EmploymentDetailsRecord employmentDetails) {
        this.employmentDetails = employmentDetails;
    }

    public List<AnnualReviewRecord> getAnnualReviewRecords() {
        return annualReviewRecords;
    }

    public List<SalaryIncreaseRecord> getSalaryIncreaseRecords() {
        return salaryIncreaseRecords;
    }

    public void setSalaryIncreaseRecords(final List<SalaryIncreaseRecord> salaryIncreaseRecords) {
        this.salaryIncreaseRecords = salaryIncreaseRecords;
    }

    public TerminationRecord getTerminationRecord() {
        return terminationRecord;
    }

    public void setTerminationRecord(final TerminationRecord terminationRecord) {
        this.terminationRecord = terminationRecord;
    }

    public List<AnnualReviewRecord> getAnnualReviews() {
        return annualReviewRecords;
    }

    public void setAnnualReviewRecords(final List<AnnualReviewRecord> newRecords) {
        annualReviewRecords = newRecords;
    }

    @SuppressWarnings({"DesignForExtension", "ConditionalExpression"})
    @Override
    public String toString() {
        return MessageFormat.format("{0}<id={1}, name={2}>", getClass()
                .getName(), id, (personalDetails == null) ? "not available" : personalDetails
                .getFullName());
    }
}
