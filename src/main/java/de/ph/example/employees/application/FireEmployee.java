package de.ph.example.employees.application;

import de.ph.example.employees.domain.Employee;
import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.employees.domain.EmployeeNotFoundException;
import org.jmolecules.ddd.annotation.Service;

@Service
public class FireEmployee {
    private final Employees employees;

    public FireEmployee(Employees employees) {
        this.employees = employees;
    }

    public Employee with(EmployeeId id) {
        Employee employee = employees.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        employee.fire();
        return employees.save(employee);
    }
}
