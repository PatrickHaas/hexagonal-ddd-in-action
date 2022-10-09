package de.ph.example.schedule.infrastructure.storage;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedule.application.EmployeeRepository;
import de.ph.example.schedule.application.VacationRequestRepository;

class InMemoryEmployeeRepository implements EmployeeRepository {

    private final VacationRequestRepository vacationRequestRepository;

    public InMemoryEmployeeRepository(VacationRequestRepository vacationRequestRepository) {
        this.vacationRequestRepository = vacationRequestRepository;
    }

    @Override
    public int calculateLeftOverVacationDays(EmployeeId employeeId, int year) {
        // TODO calculate left over vacation days
        return 30;
    }
}
