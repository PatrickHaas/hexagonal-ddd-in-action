package de.ph.example.employees.domain.model;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record FirstName(String value) {
    public FirstName {
        if (value == null || value.trim().length() < 2) {
            throw new IllegalArgumentException("first name must be at least 2 characters long");
        }
    }
}
