package de.ph.example.employees.application;

import de.ph.example.employees.domain.*;
import org.jmolecules.ddd.annotation.Service;

@Service
public class HireEmployee {

    private final Employees employees;

    public HireEmployee(Employees employees) {
        this.employees = employees;
    }

    public Employee with(FirstName firstName, LastName lastName, Birthdate birthdate) {
        return with(null, firstName, lastName, birthdate);
    }

    public Employee with(EmployeeId id, FirstName firstName, LastName lastName, Birthdate birthdate) {
        Employee employee = new Employee(id, firstName, lastName, birthdate);
        employee.hire();
        return employees.save(employee);
    }
}
