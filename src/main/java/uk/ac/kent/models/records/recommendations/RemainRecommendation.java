package uk.ac.kent.models.records.recommendations;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author norbert
 */

@Entity
@Table(name = "remain_recommendations")
@Access(AccessType.FIELD)
public class RemainRecommendation extends Recommendation {}
