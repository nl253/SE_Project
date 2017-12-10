package uk.ac.kent.people;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author norbert
 */

@Entity
@Table(name = "managers")
class Manager extends Employee {

    private List<Employee> employees;

    Manager(final String surname, final String name, final LocalDateTime dateEmployed, final Department department) {
        super(surname, name, dateEmployed, department);
        employees = new ArrayList<>(10);
    }

    final List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    final void setEmployees(final List<Employee> employees) {
        this.employees = employees;
    }
}
