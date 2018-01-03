package uk.ac.kent.models.people;

import java.text.MessageFormat;
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

@SuppressWarnings("MethodParameterNamingConvention")
@Entity
@Table(name = "directors")
@Access(AccessType.FIELD)
public final class Director extends Employee {

    /**
     * @param personalDetailsRecord a {@link PersonalDetailsRecord}
     * @param employmentDetailsRecord an {@link EmploymentDetailsRecord}
     */

    @SuppressWarnings("WeakerAccess")
    public Director(final PersonalDetailsRecord personalDetailsRecord, final EmploymentDetailsRecord employmentDetailsRecord) {
        super(personalDetailsRecord, employmentDetailsRecord);
    }

    /**
     * Empty constructor for Hibernate.
     */

    @SuppressWarnings("ProtectedMemberInFinalClass")
    public Director() {}

    /**
     * @return a fake {@link Director}
     */

    @SuppressWarnings("MethodOverridesStaticMethodOfSuperclass")
    public static Director fake() {
        return new Director(PersonalDetailsRecord
                                    .fake(), EmploymentDetailsRecord.fake());
    }

    @SuppressWarnings("LawOfDemeter")
    @Override
    public String toString() {
        return MessageFormat
                .format("Director<section={0}>", getEmploymentDetails().getDepartment().toString());
    }
}
