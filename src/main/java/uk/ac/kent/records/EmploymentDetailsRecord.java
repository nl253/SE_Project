package uk.ac.kent.records;

import java.time.LocalDateTime;
import uk.ac.kent.people.Department;

/**
 * @author norbert
 */

public class EmploymentDetailsRecord extends BaseRecord {

    private final LocalDateTime dateEmployed;
    private long salary;
    private Department department;

    public EmploymentDetailsRecord(final LocalDateTime dateEmployed) {
        this.dateEmployed = dateEmployed;
    }

    final long getSalary() { return salary; }

    final void raiseSalary(final long amount) { salary += amount; }

    final void lowerSalary(final long amount) { salary -= amount; }

    final void setSalary(final long salary) { this.salary = salary; }

    final LocalDateTime getDateEmployed() { return dateEmployed;}

    final Department getDepartment() { return department; }

    final void setDepartment(final Department department) {
        this.department = department;
    }

    @Override
    public final String toString() {
        return super.toString();
    }
}
