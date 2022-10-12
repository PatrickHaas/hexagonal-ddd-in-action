package de.ph.example.employees.domain;

import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@AggregateRoot
public class Employee {

    @Getter
    private final EmployeeId id;
    @Getter
    private final FirstName firstName;
    @Getter
    private final LastName lastName;
    @Getter
    private final Birthdate birthdate;
    @Getter
    private HireDate hireDate;
    @Getter
    private FireDate fireDate;
    private Set<DepartmentId> assignedDepartments;

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

    public void assignDepartment(DepartmentId departmentId) {
        if (assignedDepartments == null) {
            assignedDepartments = new HashSet<>();
        }
        this.assignedDepartments.add(departmentId);
    }

    public void hire() {
        this.hireDate = new HireDate(LocalDate.now());
    }

    public void fire() {
        if (hireDate == null) {
            throw new IllegalFiringException(getId());
        }
        this.fireDate = new FireDate(LocalDate.now());
    }

    public Set<DepartmentId> getAssignedDepartments() {
        return assignedDepartments == null ? Collections.emptySet() : Collections.unmodifiableSet(assignedDepartments);
    }
}
