package uk.ac.kent.people;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author norbert
 */

@Entity
@Table(name = "directors")
public class Director extends Manager {}
