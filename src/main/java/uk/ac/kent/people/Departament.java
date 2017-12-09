package uk.ac.kent.people;

/**
 * @author norbert
 */

@SuppressWarnings({"unused", "SpellCheckingInspection", "FieldNamingConvention", "EnumClass"})
enum Departament {

    HR, IT, MARKETING, OTHER;

    @Override
    public String toString() {
        return this.name();
    }
}
