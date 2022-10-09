package de.ph.example.schedule.infrastructure.storage;

import de.ph.example.schedule.domain.VacationRequest;
import de.ph.example.schedule.domain.VacationRequestId;
import de.ph.example.schedule.domain.VacationRequests;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemoryVacationRequests implements VacationRequests {

    private final Map<VacationRequestId, VacationRequest> vacationRequests = new HashMap<>();

    @Override
    public Mono<VacationRequest> save(VacationRequest vacationRequest) {
        VacationRequestId vacationRequestId = Optional.ofNullable(vacationRequest.getId()).orElseGet(VacationRequestId::random);
        VacationRequest savedVacationRequest = new VacationRequest(vacationRequestId, vacationRequest.getEmployeeId(), vacationRequest.getSpan(), vacationRequest.getVacationDays(), vacationRequest.getStatus());
        vacationRequests.put(savedVacationRequest.getId(), savedVacationRequest);
        return Mono.just(savedVacationRequest);
    }
}
