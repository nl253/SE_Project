package uk.ac.kent.records.recommendations;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author norbert
 */

@Entity
@Table(name = "salary_increase_recommendations")
public class SalaryIncreaseRecommendation extends BaseRecommendation {}
