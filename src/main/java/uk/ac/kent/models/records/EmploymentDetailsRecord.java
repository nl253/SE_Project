package uk.ac.kent.models.records;

import java.security.SecureRandom;
import java.sql.Blob;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Table;
import uk.ac.kent.models.yuconz.Department;
import uk.ac.kent.models.yuconz.Position;

/**
 * Each {@link uk.ac.kent.models.people.Employee} has a single {@link EmploymentDetailsRecord} associated with her.
 * It stores all information about the <em>current</em> employment details.
 *
 * @author norbert
 */

@SuppressWarnings("PublicMethodNotExposedInInterface")
@Entity
@Table(name = "employment_details")
@Access(AccessType.FIELD)
public final class EmploymentDetailsRecord extends BaseRecord {

    @JoinColumn(name = "date_employed")
    private LocalDate dateEmployed = LocalDate.now();

    private long salary;

    @SuppressWarnings({"AlibabaLowerCamelCaseVariableNaming", "NonConstantFieldWithUpperCaseName"})
    @Lob
    private Blob cv;

    @JoinColumn(name = "account_of_interview")
    @Lob
    private Blob accountOfInterview;

    @Enumerated(EnumType.STRING)
    private Department department;

    @Enumerated(EnumType.STRING)
    private Position position;

    /**
     * @param position position of an {@link uk.ac.kent.models.people.Employee}
     * @param department department of an {@link uk.ac.kent.models.people.Employee}
     * @param salary salary of an {@link uk.ac.kent.models.people.Employee}
     */

    public EmploymentDetailsRecord(final Position position, final Department department, final long salary) {
        this(position, department, LocalDate.now(), salary, null, null);
    }

    /**
     * @param position position of an {@link uk.ac.kent.models.people.Employee}
     * @param department department of an {@link uk.ac.kent.models.people.Employee}
     * @param dateEmployed starting date of employment of an {@link uk.ac.kent.models.people.Employee}
     * @param salary salary of an {@link uk.ac.kent.models.people.Employee}
     */

    public EmploymentDetailsRecord(final Position position, final Department department, final LocalDate dateEmployed, final long salary) {
        this(position, department, dateEmployed, salary, null, null);
    }

    /**
     * @param position position of an {@link uk.ac.kent.models.people.Employee}
     * @param department department of an {@link uk.ac.kent.models.people.Employee}
     * @param dateEmployed starting date of employment of an {@link uk.ac.kent.models.people.Employee}
     * @param salary salary of an {@link uk.ac.kent.models.people.Employee}
     * @param cv (a document) CV of an {@link uk.ac.kent.models.people.Employee}
     * @param accountOfInterview (a document) account of the interview of an {@link uk.ac.kent.models.people.Employee}
     */

    @SuppressWarnings("ConstructorWithTooManyParameters")
    public EmploymentDetailsRecord(final Position position, final Department department, final LocalDate dateEmployed, final long salary, final Blob cv, final Blob accountOfInterview) {
        this.dateEmployed = dateEmployed;
        this.salary = salary;
        this.department = department;
        this.cv = cv;
        this.accountOfInterview = accountOfInterview;
        this.position = position;
    }

    /**
     * Empty constructor for Hibernate.
     */

    public EmploymentDetailsRecord() {}

    /**
     * @return a fake {@link EmploymentDetailsRecord}.
     */

    @SuppressWarnings({"ImplicitNumericConversion", "MagicNumber", "LocalVariableOfConcreteClass", "AccessingNonPublicFieldOfAnotherObject"})
    public static EmploymentDetailsRecord fake() {

        // random number generator
        final Random random = new SecureRandom();

        // get random position
        final Position position = Position.values()[random
                .nextInt(Position.values().length)];

        final Department department = Department.values()[random
                .nextInt(Department.values().length)];

        // @formatter:off
        // get random LocalDate
        final LocalDate date = LocalDate.parse(
                MessageFormat
                        .format("20{0}-{1}-{2}",
                                16 + random.nextInt(2),
                                1 + random.nextInt(12),
                                1 + random.nextInt(28)
                                ));
        // @formatter:on
        final long salary = 15_000 + random.nextInt(30_000);

        return new EmploymentDetailsRecord(position, department, date, salary);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(final Position position) {
        this.position = position;
    }

    public long getSalary() {
        return salary;
    }

    public void raiseSalary(final long amount) {
        salary += amount;
    }

    public void lowerSalary(final long amount) {
        salary -= amount;
    }

    public void setSalary(final long salary) {
        this.salary = salary;
    }

    public LocalDate getDateEmployed() {
        return dateEmployed;
    }

    public Department getDepartment() {
        return department;
    }

    void setDepartment(final Department department) {
        this.department = department;
    }

    public Optional<Blob> getCv() {
        return Optional.ofNullable(cv);
    }

    public void setCv(final Blob cv) {
        this.cv = cv;
    }

    public Optional<Blob> getAccountOfInterview() {
        return Optional.ofNullable(accountOfInterview);
    }

    public void setAccountOfInterview(final Blob accountOfInterview) {
        this.accountOfInterview = accountOfInterview;
    }

    @SuppressWarnings({"StringConcatenation", "ConditionalExpression", "VariableNotUsedInsideIf"})
    @Override
    public String toString() {
        // @formatter:off
        return MessageFormat.format("EmploymentDetailsRecord<" +
                                            "dateEmployed={0}, " +
                                            "salary={1}, " +
                                            "department={2}, " +
                                            "CV={3}, " +
                                            "accountOfInterview={4}, " +
                                            "position={5}>",
                                    dateEmployed.toString(),
                                    salary,
                                    department.toString(),
                                    (cv == null) ? "not uploaded":"uploaded",
                                    (accountOfInterview == null) ? "not uploaded":"uploaded",
                                    position.toString());
        // @formatter:on
    }
}
