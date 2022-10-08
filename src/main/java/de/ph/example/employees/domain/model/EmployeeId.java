package de.ph.example.employees.domain.model;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public record EmployeeId(UUID value) {
    public static EmployeeId random() {
        return new EmployeeId(UUID.randomUUID());
    }

}
