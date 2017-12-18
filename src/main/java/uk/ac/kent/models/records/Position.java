package uk.ac.kent.models.records;

/**
 * @author norbert
 */

public enum Position {
    DIRECTOR, EMPLOYEE, MANAGER;

    @Override
    public String toString() { return name(); }
}
