package uk.ac.kent.records.recommendations;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Base recommendation is the superclass for all types
 * of recommendations kept in the system.
 * <p>
 * It equips all subclasses with basic fields and
 * getters / setters. Because it is abstract, it cannot
 * be directly instantiated.
 *
 * @author norbert
 */

@Entity(name = "Recommendation")
@Table(name = "recommendations")
public abstract class BaseRecommendation {}
