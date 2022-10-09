package de.ph.example.employees.domain;

import org.jmolecules.ddd.annotation.ValueObject;

import java.time.LocalDate;

@ValueObject
public record FireDate(LocalDate value) {
    public FireDate {
        if (value == null) {
            throw new IllegalArgumentException("fire date must not be empty");
        }
    }

    public static FireDate of(int year, int month, int dayOfMonth) {
        return new FireDate(LocalDate.of(year, month, dayOfMonth));
    }
}
