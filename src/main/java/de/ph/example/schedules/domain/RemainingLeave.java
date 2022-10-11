package de.ph.example.schedules.domain;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record RemainingLeave(EmployeeId employeeId, int year, int days) {

    public RemainingLeave {
        if (employeeId == null) {
            throw new IllegalArgumentException("Remaining leave must be associated with an employee");
        }
        if (days < 0) {
            throw new IllegalArgumentException("days must be positive or zero");
        }
    }
}
