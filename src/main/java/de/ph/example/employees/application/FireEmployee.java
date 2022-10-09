package de.ph.example.employees.application;

import de.ph.example.employees.domain.Employee;
import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.employees.domain.Employees;
import org.jmolecules.ddd.annotation.Service;
import reactor.core.publisher.Mono;

@Service
public class FireEmployee {
    private final Employees employees;

    public FireEmployee(Employees employees) {
        this.employees = employees;
    }

    public Mono<Employee> with(EmployeeId id) {
        return employees.findById(id)
                .doOnNext(Employee::fire)
                .flatMap(employees::save);
    }
}
