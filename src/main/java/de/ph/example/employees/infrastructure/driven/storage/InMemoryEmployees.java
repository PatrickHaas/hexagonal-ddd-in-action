package de.ph.example.employees.infrastructure.driven.storage;

import de.ph.example.employees.application.Employees;
import de.ph.example.employees.domain.Employee;
import de.ph.example.employees.domain.EmployeeId;

import java.util.*;

public class InMemoryEmployees implements Employees {

    private final Map<EmployeeId, Employee> employees = new HashMap<>();

    @Override
    public Employee save(Employee employee) {
        EmployeeId employeeId = Optional.ofNullable(employee.getId()).orElseGet(EmployeeId::random);
        Employee savedEmployee = new Employee(employeeId, employee.getFirstName(), employee.getLastName(), employee.getBirthdate(), employee.getHireDate(), employee.getFireDate());
        employees.put(savedEmployee.getId(), savedEmployee);
        return savedEmployee;
    }

    @Override
    public Optional<Employee> findById(EmployeeId id) {
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }
}
