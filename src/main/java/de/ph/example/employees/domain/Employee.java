package de.ph.example.employees.domain;

import org.jmolecules.ddd.annotation.AggregateRoot;

import java.time.LocalDate;

@AggregateRoot
public class Employee {

    private final EmployeeId id;
    private final FirstName firstName;
    private final LastName lastName;
    private final Birthdate birthdate;
    private HireDate hireDate;
    private FireDate fireDate;

    public Employee(EmployeeId id, FirstName firstName, LastName lastName, Birthdate birthdate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
    }

    public Employee(EmployeeId id, FirstName firstName, LastName lastName, Birthdate birthdate, HireDate hireDate, FireDate fireDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.hireDate = hireDate;
        this.fireDate = fireDate;
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
        this.hireDate = new HireDate(LocalDate.now());
    }

    public HireDate getHireDate() {
        return hireDate;
    }

    public void fire() {
        if (hireDate == null) {
            throw new IllegalFiringException(this);
        }
        this.fireDate = new FireDate(LocalDate.now());
    }

    public FireDate getFireDate() {
        return fireDate;
    }
}
