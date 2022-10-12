package de.ph.example.employees.infrastructure.driven.storage;

import de.ph.example.employees.application.Departments;
import de.ph.example.employees.domain.Department;
import de.ph.example.employees.domain.DepartmentId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemoryDepartments implements Departments {

    private final Map<DepartmentId, Department> departments = new HashMap<>();

    @Override
    public Department save(Department department) {
        DepartmentId departmentId = Optional.ofNullable(department.getId()).orElseGet(DepartmentId::random);
        Department savedDepartment = new Department(departmentId, department.getCode(), department.getName());
        departments.put(savedDepartment.getId(), savedDepartment);
        return savedDepartment;
    }

    @Override
    public Optional<Department> findById(DepartmentId id) {
        return Optional.ofNullable(departments.get(id));
    }

}
