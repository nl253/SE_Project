package uk.ac.kent.people;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author norbert
 */

@SuppressWarnings("NonBooleanMethodNameMayNotStartWithQuestion")
@Entity
@Table(name = "managers")
class Manager extends Employee {

    private final List<Employee> employees;

    Manager(final List<Employee> employees) { this.employees = employees; }

    Manager() { employees = new ArrayList<>(10); }

    final List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    final void addEmployee(final Employee e) { employees.add(e); }

    final void removeEmployee(final Employee e) { employees.remove(e); }
}
