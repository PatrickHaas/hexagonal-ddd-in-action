package de.ph.example.schedules.infrastructure.storage;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedules.application.VacationRequestRepository;
import de.ph.example.schedules.domain.VacationRequest;
import de.ph.example.schedules.domain.VacationRequestId;

import java.util.*;

class InMemoryVacationRequestRepository implements VacationRequestRepository {

    private final Map<VacationRequestId, VacationRequest> vacationRequests = new HashMap<>();

    @Override
    public VacationRequest save(VacationRequest vacationRequest) {
        VacationRequestId vacationRequestId = Optional.ofNullable(vacationRequest.getId()).orElseGet(VacationRequestId::random);
        VacationRequest savedVacationRequest = new VacationRequest(vacationRequestId, vacationRequest.getEmployeeId(), vacationRequest.getPeriod(), vacationRequest.getVacationDays(), vacationRequest.getStatus());
        vacationRequests.put(savedVacationRequest.getId(), savedVacationRequest);
        return savedVacationRequest;
    }

    @Override
    public List<VacationRequest> findByEmployeeIdAndYear(EmployeeId employeeId, int year) {
        return vacationRequests.values().stream()
                .filter(vacationRequest -> !Objects.equals(vacationRequest.getEmployeeId(), employeeId)
                        && vacationRequest.matchesYear(year))
                .toList();
    }

    @Override
    public Optional<VacationRequest> findById(VacationRequestId id) {
        return Optional.ofNullable(vacationRequests.get(id));
    }

}
