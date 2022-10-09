package de.ph.example.employees;

import de.ph.example.employees.application.FireEmployee;
import de.ph.example.employees.application.HireEmployee;
import de.ph.example.employees.domain.Employees;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeesApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeesApplication.class, args);
    }

    @Bean
    HireEmployee hireEmployee(Employees employees) {
        return new HireEmployee(employees);
    }

    @Bean
    FireEmployee fireEmployee(Employees employees) {
        return new FireEmployee(employees);
    }
}
