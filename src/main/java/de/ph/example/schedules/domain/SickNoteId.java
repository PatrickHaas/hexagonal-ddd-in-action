package de.ph.example.schedules.domain;

import java.util.UUID;

public record SickNoteId(String value) {
    // TODO ensure the id is always !empty
    public static SickNoteId random() {
        return new SickNoteId(UUID.randomUUID().toString());
    }
}
