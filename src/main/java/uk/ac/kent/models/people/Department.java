package uk.ac.kent.models.people;

/**
 * @author norbert
 */

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public enum Department {

    ADMINISTRATION, HR, IT, SALESANDMARKETING, BI, MANAGEMENTCONSULATANCY, OTHER;

    @Override
    public String toString() {
        return name();
    }
}
