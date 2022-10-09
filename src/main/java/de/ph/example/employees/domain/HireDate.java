package de.ph.example.employees.domain;

import org.jmolecules.ddd.annotation.ValueObject;

import java.time.LocalDate;

@ValueObject
public record HireDate(LocalDate value) {
    public HireDate {
        if (value == null) {
            throw new IllegalArgumentException("hire date must not be empty");
        }
    }

    public static HireDate of(int year, int month, int dayOfMonth) {
        return new HireDate(LocalDate.of(year, month, dayOfMonth));
    }
}
