package de.ph.example.employees.application;

import de.ph.example.employees.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HireEmployeeTest {

    @Mock
    private Employees employees;

    @Mock
    private ApplicationEvents applicationEvents;

    @InjectMocks
    private HireEmployee hireEmployee;

    @Test
    void hireEmployee_shouldHireAndSaveTheEmployeeWithTheGivenId_whenGivenIdIsNotNull() {
        EmployeeId employeeId = EmployeeId.random();
        when(employees.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0, Employee.class));
        Employee hiredEmployee = hireEmployee.with(employeeId, new FirstName("Steve"), new LastName("Rogers"), new Birthdate(LocalDate.of(1918, 7, 4)));
        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employees).save(employeeArgumentCaptor.capture());
        assertThat(employeeArgumentCaptor.getValue().getId()).isEqualTo(employeeId);
        assertThat(employeeArgumentCaptor.getValue().getFirstName()).isEqualTo(new FirstName("Steve"));
        assertThat(employeeArgumentCaptor.getValue().getLastName()).isEqualTo(new LastName("Rogers"));
        assertThat(employeeArgumentCaptor.getValue().getBirthdate()).isEqualTo(new Birthdate(LocalDate.of(1918, 7, 4)));
        verify(applicationEvents).employeeHired(new EmployeeHired(employeeId, hiredEmployee.getFirstName(), hiredEmployee.getLastName()));
    }

    @Test
    void hireEmployee_shouldHireAndSaveTheEmployeeWithARandomId_whenGivenIdIsNull() {
        when(employees.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0, Employee.class));
        Employee hiredEmployee = hireEmployee.with(new FirstName("Steve"), new LastName("Rogers"), new Birthdate(LocalDate.of(1918, 7, 4)));
        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employees).save(employeeArgumentCaptor.capture());
        assertThat(employeeArgumentCaptor.getValue().getId()).isNull();
        assertThat(employeeArgumentCaptor.getValue().getFirstName()).isEqualTo(new FirstName("Steve"));
        assertThat(employeeArgumentCaptor.getValue().getLastName()).isEqualTo(new LastName("Rogers"));
        assertThat(employeeArgumentCaptor.getValue().getBirthdate()).isEqualTo(new Birthdate(LocalDate.of(1918, 7, 4)));
        verify(applicationEvents).employeeHired(new EmployeeHired(hiredEmployee.getId(), hiredEmployee.getFirstName(), hiredEmployee.getLastName()));
    }
}
