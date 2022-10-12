package de.ph.example.employees.infrastructure.driving.web;

import de.ph.example.employees.domain.DepartmentId;
import de.ph.example.employees.domain.Employee;

import java.time.LocalDate;
import java.util.List;

public record EmployeeResponse(String id, String firstname, String lastname, LocalDate birthdate,
                               List<String> assignedDepartments) {

    public static EmployeeResponse fromEmployee(Employee employee) {
        return new EmployeeResponse(employee.getId().value(),
                employee.getFirstName().value(),
                employee.getLastName().value(),
                employee.getBirthdate().value(),
                employee.getAssignedDepartments().stream().map(DepartmentId::value).toList());
    }
}
