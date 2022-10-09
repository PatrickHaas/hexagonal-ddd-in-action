package de.ph.example.schedule.application;

import de.ph.example.employees.domain.EmployeeId;

public interface EmployeeRepository {

    int calculateLeftOverVacationDays(EmployeeId employeeId, int year);

}
