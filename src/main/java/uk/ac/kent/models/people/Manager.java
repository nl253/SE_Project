package uk.ac.kent.models.people;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
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
@Access(AccessType.FIELD)
public class Manager extends Employee {

    /**
     * A group of {@link Employee}s that the manger has been assigned.
     */

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private final List<Employee> employees;

    public Manager(final PersonalDetailsRecord personalDetailsRecord, final EmploymentDetailsRecord employmentDetailsRecord, final Iterable<Employee> employees) {
        super(personalDetailsRecord, employmentDetailsRecord);
        employees.forEach(this::addEmployee);
        this.employees = new ArrayList<>(15);
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    protected Manager() {employees = new ArrayList<>(15);}

    /**
     * Fake {@link Manager}. Each fake {@link Manager} will have 15 {@link Employee }s.
     *
     * @return A fake Manager
     */

    public static Manager fake() {
        // @formatter:off
        return new Manager(
                PersonalDetailsRecord.fake(),
                EmploymentDetailsRecord.fake(),
                IntStream.range(0, 15)
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
