package uk.ac.kent.models.people;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import uk.ac.kent.models.records.EmploymentDetailsRecord;
import uk.ac.kent.models.records.PersonalDetailsRecord;

/**
 * @author norbert
 */

@SuppressWarnings({"NonBooleanMethodNameMayNotStartWithQuestion", "MethodParameterNamingConvention"})
@Entity
@Table(name = "managers")
class Manager extends Employee {

    @OneToMany
    private final List<Employee> employees;

    public Manager(final PersonalDetailsRecord personalDetailsRecord, final EmploymentDetailsRecord employmentDetailsRecord, final List<Employee> employees) {
        super(personalDetailsRecord, employmentDetailsRecord);
        this.employees = employees;
    }

    public Manager(final PersonalDetailsRecord personalDetailsRecord, final EmploymentDetailsRecord employmentDetailsRecord) {
        this(personalDetailsRecord, employmentDetailsRecord, new LinkedList<>());
    }

    public Manager() {
        this(new PersonalDetailsRecord(), new EmploymentDetailsRecord());
    }

    final List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    final void addEmployee(final Employee e) { employees.add(e); }

    final void removeEmployee(final Employee e) { employees.remove(e); }
}
