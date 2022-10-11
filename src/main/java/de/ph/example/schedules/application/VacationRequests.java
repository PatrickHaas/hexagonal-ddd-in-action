package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.EmployeeId;
import de.ph.example.schedules.domain.VacationRequest;
import de.ph.example.schedules.domain.VacationRequestId;
import org.jmolecules.ddd.annotation.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VacationRequests {
    VacationRequest save(VacationRequest vacationRequest);

    Optional<VacationRequest> findById(VacationRequestId id);

    List<VacationRequest> findByEmployeeIdAndYear(EmployeeId employeeId, int year);
}
