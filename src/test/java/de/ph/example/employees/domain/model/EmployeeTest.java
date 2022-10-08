package de.ph.example.employees.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeTest {

    @Test
    void creation() {
        var employee = new Employee(EmployeeId.random(), new FirstName("Patrick"), new LastName("Haas"), new Birthdate(LocalDate.of(1987, 3, 9)));
        assertThat(employee.getId()).isNotNull();
        assertThat(employee.getFirstName().value()).isEqualTo("Patrick");
        assertThat(employee.getLastName().value()).isEqualTo("Haas");
        assertThat(employee.getBirthdate().value()).isEqualTo(LocalDate.of(1987, 3, 9));
    }

}
