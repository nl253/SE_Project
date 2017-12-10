package uk.ac.kent.people;

/**
 * @author norbert
 */

@SuppressWarnings({"unused", "SpellCheckingInspection" })
public enum Department {

    HR, IT, MARKETING, OTHER;

    @Override
    public String toString() {
        return name();
    }
}
