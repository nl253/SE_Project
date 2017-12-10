package uk.ac.kent.records.recommendations;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author norbert
 */

@Entity
@Table(name = "promotion_recommendations")
public class PromotionRecommendation extends BaseRecommendation {}
