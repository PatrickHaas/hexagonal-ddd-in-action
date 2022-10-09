package de.ph.example.employees.domain;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public record EmployeeId(String value) {
    public static EmployeeId random() {
        return new EmployeeId(UUID.randomUUID().toString());
    }

}
