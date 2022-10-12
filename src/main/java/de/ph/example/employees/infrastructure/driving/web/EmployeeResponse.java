package de.ph.example.employees.infrastructure.driving.web;

import de.ph.example.employees.domain.Employee;

import java.time.LocalDate;

public record EmployeeResponse(String id, String firstname, String lastname, LocalDate birthdate) {

    public static EmployeeResponse fromEmployee(Employee employee) {
        return new EmployeeResponse(employee.getId().value(),
                employee.getFirstName().value(),
                employee.getLastName().value(),
                employee.getBirthdate().value());
    }
}
