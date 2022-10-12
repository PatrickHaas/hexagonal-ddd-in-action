package de.ph.example.employees.infrastructure.driving.web;

import de.ph.example.employees.application.AssignDepartment;
import de.ph.example.employees.application.FindEmployees;
import de.ph.example.employees.application.FireEmployee;
import de.ph.example.employees.application.HireEmployee;
import de.ph.example.employees.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class EmployeeHandler {

    private final FindEmployees findEmployees;
    private final HireEmployee hireEmployee;
    private final FireEmployee fireEmployee;
    private final AssignDepartment assignDepartment;

    public Mono<ServerResponse> hire(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(HireEmployeeRequest.class)
                .map(hireEmployeeRequest -> hireEmployee
                        .with(
                                new FirstName(hireEmployeeRequest.firstName()),
                                new LastName(hireEmployeeRequest.lastName()),
                                new Birthdate(hireEmployeeRequest.birthdate())
                        ))
                .flatMap(employee -> ServerResponse.created(URI.create("/employees/" + employee.getId().value())).build());
    }

    public Mono<ServerResponse> fire(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(FireEmployeeRequest.class)
                .map(fireEmployeeRequest -> fireEmployee.with(new EmployeeId(fireEmployeeRequest.id())))
                .flatMap(employee -> ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> assignDepartment(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(AssignDepartmentRequest.class)
                .map(assignDepartmentRequest -> assignDepartment.with(new EmployeeId(assignDepartmentRequest.employeeId()), new DepartmentId(assignDepartmentRequest.departmentId())))
                .flatMap(employee -> ServerResponse.ok().bodyValue(employee));
    }

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return Mono.just(findEmployees.findAll().stream().map(EmployeeResponse::fromEmployee).toList())
                .flatMap(employees -> ServerResponse.ok().bodyValue(employees));
    }

}
