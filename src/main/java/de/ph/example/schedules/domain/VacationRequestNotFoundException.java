package de.ph.example.schedules.domain;

public class VacationRequestNotFoundException extends RuntimeException {
    public VacationRequestNotFoundException(VacationRequestId id) {
        super("a vacation request with the id %s could not be found".formatted(id == null ? null : id.value()));

    }
}
