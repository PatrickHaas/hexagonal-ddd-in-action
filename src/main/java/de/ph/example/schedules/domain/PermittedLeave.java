package de.ph.example.schedules.domain;

import de.ph.example.employees.domain.EmployeeId;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record PermittedLeave(EmployeeId employeeId, int days) {

    public PermittedLeave {
        if (employeeId == null) {
            throw new IllegalArgumentException("Permitted leave must be associated with an employee");
        }
        if (days < 0) {
            throw new IllegalArgumentException("days must be positive or zero");
        }
    }
}
