package de.ph.example.employees.application;

import de.ph.example.employees.domain.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class HireEmployeeTest {

    @Test
    void hireEmployee_shouldHireAndSaveTheEmployeeWithTheGivenId_whenGivenIdIsNotNull() {
        InMemoryEmployees employees = new InMemoryEmployees();
        HireEmployee hireEmployee = new HireEmployee(employees);
        EmployeeId employeeId = EmployeeId.random();
        Employee employee = hireEmployee.with(employeeId, new FirstName("Steve"), new LastName("Rogers"), new Birthdate(LocalDate.of(1918, 7, 4)));
        assertThat(employee.getId()).isEqualTo(employeeId);
    }

    @Test
    void hireEmployee_shouldHireAndSaveTheEmployeeWithARandomId_whenGivenIdIsNull() {
        InMemoryEmployees employees = new InMemoryEmployees();
        HireEmployee hireEmployee = new HireEmployee(employees);
        Employee employee = hireEmployee.with(new FirstName("Steve"), new LastName("Rogers"), new Birthdate(LocalDate.of(1918, 7, 4)));
        assertThat(employee.getId()).isNotNull();
    }
}
