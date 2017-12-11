package uk.ac.kent.records;

import uk.ac.kent.people.Employee;

public enum Position {
    DIRECTOR, EMPLOYEE, MANAGER;

    @Override
    public String toString() {
        return name();
    }
}
