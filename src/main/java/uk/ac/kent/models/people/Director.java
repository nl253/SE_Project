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

    public Director(final PersonalDetailsRecord personalDetailsRecord, final EmploymentDetailsRecord employmentDetailsRecord) {
        super(personalDetailsRecord, employmentDetailsRecord);
    }

    public Director() {
        super(new PersonalDetailsRecord(), new EmploymentDetailsRecord());
    }
}
