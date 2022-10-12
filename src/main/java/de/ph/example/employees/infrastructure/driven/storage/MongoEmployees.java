package de.ph.example.employees.infrastructure.driven.storage;

import de.ph.example.employees.application.Employees;
import de.ph.example.employees.domain.*;

import java.util.List;
import java.util.Optional;

class MongoEmployees implements Employees {

    private final SpringMongoEmployeeRepository employeeRepository;

    public MongoEmployees(SpringMongoEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        return fromEntity(employeeRepository.save(toEntity(employee)));
    }

    @Override
    public Optional<Employee> findById(EmployeeId id) {
        return employeeRepository.findById(id.value())
                .map(this::fromEntity);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll().stream().map(this::fromEntity).toList();
    }

    EmployeeEntity toEntity(Employee employee) {
        return new EmployeeEntity(Optional.ofNullable(employee.getId())
                .map(EmployeeId::value)
                .orElse(null),
                employee.getFirstName().value(),
                employee.getLastName().value(),
                employee.getBirthdate().value(),
                Optional.ofNullable(employee.getHireDate())
                        .map(HireDate::value)
                        .orElse(null),
                Optional.ofNullable(employee.getFireDate())
                        .map(FireDate::value)
                        .orElse(null));
    }

    Employee fromEntity(EmployeeEntity employeeEntity) {
        return new Employee(new EmployeeId(employeeEntity.id()),
                new FirstName(employeeEntity.firstName()),
                new LastName(employeeEntity.lastName()),
                new Birthdate(employeeEntity.birthdate()),
                Optional.ofNullable(employeeEntity.hireDate())
                        .map(HireDate::new)
                        .orElse(null),
                Optional.ofNullable(employeeEntity.fireDate())
                        .map(FireDate::new)
                        .orElse(null));
    }
}
