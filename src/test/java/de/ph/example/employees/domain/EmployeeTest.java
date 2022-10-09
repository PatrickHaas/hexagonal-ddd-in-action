package de.ph.example.employees.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EmployeeTest {

    @Test
    void creation() {
        var employee = new Employee(EmployeeId.random(), new FirstName("Patrick"), new LastName("Haas"), new Birthdate(LocalDate.of(1987, 3, 9)));
        assertThat(employee.getId()).isNotNull();
        assertThat(employee.getFirstName().value()).isEqualTo("Patrick");
        assertThat(employee.getLastName().value()).isEqualTo("Haas");
        assertThat(employee.getBirthdate().value()).isEqualTo(LocalDate.of(1987, 3, 9));
    }

    @Test
    void hire_shouldSetHiringDate() {
        var employee = new Employee(EmployeeId.random(), new FirstName("Patrick"), new LastName("Haas"), new Birthdate(LocalDate.of(1987, 3, 9)));
        employee.hire();
        assertThat(employee.getHireDate().value()).isEqualTo(LocalDate.now());
    }

    @Test
    void creation_shouldFail_whenLastNameIsNotAtLeast2CharactersLong() {
        assertThatThrownBy(() -> new Employee(EmployeeId.random(), new FirstName("Patrick"), new LastName(null), new Birthdate(LocalDate.of(1987, 3, 9))))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Employee(EmployeeId.random(), new FirstName("Patrick"), new LastName("H"), new Birthdate(LocalDate.of(1987, 3, 9))))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Employee(EmployeeId.random(), new FirstName("Patrick"), new LastName("Ha"), new Birthdate(LocalDate.of(1987, 3, 9))))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Employee(EmployeeId.random(), new FirstName("Patrick"), new LastName("Ha "), new Birthdate(LocalDate.of(1987, 3, 9))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void creation_shouldFail_whenFirstNameIsNotAtLeast2CharactersLong() {
        assertThatThrownBy(() -> new Employee(EmployeeId.random(), new FirstName(null), new LastName("Haas"), new Birthdate(LocalDate.of(1987, 3, 9))))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Employee(EmployeeId.random(), new FirstName("P"), new LastName("Haas"), new Birthdate(LocalDate.of(1987, 3, 9))))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Employee(EmployeeId.random(), new FirstName("P "), new LastName("Haas"), new Birthdate(LocalDate.of(1987, 3, 9))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void creation_shouldFail_whenBirthdateIsNotSet() {
        assertThatThrownBy(() -> new Employee(EmployeeId.random(), new FirstName("Patrick"), new LastName("Haas"), new Birthdate(null)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void fire_shouldSetFiringDate() {
        var employee = new Employee(EmployeeId.random(), new FirstName("Patrick"), new LastName("Haas"), new Birthdate(LocalDate.of(1987, 3, 9)));
        employee.hire();
        employee.fire();
        assertThat(employee.getFireDate().value()).isEqualTo(LocalDate.now());
    }

    @Test
    void fire_shouldFail_whenEmployeeNotHiredYet() {
        var employee = new Employee(EmployeeId.random(), new FirstName("Patrick"), new LastName("Haas"), new Birthdate(LocalDate.of(1987, 3, 9)));
        assertThatThrownBy(employee::fire)
                .isInstanceOf(IllegalFiringException.class);
    }
}
