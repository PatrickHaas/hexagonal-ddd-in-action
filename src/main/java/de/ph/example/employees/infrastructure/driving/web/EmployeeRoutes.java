package de.ph.example.employees.infrastructure.driving.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class EmployeeRoutes {

    private final EmployeeHandler handler;

    public EmployeeRoutes(EmployeeHandler handler) {
        this.handler = handler;
    }

    @Bean
    RouterFunction<ServerResponse> routes() {
        return route(GET("/employees"), handler::findAll)
                .andRoute(POST("/hired-employees"), handler::hire)
                .andRoute(POST("/fired-employees"), handler::fire)
                .andRoute(POST("/hired-employees/{employeeId}/departments"), handler::assignDepartment);
    }

}
