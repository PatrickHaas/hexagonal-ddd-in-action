package de.ph.example.employees.domain;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record DepartmentName(String value) {
}
