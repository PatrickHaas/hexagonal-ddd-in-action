package de.ph.example.employees.application;

import de.ph.example.employees.domain.Employee;
import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.employees.domain.Employees;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryEmployees implements Employees {

    private final Map<EmployeeId, Employee> employees = new HashMap<>();

    @Override
    public Employee save(Employee employee) {
        EmployeeId employeeId = Optional.ofNullable(employee.getId()).orElseGet(EmployeeId::random);
        Employee savedEmployee = new Employee(employeeId, employee.getFirstName(), employee.getLastName(), employee.getBirthdate(), employee.getHiredOn(), employee.getFiredOn());
        employees.put(savedEmployee.getId(), savedEmployee);
        return savedEmployee;
    }

    @Override
    public Optional<Employee> findById(EmployeeId id) {
        return Optional.ofNullable(employees.get(id));
    }
}
