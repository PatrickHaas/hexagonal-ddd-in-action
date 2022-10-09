package de.ph.example.employees.infrastructure;

import de.ph.example.employees.application.FireEmployee;
import de.ph.example.employees.application.HireEmployee;
import de.ph.example.employees.domain.Employees;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EmployeeConfiguration {
    @Bean
    HireEmployee hireEmployee(Employees employees) {
        return new HireEmployee(employees);
    }

    @Bean
    FireEmployee fireEmployee(Employees employees) {
        return new FireEmployee(employees);
    }
}
