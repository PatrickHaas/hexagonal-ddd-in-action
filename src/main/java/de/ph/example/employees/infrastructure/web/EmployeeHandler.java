package de.ph.example.employees.infrastructure.web;

import de.ph.example.employees.application.FireEmployee;
import de.ph.example.employees.application.HireEmployee;
import de.ph.example.employees.domain.Birthdate;
import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.employees.domain.FirstName;
import de.ph.example.employees.domain.LastName;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class EmployeeHandler {

    private final HireEmployee hireEmployee;
    private final FireEmployee fireEmployee;

    public EmployeeHandler(HireEmployee hireEmployee, FireEmployee fireEmployee) {
        this.hireEmployee = hireEmployee;
        this.fireEmployee = fireEmployee;
    }

    public Mono<ServerResponse> hire(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(HireEmployeeRequest.class)
                .flatMap(hireEmployeeRequest -> hireEmployee
                        .with(
                                new FirstName(hireEmployeeRequest.firstName()),
                                new LastName(hireEmployeeRequest.lastName()),
                                new Birthdate(hireEmployeeRequest.birthdate())
                        ))
                .flatMap(employee -> ServerResponse.created(URI.create("/employees/" + employee.getId().value())).build());
    }

    public Mono<ServerResponse> fire(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(FireEmployeeRequest.class)
                .flatMap(fireEmployeeRequest -> fireEmployee.with(new EmployeeId(fireEmployeeRequest.id())))
                .flatMap(employee -> ServerResponse.noContent().build());
    }

}
