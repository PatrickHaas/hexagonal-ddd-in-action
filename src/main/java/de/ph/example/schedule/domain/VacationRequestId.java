package de.ph.example.schedule.domain;

import java.util.UUID;

public record VacationRequestId(String value) {
    // TODO ensure the id is always !empty
    public static VacationRequestId random() {
        return new VacationRequestId(UUID.randomUUID().toString());
    }
}
