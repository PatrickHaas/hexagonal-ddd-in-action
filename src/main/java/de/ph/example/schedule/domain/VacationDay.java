package de.ph.example.schedule.domain;

import org.jmolecules.ddd.annotation.ValueObject;

import java.time.LocalDate;

@ValueObject
public record VacationDay(LocalDate value) {
}
