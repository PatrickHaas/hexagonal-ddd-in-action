package de.ph.example.employees.application;

import de.ph.example.employees.domain.Employee;
import de.ph.example.employees.domain.EmployeeFired;
import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.employees.domain.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.annotation.Service;

@Service
@RequiredArgsConstructor
public class FireEmployee {
    private final Employees employees;
    private final EmployeeApplicationEvents employeeApplicationEvents;

    public Employee with(EmployeeId id) {
        Employee employee = employees.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        employee.fire();
        Employee firedEmployee = employees.save(employee);
        employeeApplicationEvents.employeeFired(new EmployeeFired(id, firedEmployee.getFirstName(), firedEmployee.getLastName()));
        return firedEmployee;
    }
}
