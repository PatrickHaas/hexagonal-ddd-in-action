package de.ph.example.employees.infrastructure.storage;

import de.ph.example.employees.domain.Employees;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EmployeeStorageConfiguration {

    @Bean
    Employees employees(ReactiveEmployeeRepository employeeRepository) {
        return new JpaEmployees(employeeRepository);
    }

}
