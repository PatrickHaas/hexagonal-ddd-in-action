package de.ph.example.schedules.application;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedules.domain.VacationRequest;

import java.util.List;

public interface VacationRequestRepository {
    VacationRequest save(VacationRequest vacationRequest);

    List<VacationRequest> findByEmployeeIdAndYear(EmployeeId employeeId, int... years);
}
