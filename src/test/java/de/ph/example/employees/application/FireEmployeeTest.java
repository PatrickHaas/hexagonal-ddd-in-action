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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FireEmployeeTest {

    @Mock
    private Employees employees;

    @Mock
    private ApplicationEvents applicationEvents;

    @InjectMocks
    private FireEmployee fireEmployee;

    @Test
    void fireEmployee_shouldFireAndSaveTheEmployee() {
        EmployeeId employeeId = EmployeeId.random();
        Employee employee = new Employee(employeeId, new FirstName("Tony"), new LastName("Stark"), new Birthdate(LocalDate.of(1970, 5, 29)));
        employee.hire();
        when(employees.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employees.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0, Employee.class));
        Employee firedEmployee = fireEmployee.with(employeeId);
        assertThat(firedEmployee.getFireDate().value()).isEqualTo(LocalDate.now());
        verify(applicationEvents).employeeFired(new EmployeeFired(employeeId, firedEmployee.getFirstName(), firedEmployee.getLastName()));
    }

}
