package uk.ac.kent.models.records;

import com.github.javafaker.Faker;
import java.security.SecureRandom;
import java.sql.Blob;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;
import uk.ac.kent.models.people.Department;

/**
 * @author norbert
 */

@SuppressWarnings("PublicMethodNotExposedInInterface")
@Entity
@Table(name = "employment_details")
@Access(AccessType.FIELD)
public final class EmploymentDetailsRecord extends BaseRecord {

    private LocalDate dateEmployed;
    private long salary;
    @SuppressWarnings({"AlibabaLowerCamelCaseVariableNaming", "NonConstantFieldWithUpperCaseName"})
    @Lob
    private Blob cv;
    @Lob
    private Blob accountOfInterview;
    @Enumerated(EnumType.STRING)
    private Department department;

    /**
     * @param position
     * @param department
     * @param salary
     */

    public EmploymentDetailsRecord(final Position position, final Department department, final long salary) {
        this(position, department, LocalDate.now(), salary, null, null);
    }

    /**
     * @param position
     * @param department
     * @param dateEmployed
     * @param salary
     */

    public EmploymentDetailsRecord(final Position position, final Department department, final LocalDate dateEmployed, final long salary) {
        this(position, department, dateEmployed, salary, null, null);
    }

    /**
     * @param position
     * @param department
     * @param dateEmployed
     * @param salary
     * @param cv
     * @param accountOfInterview
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

    @SuppressWarnings({"ImplicitNumericConversion", "MagicNumber", "LocalVariableOfConcreteClass", "AccessingNonPublicFieldOfAnotherObject"})
    public static EmploymentDetailsRecord fake() {

        final EmploymentDetailsRecord record = new EmploymentDetailsRecord();

        // fake data generator
        final Faker faker = new Faker(new Locale("en-GB"));

        // random number generator
        final Random random = new SecureRandom();

        // get random position
        record.position = Position.values()[random
                .nextInt(Position.values().length)];

        // @formatter:off
        // get random LocalDate
        record.dateEmployed = LocalDate.parse(
                MessageFormat
                        .format("201{0}-{1}-{2}",
                                16 + random.nextInt(2),
                                1 + random.nextInt(12),
                                1 + random.nextInt(28)
                                ));
        // @formatter:on
        record.salary = faker.number().numberBetween(15_000, 100_000);

        return record;
    }

    public Position getPosition() { return position;}

    public void setPosition(final Position position) {
        this.position = position;
    }

    @Enumerated(EnumType.STRING)
    private Position position;

    public EmploymentDetailsRecord(final LocalDate dateEmployed) {
        this.dateEmployed = dateEmployed;
    }

    long getSalary() { return salary; }

    void raiseSalary(final long amount) { salary += amount; }

    void lowerSalary(final long amount) { salary -= amount; }

    void setSalary(final long salary) { this.salary = salary; }

    LocalDate getDateEmployed() { return dateEmployed;}

    Department getDepartment() { return department; }

    void setDepartment(final Department department) {
        this.department = department;
    }

    public Optional<Blob> getCv() { return Optional.ofNullable(cv); }

    public void setCv(final Blob cv) { this.cv = cv; }

    public Optional<Blob> getAccountOfInterview() {
        return Optional.ofNullable(accountOfInterview);
    }

    public void setAccountOfInterview(final Blob accountOfInterview) {
        this.accountOfInterview = accountOfInterview;
    }

    @SuppressWarnings("StringConcatenation")
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
                                    Arrays.toString(new Blob[]{cv}),
                                    Arrays.toString(new Blob[]{accountOfInterview}),
                                    position.toString());
        // @formatter:on
    }
}
