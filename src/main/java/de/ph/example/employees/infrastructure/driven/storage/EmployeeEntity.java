package de.ph.example.employees.infrastructure.driven.storage;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
public record EmployeeEntity(@Id String id, String firstName, String lastName, LocalDate birthdate, LocalDate hireDate,
                             LocalDate fireDate) {
}
