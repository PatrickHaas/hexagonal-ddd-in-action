package de.ph.example.employees.infrastructure.driven.storage;

import de.ph.example.employees.application.Departments;
import de.ph.example.employees.application.Employees;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EmployeeStorageConfiguration {

    @Bean
    Employees employees(SpringMongoEmployeeRepository employeeRepository) {
        return new MongoEmployees(employeeRepository);
    }

    @Bean
    Departments departments() {
        return new InMemoryDepartments();
    }

}
