package de.ph.example.schedules.domain;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public record ProjectAssignmentId(String value) {
    // TODO ensure the id is always !empty
    public static ProjectAssignmentId random() {
        return new ProjectAssignmentId(UUID.randomUUID().toString());
    }

}
