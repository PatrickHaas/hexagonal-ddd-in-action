package de.ph.example.employees.domain;

public class IllegalFiringException extends RuntimeException {
    public IllegalFiringException(EmployeeId employeeId) {
        super("firing employee with id %s is illegal".formatted(employeeId == null ? null : employeeId.value()));
    }
}
