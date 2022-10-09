package de.ph.example.employees.domain;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(EmployeeId id) {
        super("employee with id %s could not be found".formatted(id == null ? null : id.value()));
    }
}
