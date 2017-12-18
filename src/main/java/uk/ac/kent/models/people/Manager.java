package uk.ac.kent.models.people;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import uk.ac.kent.models.records.EmploymentDetailsRecord;
import uk.ac.kent.models.records.PersonalDetailsRecord;

/**
 * @author norbert
 */

@SuppressWarnings({"NonBooleanMethodNameMayNotStartWithQuestion", "MethodParameterNamingConvention", "WeakerAccess", "PublicConstructorInNonPublicClass", "MethodOverridesStaticMethodOfSuperclass"})
@Entity
@Table(name = "managers")
class Manager extends Employee {

    /**
     * A group of {@link Employee}s that the manger has been assigned.
     */

    @OneToMany
    private List<Employee> employees;

    public Manager(final PersonalDetailsRecord personalDetailsRecord, final EmploymentDetailsRecord employmentDetailsRecord, final List<Employee> employees) {
        super(personalDetailsRecord, employmentDetailsRecord);
        this.employees = employees;
    }

    public Manager(final PersonalDetailsRecord personalDetailsRecord, final EmploymentDetailsRecord employmentDetailsRecord) {
        this(personalDetailsRecord, employmentDetailsRecord, new LinkedList<>());
    }

    public Manager() {}

    /**
     * Fake manager.
     *
     * @return A fake Manager
     */

    public static Manager fake() {
        // @formatter:off
        return new Manager(
                PersonalDetailsRecord.fake(),
                EmploymentDetailsRecord.fake(),
                IntStream.range(0, 10)
                        .mapToObj(x -> Employee.fake())
                        .collect(Collectors.toList()));
        // @formatter:on
    }

    @SuppressWarnings("OptionalContainsCollection")
    final Optional<List<Employee>> getEmployees() {
        return Optional.of(Collections.unmodifiableList(employees));
    }

    final void addEmployee(final Employee e) { employees.add(e); }

    final void removeEmployee(final Employee e) { employees.remove(e); }
}
