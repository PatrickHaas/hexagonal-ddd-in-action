package de.ph.example.projects.domain;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public record ProjectId(String value) {
    // TODO ensure the id is always !empty
    public static ProjectId random() {
        return new ProjectId(UUID.randomUUID().toString());
    }

}
