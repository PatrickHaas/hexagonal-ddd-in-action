package de.ph.example.employees.application;

import de.ph.example.employees.domain.*;
import org.jmolecules.ddd.annotation.Service;
import reactor.core.publisher.Mono;

@Service
public class HireEmployee {

    private final Employees employees;

    public HireEmployee(Employees employees) {
        this.employees = employees;
    }

    public Mono<Employee> with(FirstName firstName, LastName lastName, Birthdate birthdate) {
        return with(null, firstName, lastName, birthdate);
    }

    public Mono<Employee> with(EmployeeId id, FirstName firstName, LastName lastName, Birthdate birthdate) {
        return Mono.just(new Employee(id, firstName, lastName, birthdate))
                .doOnNext(Employee::hire)
                .flatMap(employees::save);
    }
}
