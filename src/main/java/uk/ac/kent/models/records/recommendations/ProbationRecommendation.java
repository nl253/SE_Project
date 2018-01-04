package uk.ac.kent.models.records.recommendations;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * @author norbert
 */

@Table(name = "probation_recommendations")
@Entity
@Access(AccessType.FIELD)
public class ProbationRecommendation extends BaseRecommendation {}
