package de.ph.example.employees.application;

import de.ph.example.employees.domain.EmployeeId;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(EmployeeId id) {
        super("An employee with the id %s could not be found".formatted(id != null ? id.value() : null));
    }
}
