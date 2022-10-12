package de.ph.example.employees.domain;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(DepartmentId departmentId) {
        super("a department with the id %s could not be found".formatted(departmentId == null ? null : departmentId.value()));
    }
}
