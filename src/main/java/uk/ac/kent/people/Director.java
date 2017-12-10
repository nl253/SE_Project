package uk.ac.kent.people;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author norbert
 */

@Entity
@Table(name = "directors")
public class Director extends Manager {

    Director(final String surname, final String name, final LocalDateTime dateEmployed, final Department department) {
        super(surname, name, dateEmployed, department);
    }
}
