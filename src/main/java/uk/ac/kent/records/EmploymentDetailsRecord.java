package uk.ac.kent.records;

import java.sql.Blob;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import uk.ac.kent.people.Department;

/**
 * @author norbert
 */

@SuppressWarnings("PublicMethodNotExposedInInterface")
public final class EmploymentDetailsRecord extends BaseRecord {

    private final LocalDateTime dateEmployed;
    private long salary;
    private Department department;

    @SuppressWarnings("ConstructorWithTooManyParameters")
    public EmploymentDetailsRecord(final LocalDateTime dateEmployed, final long salary, final Department department, final Blob CV, final Blob accountOfInterview, final Position position) {
        this.dateEmployed = dateEmployed;
        this.salary = salary;
        this.department = department;
        this.CV = CV;
        this.accountOfInterview = accountOfInterview;
        this.position = position;
    }

    @SuppressWarnings({"AlibabaLowerCamelCaseVariableNaming", "NonConstantFieldWithUpperCaseName"})
    private Blob CV;
    private Blob accountOfInterview;

    public Position getPosition() {
        return position;
    }

    public void setPosition(final Position position) {
        this.position = position;
    }

    private Position position;

    public EmploymentDetailsRecord(final LocalDateTime dateEmployed) {
        this.dateEmployed = dateEmployed;
    }

    long getSalary() { return salary; }

    void raiseSalary(final long amount) { salary += amount; }

    void lowerSalary(final long amount) { salary -= amount; }

    void setSalary(final long salary) { this.salary = salary; }

    LocalDateTime getDateEmployed() { return dateEmployed;}

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
