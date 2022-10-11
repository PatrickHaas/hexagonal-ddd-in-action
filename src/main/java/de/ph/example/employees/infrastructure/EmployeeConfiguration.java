package de.ph.example.employees.infrastructure;

import de.ph.example.employees.application.ApplicationEvents;
import de.ph.example.employees.application.Employees;
import de.ph.example.employees.application.FireEmployee;
import de.ph.example.employees.application.HireEmployee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EmployeeConfiguration {
    @Bean
    HireEmployee hireEmployee(Employees employees, ApplicationEvents applicationEvents) {
        return new HireEmployee(employees, applicationEvents);
    }

    @Bean
    FireEmployee fireEmployee(Employees employees, ApplicationEvents applicationEvents) {
        return new FireEmployee(employees, applicationEvents);
    }
}
