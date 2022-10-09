package de.ph.example.schedule.domain;

import de.ph.example.employees.domain.EmployeeId;

public record RemainingLeave(EmployeeId employeeId, int days) {
}
