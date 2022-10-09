package de.ph.example.schedule.domain;

import reactor.core.publisher.Mono;

public interface VacationRequests {
    Mono<VacationRequest> save(VacationRequest vacationRequest);
}
