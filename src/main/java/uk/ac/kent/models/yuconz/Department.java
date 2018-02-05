package uk.ac.kent.models.yuconz;

import java.text.MessageFormat;

/**
 * @author Norbert
 */

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public enum Department {

    // @formatter:off
    ADMINISTRATION,
    HUMAN_RESOURCES,
    INFORMATION_TECHNOLOGY,
    SALES_AND_MARKETING,
    BUSINESS_INTELLIGENCE,
    MANAGEMENT_AND_CONSULATANCY,
    OTHER;
    // @formatter:on

    @Override
    public String toString() {
        return MessageFormat.format("Department<{0}>", name());
    }
}
