package de.ph.example.projects.domain;

import org.jmolecules.ddd.annotation.ValueObject;

import java.time.LocalDate;

@ValueObject
public record AcquireDate(LocalDate date) {
}
