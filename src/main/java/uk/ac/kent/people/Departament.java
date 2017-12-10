package uk.ac.kent.people;

/**
 * @author norbert
 */

@SuppressWarnings({"unused", "SpellCheckingInspection" })
enum Departament {

    HR, IT, MARKETING, OTHER;

    @Override
    public String toString() {
        return name();
    }
}
