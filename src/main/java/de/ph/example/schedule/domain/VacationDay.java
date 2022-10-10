package de.ph.example.schedule.domain;

import org.jmolecules.ddd.annotation.ValueObject;

import java.time.LocalDate;

@ValueObject
public record VacationDay(LocalDate value) {

    public VacationDay {
        if (value == null) {
            throw new IllegalArgumentException("vacation day needs a date");
        }
    }

}
