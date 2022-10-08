package de.ph.example.employees.application;

import de.ph.example.employees.domain.Employee;
import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.employees.domain.Employees;

public class FireEmployee {
    private Employees employees;

    public FireEmployee(Employees employees) {
        this.employees = employees;
    }

    public void with(EmployeeId id) {
        Employee employee = employees.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        employee.fire();
        employees.save(employee);
    }
}
