package de.ph.example.schedules.domain;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public record EmployeeId(String value) {
    // TODO ensure the id is always !empty
    public static EmployeeId random() {
        return new EmployeeId(UUID.randomUUID().toString());
    }

}
