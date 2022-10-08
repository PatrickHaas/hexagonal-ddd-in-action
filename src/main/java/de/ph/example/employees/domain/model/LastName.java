package de.ph.example.employees.domain.model;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record LastName(String value) {
    public LastName {
        if (value == null || value.length() < 4) {
            throw new IllegalArgumentException("last name must be at least 2 characters long");
        }
    }
}
