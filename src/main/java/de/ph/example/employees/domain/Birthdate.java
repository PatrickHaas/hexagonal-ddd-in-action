package de.ph.example.employees.domain;

import org.jmolecules.ddd.annotation.ValueObject;

import java.time.LocalDate;

@ValueObject
public record Birthdate(LocalDate value) {
    public Birthdate {
        if (value == null) {
            throw new IllegalArgumentException("birthdate must not be empty");
        }
    }

    public static Birthdate of(int year, int month, int dayOfMonth) {
        return new Birthdate(LocalDate.of(year, month, dayOfMonth));
    }
}
