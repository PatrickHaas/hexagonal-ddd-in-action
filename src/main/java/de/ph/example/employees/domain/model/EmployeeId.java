package de.ph.example.employees.domain.model;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public class EmployeeId {

   public static EmployeeId random() {
       return new EmployeeId(UUID.randomUUID());
   }

    private final UUID value;

    public EmployeeId(UUID value) {
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }
}
