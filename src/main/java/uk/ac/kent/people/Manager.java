package uk.ac.kent.people;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author norbert
 */

@Entity
@Table(name = "managers")
class Manager extends Employee {

    Manager(final String surname, final String name, final LocalDateTime dateEmployed, final Departament departament) {
        super(surname, name, dateEmployed, departament);
    }
}
