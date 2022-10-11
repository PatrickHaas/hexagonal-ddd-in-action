package de.ph.example.employees.infrastructure.storage;

import de.ph.example.employees.application.EmployeeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EmployeeStorageConfiguration {

    @Bean
    EmployeeRepository employees(SpringMongoEmployeeRepository employeeRepository) {
        return new MongoEmployeeRepository(employeeRepository);
    }

}
