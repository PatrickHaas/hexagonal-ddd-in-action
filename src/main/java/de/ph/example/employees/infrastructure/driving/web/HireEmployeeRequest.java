package de.ph.example.employees.infrastructure.driving.web;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record HireEmployeeRequest(String firstName, String lastName,
                                  @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate) {
}