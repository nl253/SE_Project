package uk.ac.kent.models.people;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import uk.ac.kent.models.records.EmploymentDetailsRecord;
import uk.ac.kent.models.records.PersonalDetailsRecord;
import uk.ac.kent.models.yuconz.Department;

/**
 * A {@link Manager} is an {@link Employee} that in addition to all the
 * properties of an {@link Employee} has a set of additional privileges and responsibilities.
 * Each {@link Department} has many {@link Manager}s and each one of those
 * {@link Manager}s has a {@link java.util.Collection} of {@link Employee}s that she is responsible for.
 *
 * @author norbert
 */

@Table(name = "managers")
@SuppressWarnings({"NonBooleanMethodNameMayNotStartWithQuestion", "MethodParameterNamingConvention", "WeakerAccess", "PublicConstructorInNonPublicClass", "MethodOverridesStaticMethodOfSuperclass"})
@Entity
@Access(AccessType.FIELD)
public final class Manager extends Employee {

    /**
     * A group of {@link Employee}s that the manger has been assigned.
     */

    @SuppressWarnings("FieldMayBeFinal")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, targetEntity = Employee.class)
    private List<Employee> employees = new ArrayList<>(15);

    /**
     * @param personalDetailsRecord a {@link PersonalDetailsRecord}
     * @param employmentDetailsRecord an {@link EmploymentDetailsRecord}
     * @param employees an {@link Iterable} of {@link Employee}s
     */

    public Manager(final PersonalDetailsRecord personalDetailsRecord, final EmploymentDetailsRecord employmentDetailsRecord, final Iterable<Employee> employees) {
        super(personalDetailsRecord, employmentDetailsRecord);
        employees.forEach(this::addEmployee);
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    protected Manager() {}

    /**
     * @return A fake {@link Manager}
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
    final List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    final void addEmployee(final Employee e) {
        employees.add(e);
    }

    final void removeEmployee(final Employee e) {
        employees.remove(e);
    }

    @Override
    public String toString() {
        return MessageFormat
                .format("Manager<employees={0}>", getEmployees().toString());
    }
}
