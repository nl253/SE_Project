package uk.ac.kent.models.people;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import uk.ac.kent.models.records.EmploymentDetailsRecord;
import uk.ac.kent.models.records.PersonalDetailsRecord;

/**
 * A {@link Director} is an {@link Employee} that in addition to all the
 * properties of an {@link Employee} has a set of additional privileges and responsibilities.
 *
 * @author norbert
 */

@Table(name = "directors")
@SuppressWarnings("MethodParameterNamingConvention")
@Entity
@Access(AccessType.FIELD)
public final class Director extends Employee {

    /**
     * @param password a {@link String} password
     * @param personalDetails a {@link PersonalDetailsRecord} record
     * @param employmentDetails an {@link EmploymentDetailsRecord} record
     */

    public Director(final PersonalDetailsRecord personalDetails, final EmploymentDetailsRecord employmentDetails, final String password) {
        super(personalDetails, employmentDetails, password);
    }

    /**
     * @param personalDetailsRecord a {@link PersonalDetailsRecord}
     * @param employmentDetailsRecord an {@link EmploymentDetailsRecord}
     */

    @SuppressWarnings("WeakerAccess")
    public Director(final PersonalDetailsRecord personalDetailsRecord, final EmploymentDetailsRecord employmentDetailsRecord) {
        this(personalDetailsRecord, employmentDetailsRecord, null);
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    public Director() {}
}
