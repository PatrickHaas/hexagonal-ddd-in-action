package de.ph.example.employees.infrastructure.storage;

import de.ph.example.employees.domain.Employee;
import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.employees.domain.Employees;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryEmployees implements Employees {

    private final Map<EmployeeId, Employee> employees = new HashMap<>();

    @Override
    public Mono<Employee> save(Employee employee) {
        EmployeeId employeeId = Optional.ofNullable(employee.getId()).orElseGet(EmployeeId::random);
        Employee savedEmployee = new Employee(employeeId, employee.getFirstName(), employee.getLastName(), employee.getBirthdate(), employee.getHiredOn(), employee.getFiredOn());
        employees.put(savedEmployee.getId(), savedEmployee);
        return Mono.just(savedEmployee);
    }

    @Override
    public Mono<Employee> findById(EmployeeId id) {
        return Mono.justOrEmpty(employees.get(id));
    }
}
