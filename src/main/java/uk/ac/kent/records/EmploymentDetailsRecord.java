package uk.ac.kent.records;

import com.github.javafaker.Faker;
import java.security.SecureRandom;
import java.sql.Blob;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Random;
import uk.ac.kent.people.Department;

/**
 * @author norbert
 */

@SuppressWarnings("PublicMethodNotExposedInInterface")
public final class EmploymentDetailsRecord extends BaseRecord {

    private final LocalDate dateEmployed;
    private long salary;
    @SuppressWarnings({"AlibabaLowerCamelCaseVariableNaming", "NonConstantFieldWithUpperCaseName"})
    private Blob CV;
    private Blob accountOfInterview;
    private Department department;

    @SuppressWarnings("ConstructorWithTooManyParameters")
    public EmploymentDetailsRecord(final LocalDate dateEmployed, final long salary, final Department department, final Blob CV, final Blob accountOfInterview, final Position position) {
        this.dateEmployed = dateEmployed;
        this.salary = salary;
        this.department = department;
        this.CV = CV;
        this.accountOfInterview = accountOfInterview;
        this.position = position;
    }

    @SuppressWarnings({"ImplicitNumericConversion", "MagicNumber"})
    public EmploymentDetailsRecord() {

        // fake data generator
        final Faker faker = new Faker(new Locale("en-GB"));

        // random number generator
        final Random random = new SecureRandom();

        // get random position
        position = Position.values()[random.nextInt(Position.values().length)];

        // @formatter:off
        // get random LocalDate
        dateEmployed = LocalDate.parse(
                MessageFormat
                        .format("201{0}-{1}-{2}",
                                16 + random.nextInt(2),
                                1 + random.nextInt(12),
                                1 + random.nextInt(28)
                                ));
        // @formatter:on
        salary = faker.number().numberBetween(15_000, 100_000);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(final Position position) {
        this.position = position;
    }

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

    @Override
    public String toString() {
        return MessageFormat
                .format("EmploymentDetailsRecord<dateEmployed={0}, salary={1}, department={2}, CV={3}, accountOfInterview={4}, position={5}>", dateEmployed, salary, department, CV, accountOfInterview, position);
    }
}
