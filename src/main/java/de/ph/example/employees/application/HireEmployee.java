package de.ph.example.employees.application;

import de.ph.example.employees.domain.*;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.annotation.Service;

@Service
@RequiredArgsConstructor
public class HireEmployee {

    private final Employees employees;
    private final ApplicationEvents applicationEvents;

    public Employee with(FirstName firstName, LastName lastName, Birthdate birthdate) {
        return with(null, firstName, lastName, birthdate);
    }

    public Employee with(EmployeeId id, FirstName firstName, LastName lastName, Birthdate birthdate) {
        Employee employee = new Employee(id, firstName, lastName, birthdate);
        employee.hire();
        Employee savedEmployee = employees.save(employee);
        applicationEvents.employeeHired(new EmployeeHired(savedEmployee.getId(), savedEmployee.getFirstName(), savedEmployee.getLastName()));
        return savedEmployee;
    }
}
