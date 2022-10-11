package de.ph.example.schedules.domain;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public enum VacationRequestStatus {
    CREATED, APPROVED, DECLINED
}
