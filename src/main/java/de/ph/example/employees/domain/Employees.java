package de.ph.example.employees.domain;

import java.util.Optional;

public interface Employees {
    Employee save(Employee employee);

    Optional<Employee> findById(EmployeeId id);
}
