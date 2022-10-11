package de.ph.example.employees.application;

import de.ph.example.employees.domain.Employee;
import de.ph.example.employees.domain.EmployeeId;
import org.jmolecules.ddd.annotation.Repository;

import java.util.Optional;

@Repository
public interface Employees {
    Employee save(Employee employee);

    Optional<Employee> findById(EmployeeId id);
}
