package de.ph.example.schedule.domain;

import java.util.UUID;

public record VacationRequestId(String value) {
    public static VacationRequestId random() {
        return new VacationRequestId(UUID.randomUUID().toString());
    }
}
