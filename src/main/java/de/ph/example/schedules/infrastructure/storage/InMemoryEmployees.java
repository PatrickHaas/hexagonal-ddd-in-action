package de.ph.example.schedules.infrastructure.storage;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedules.application.Employees;
import de.ph.example.schedules.application.VacationRequests;
import de.ph.example.schedules.domain.PermittedLeave;

class InMemoryEmployees implements Employees {

    private final VacationRequests vacationRequests;

    public InMemoryEmployees(VacationRequests vacationRequests) {
        this.vacationRequests = vacationRequests;
    }

    @Override
    public PermittedLeave calculatePermittedLeave(EmployeeId employeeId) {
        return new PermittedLeave(employeeId, 30);
    }
}
