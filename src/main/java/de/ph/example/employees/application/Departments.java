package de.ph.example.employees.application;

import de.ph.example.employees.domain.Department;
import de.ph.example.employees.domain.DepartmentId;

import java.util.Optional;

public interface Departments {
    Department save(Department department);

    Optional<Department> findById(DepartmentId departmentId);
}
