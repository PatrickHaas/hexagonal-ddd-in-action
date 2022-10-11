package de.ph.example.employees.infrastructure;

import de.ph.example.employees.application.EmployeeRepository;
import de.ph.example.employees.application.FireEmployee;
import de.ph.example.employees.application.HireEmployee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EmployeeConfiguration {
    @Bean
    HireEmployee hireEmployee(EmployeeRepository employees) {
        return new HireEmployee(employees);
    }

    @Bean
    FireEmployee fireEmployee(EmployeeRepository employees) {
        return new FireEmployee(employees);
    }
}
