package uk.ac.kent.models.people;

import java.text.MessageFormat;

/**
 * Used in creation of {@link uk.ac.kent.models.records.EmploymentDetailsRecord}
 * and {@link uk.ac.kent.models.records.PromotionRecord}.
 *
 * @author norbert
 */

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public enum Department {

    ADMINISTRATION, HUMAN_RESOURCES, INFORMATION_TECHNOLOGY, SALES_AND_MARKETING, BUSINESS_INTELLIGENCE, MANAGEMENT_AND_CONSULATANCY, OTHER;

    @Override
    public String toString() {
        return MessageFormat.format("Department<{0}>", name());
    }
}
