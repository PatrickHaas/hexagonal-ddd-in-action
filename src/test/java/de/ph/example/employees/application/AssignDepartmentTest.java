package de.ph.example.employees.application;

import de.ph.example.employees.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssignDepartmentTest {

    @Mock
    private Employees employees;

    @Mock
    private Departments departments;

    @InjectMocks
    private AssignDepartment assignDepartment;

    @Test
    void assignDepartment_shouldAssignDepartmentAndSaveEmployee() {
        EmployeeId employeeId = EmployeeId.random();
        when(employees.findById(employeeId))
                .thenReturn(Optional.of(
                        new Employee(employeeId,
                                new FirstName("Steve"),
                                new LastName("Rogers"),
                                new Birthdate(LocalDate.of(1918, 7, 4)))
                ));

        DepartmentId departmentId = DepartmentId.random();
        when(departments.findById(departmentId))
                .thenReturn(Optional.of(
                        new Department(departmentId,
                                new DepartmentCode("VNGRS"),
                                new DepartmentName("Marvel Avengers"))
                ));

        when(employees.save(Mockito.any()))
                .thenAnswer(invocation -> invocation.getArgument(0, Employee.class));

        Employee employee = assignDepartment.with(employeeId, departmentId);
        assertThat(employee.getAssignedDepartments()).containsExactly(departmentId);
    }

    @Test
    void assignDepartment_shouldFail_whenEmployeeCouldNotBeFound() {
        EmployeeId employeeId = EmployeeId.random();
        when(employees.findById(employeeId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> assignDepartment.with(employeeId, DepartmentId.random()))
                .isInstanceOf(EmployeeNotFoundException.class);
    }

    @Test
    void assignDepartment_shouldFail_whenDepartmentCouldNotBeFound() {
        EmployeeId employeeId = EmployeeId.random();
        when(employees.findById(employeeId))
                .thenReturn(Optional.of(
                        new Employee(employeeId,
                                new FirstName("Steve"),
                                new LastName("Rogers"),
                                new Birthdate(LocalDate.of(1918, 7, 4)))
                ));

        DepartmentId departmentId = DepartmentId.random();
        when(departments.findById(departmentId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> assignDepartment.with(employeeId, departmentId))
                .isInstanceOf(DepartmentNotFoundException.class);
    }

}
