package de.ph.example.employees.domain;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public record DepartmentId(String value) {
    // TODO ensure the id is always !empty
    public static DepartmentId random() {
        return new DepartmentId(UUID.randomUUID().toString());
    }

}
