package de.ph.example.employees.domain;

import org.jmolecules.ddd.annotation.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface Employees {
    Mono<Employee> save(Employee employee);

    Mono<Employee> findById(EmployeeId id);
}
