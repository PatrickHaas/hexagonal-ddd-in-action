package de.ph.example.employees.infrastructure.web;

import java.time.LocalDate;

public record HireEmployeeRequest(String firstName, String lastName, LocalDate birthdate) {
}