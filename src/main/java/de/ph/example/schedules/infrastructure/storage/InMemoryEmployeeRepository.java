package de.ph.example.schedules.infrastructure.storage;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedules.application.EmployeeRepository;
import de.ph.example.schedules.application.VacationRequestRepository;
import de.ph.example.schedules.domain.PermittedLeave;

class InMemoryEmployeeRepository implements EmployeeRepository {

    private final VacationRequestRepository vacationRequestRepository;

    public InMemoryEmployeeRepository(VacationRequestRepository vacationRequestRepository) {
        this.vacationRequestRepository = vacationRequestRepository;
    }

    @Override
    public PermittedLeave calculatePermittedLeave(EmployeeId employeeId) {
        return new PermittedLeave(employeeId, 30);
    }
}
