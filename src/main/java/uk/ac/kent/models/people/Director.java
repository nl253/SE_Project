package uk.ac.kent.models.people;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import uk.ac.kent.models.records.EmploymentDetailsRecord;
import uk.ac.kent.models.records.PersonalDetailsRecord;

/**
 * @author norbert
 */

@SuppressWarnings("MethodParameterNamingConvention")
@Entity
@Table(name = "directors")
@Access(AccessType.FIELD)
public final class Director extends Employee {

    @SuppressWarnings("WeakerAccess")
    public Director(final PersonalDetailsRecord personalDetailsRecord, final EmploymentDetailsRecord employmentDetailsRecord) {
        super(personalDetailsRecord, employmentDetailsRecord);
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    public Director() {}

    @SuppressWarnings("MethodOverridesStaticMethodOfSuperclass")
    public static Director fake() {
        return new Director(PersonalDetailsRecord.fake(), EmploymentDetailsRecord.fake());
    }
}
