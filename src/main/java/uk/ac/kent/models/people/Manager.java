package uk.ac.kent.models.people;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

@SuppressWarnings({"NonBooleanMethodNameMayNotStartWithQuestion", "MethodParameterNamingConvention", "WeakerAccess", "PublicConstructorInNonPublicClass", "MethodOverridesStaticMethodOfSuperclass"})
@Table(name = "managers")
@Entity
@Access(AccessType.FIELD)
public final class Manager extends Employee {

    /**
     * A group of {@link Employee}s that the manger has been assigned.
     */

    @SuppressWarnings("FieldMayBeFinal")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private List<Employee> employees = new ArrayList<>(15);

    /**
     * @param personalDetails a {@link PersonalDetailsRecord} record
     * @param employmentDetails an {@link EmploymentDetailsRecord} record
     * @param password a {@link String} password
     */

    public Manager(final PersonalDetailsRecord personalDetails, final EmploymentDetailsRecord employmentDetails, final String password) {
        super(personalDetails, employmentDetails, password);
    }

    /**
     * @param personalDetailsRec a {@link PersonalDetailsRecord}
     * @param employmentDetailsRec an {@link EmploymentDetailsRecord}
     */

    public Manager(final PersonalDetailsRecord personalDetailsRec, final EmploymentDetailsRecord employmentDetailsRec) {
        super(personalDetailsRec, employmentDetailsRec);
    }

    /**
     * @param personalDetails a {@link PersonalDetailsRecord} record
     * @param employmentDetails an {@link EmploymentDetailsRecord} record
     * @param password a {@link String} password
     * @param employees an {@link Iterable} of {@link Employee}s
     */

    public Manager(final PersonalDetailsRecord personalDetails, final EmploymentDetailsRecord employmentDetails, final String password, final Collection<Employee> employees) {
        super(personalDetails, employmentDetails, password);
        this.employees.addAll(employees);
    }

    /**
     * Empty constructor for Hibernate.
     */

    public Manager() {}

    /**
     * @return A fake {@link Manager}
     */


    @SuppressWarnings("OptionalContainsCollection")
    List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    void addEmployee(final Employee e) {
        employees.add(e);
    }

    void removeEmployee(final Employee e) {
        employees.remove(e);
    }

    @SuppressWarnings("PublicMethodNotExposedInInterface")
    public void setEmployees(final Collection<Employee> employees) {
        if (this.employees == null) this.employees = new ArrayList<>(15);
        this.employees.addAll(employees);
    }

    @SuppressWarnings("ConditionalExpression")
    @Override
    public String toString() {
        return (employees != null) ? MessageFormat
                .format("Manager<employees={0}>", employees
                        .toString()) : MessageFormat
                       .format("Manager<employees={0}>", "not available");
    }
}
