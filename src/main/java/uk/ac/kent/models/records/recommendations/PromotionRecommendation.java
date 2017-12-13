package uk.ac.kent.models.records.recommendations;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author norbert
 */

@Entity
@Table(name = "promotion_recommendations")
@Access(AccessType.FIELD)
public class PromotionRecommendation extends Recommendation {}
