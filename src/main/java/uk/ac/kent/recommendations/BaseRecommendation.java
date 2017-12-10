package uk.ac.kent.recommendations;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author norbert
 */

@Entity(name = "Recommendation")
@Table(name = "recommendations")
abstract class BaseRecommendation {}
