package de.ph.example.schedules.application;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedules.domain.PermittedLeave;

public interface EmployeeRepository {

    PermittedLeave calculatePermittedLeave(EmployeeId employeeId);
}
