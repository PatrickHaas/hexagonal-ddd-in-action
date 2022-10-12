package de.ph.example.employees.infrastructure;

import de.ph.example.employees.application.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EmployeeConfiguration {
    @Bean
    HireEmployee hireEmployee(Employees employees, EmployeeApplicationEvents employeeApplicationEvents) {
        return new HireEmployee(employees, employeeApplicationEvents);
    }

    @Bean
    FireEmployee fireEmployee(Employees employees, EmployeeApplicationEvents employeeApplicationEvents) {
        return new FireEmployee(employees, employeeApplicationEvents);
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
