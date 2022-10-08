package de.ph.example.employees.domain;

public class IllegalFiringException extends RuntimeException {
    public IllegalFiringException(Employee employee) {
        super("firing employee with id %s is illegal".formatted(employee.getId().value()));
    }
}
