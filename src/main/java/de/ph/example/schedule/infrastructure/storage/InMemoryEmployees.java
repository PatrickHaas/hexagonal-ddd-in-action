package de.ph.example.schedule.infrastructure.storage;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedule.domain.Employees;
import de.ph.example.schedule.domain.VacationRequests;

class InMemoryEmployees implements Employees {

    private final VacationRequests vacationRequestRepository;

    public InMemoryEmployees(VacationRequests vacationRequestRepository) {
        this.vacationRequestRepository = vacationRequestRepository;
    }

    @Override
    public int calculateLeftOverVacationDays(EmployeeId employeeId, int year) {
        // TODO calculate left over vacation days
        return 30;
    }
}
