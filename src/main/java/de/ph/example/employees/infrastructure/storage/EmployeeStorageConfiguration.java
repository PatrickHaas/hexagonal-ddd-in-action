package de.ph.example.employees.infrastructure.storage;

import de.ph.example.employees.application.Employees;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EmployeeStorageConfiguration {

    @Bean
    Employees employees(MongoEmployeeRepository employeeRepository) {
        return new MongoEmployees(employeeRepository);
    }

}