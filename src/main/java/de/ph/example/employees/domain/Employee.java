package de.ph.example.employees.domain;

import org.jmolecules.ddd.annotation.AggregateRoot;
import org.slf4j.spi.LocationAwareLogger;

import java.time.LocalDate;

@AggregateRoot
public class Employee {

    private final EmployeeId id;
    private final FirstName firstName;
    private final LastName lastName;
    private final Birthdate birthdate;
    private LocalDate hiredOn;
    private LocalDate firedOn;

    public Employee(EmployeeId id, FirstName firstName, LastName lastName, Birthdate birthdate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
    }

    public Employee(EmployeeId id, FirstName firstName, LastName lastName, Birthdate birthdate, LocalDate hiredOn, LocalDate firedOn) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.hiredOn = hiredOn;
        this.firedOn = firedOn;
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

    public void hire() {
        this.hiredOn = LocalDate.now();
    }

    public LocalDate getHiredOn() {
        return hiredOn;
    }

    public void fire() {
        if (hiredOn == null) {
            throw new IllegalFiringException(this);
        }
        this.firedOn = LocalDate.now();
    }

    public LocalDate getFiredOn() {
        return firedOn;
    }
}
