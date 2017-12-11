package uk.ac.kent.records;

public enum Position {
    DIRECTOR, EMPLOYEE, MANAGER;

    @Override
    public String toString() {
        return name();
    }
}
