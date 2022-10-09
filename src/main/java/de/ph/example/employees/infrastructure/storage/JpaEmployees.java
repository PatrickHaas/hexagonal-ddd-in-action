package de.ph.example.employees.infrastructure.storage;

import de.ph.example.employees.domain.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

class JpaEmployees implements Employees {

    private final ReactiveEmployeeRepository employeeRepository;

    public JpaEmployees(ReactiveEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Mono<Employee> save(Employee employee) {
        return employeeRepository.save(toEntity(employee))
                .map(this::fromEntity);
    }

    @Override
    public Mono<Employee> findById(EmployeeId id) {
        return employeeRepository.findById(id.value())
                .map(this::fromEntity);
    }

    EmployeeEntity toEntity(Employee employee) {
        return new EmployeeEntity(Optional.ofNullable(employee.getId())
                .map(EmployeeId::value)
                .orElse(null),
                employee.getFirstName().value(),
                employee.getLastName().value(),
                employee.getBirthdate().value(),
                employee.getHiredOn(),
                employee.getFiredOn());
    }

    Employee fromEntity(EmployeeEntity employeeEntity) {
        return new Employee(new EmployeeId(employeeEntity.id()),
                new FirstName(employeeEntity.firstName()),
                new LastName(employeeEntity.lastName()),
                new Birthdate(employeeEntity.birthdate()),
                employeeEntity.hiredOn(),
                employeeEntity.firedOn());
    }
}
