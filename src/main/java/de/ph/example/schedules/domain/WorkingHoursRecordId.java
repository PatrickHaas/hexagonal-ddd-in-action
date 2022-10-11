package de.ph.example.schedules.domain;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public record WorkingHoursRecordId(String value) {
    // TODO ensure the id is always !empty
    public static WorkingHoursRecordId random() {
        return new WorkingHoursRecordId(UUID.randomUUID().toString());
    }

}
