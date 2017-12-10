package uk.ac.kent.people;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import uk.ac.kent.records.EmploymentDetailsRecord;
import uk.ac.kent.records.PersonalDetailsRecord;

/**
 * @author norbert
 */

@SuppressWarnings({"NonBooleanMethodNameMayNotStartWithQuestion", "MethodParameterNamingConvention"})
@Entity
@Table(name = "managers")
class Manager extends Employee {

    private final List<Employee> employees;

    Manager(final PersonalDetailsRecord personalDetailsRecord, final EmploymentDetailsRecord employmentDetailsRecord, final List<Employee> employees) {
        super(personalDetailsRecord, employmentDetailsRecord);
        this.employees = employees;
    }

    Manager(final PersonalDetailsRecord personalDetailsRecord, final EmploymentDetailsRecord employmentDetailsRecord) {
        this(personalDetailsRecord, employmentDetailsRecord, new LinkedList<Employee>());
    }

    final List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    final void addEmployee(final Employee e) { employees.add(e); }

    final void removeEmployee(final Employee e) { employees.remove(e); }
}
