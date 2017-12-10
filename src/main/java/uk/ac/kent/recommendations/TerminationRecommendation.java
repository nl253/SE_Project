package uk.ac.kent.recommendations;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author norbert
 */

@Entity
@Table(name = "termination_recommendations")
public class TerminationRecommendation extends BaseRecommendation {}
