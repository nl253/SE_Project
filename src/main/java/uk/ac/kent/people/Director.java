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

    public Director(final String surname, final String name, final LocalDateTime dateEmployed, final Departament departament) {
        super(surname, name, dateEmployed, departament);
    }
}
