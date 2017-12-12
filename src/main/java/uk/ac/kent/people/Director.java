package uk.ac.kent.people;

import javax.persistence.Entity;
import javax.persistence.Table;
import uk.ac.kent.records.EmploymentDetailsRecord;
import uk.ac.kent.records.PersonalDetailsRecord;

/**
 * @author norbert
 */

@SuppressWarnings("MethodParameterNamingConvention")
@Entity
@Table(name = "directors")
public final class Director extends Employee {

    public Director(final PersonalDetailsRecord personalDetailsRecord, final EmploymentDetailsRecord employmentDetailsRecord) {
        super(personalDetailsRecord, employmentDetailsRecord);
    }

    public Director() {
        super(new PersonalDetailsRecord(), new EmploymentDetailsRecord());
    }
}
