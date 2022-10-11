package de.ph.example.schedules.application;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedules.domain.VacationRequest;
import de.ph.example.schedules.domain.VacationRequestId;

import java.util.List;
import java.util.Optional;

public interface VacationRequestRepository {
    VacationRequest save(VacationRequest vacationRequest);

    List<VacationRequest> findByEmployeeIdAndYear(EmployeeId employeeId, int... years);

    Optional<VacationRequest> findById(VacationRequestId id);
}
