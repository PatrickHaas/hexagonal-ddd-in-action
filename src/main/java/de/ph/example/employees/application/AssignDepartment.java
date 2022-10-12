package de.ph.example.employees.application;

import de.ph.example.employees.domain.*;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.annotation.Service;

@Service
@RequiredArgsConstructor
public class AssignDepartment {

    private final Employees employees;
    private final Departments departments;

    public Employee with(EmployeeId employeeId, DepartmentId departmentId) {
        Employee employee = employees.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        departments.findById(departmentId).orElseThrow(() -> new DepartmentNotFoundException(departmentId));
        employee.assignDepartment(departmentId);
        return employees.save(employee);
    }
}
