package de.ph.example.employees.domain.model;

import org.jmolecules.ddd.annotation.AggregateRoot;

import java.time.LocalDate;

@AggregateRoot
public class Employee {

    private EmployeeId id;
    private FirstName firstName;
    private LastName lastName;
    private Birthdate birthdate;

    public Employee(EmployeeId id, FirstName firstName, LastName lastName, Birthdate birthdate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
    }

    public FirstName getFirstName() {
        return firstName;
    }

    public LastName getLastName() {
        return lastName;
    }

    public Birthdate getBirthdate() {
        return birthdate;
    }

    public EmployeeId getId() {
        return id;
    }
}
