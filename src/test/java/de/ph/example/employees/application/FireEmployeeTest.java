package de.ph.example.employees.application;

import de.ph.example.employees.domain.*;
import de.ph.example.employees.infrastructure.storage.InMemoryEmployees;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class FireEmployeeTest {

    @Test
    void fireEmployee_shouldFireAndSaveTheEmployee() {
        InMemoryEmployees employees = new InMemoryEmployees();
        Employee employee = new Employee(EmployeeId.random(), new FirstName("Tony"), new LastName("Stark"), new Birthdate(LocalDate.of(1970, 5, 29)));
        employee.hire();
        Employee savedEmployee = employees.save(employee);
        assertThat(savedEmployee.getHireDate().value()).isEqualTo(LocalDate.now());
        FireEmployee fireEmployee = new FireEmployee(employees);
        fireEmployee.with(savedEmployee.getId());
        Employee firedEmployee = employees.findById(savedEmployee.getId()).orElseThrow();
        assertThat(firedEmployee.getFireDate().value()).isEqualTo(LocalDate.now());
    }

}
