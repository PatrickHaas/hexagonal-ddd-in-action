package de.ph.example.employees.domain;

import reactor.core.publisher.Mono;

import java.util.Optional;

public interface Employees {
    Mono<Employee> save(Employee employee);

    Mono<Employee> findById(EmployeeId id);
}
