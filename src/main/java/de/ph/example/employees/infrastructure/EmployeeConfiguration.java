package de.ph.example.employees.infrastructure;

import de.ph.example.employees.application.*;
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

    @Bean
    FindEmployees findEmployees(Employees employees) {
        return new FindEmployees(employees);
    }

    @Bean
    AssignDepartment assignDepartment(Employees employees, Departments departments) {
        return new AssignDepartment(employees, departments);
    }
}
