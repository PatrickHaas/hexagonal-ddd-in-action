package de.ph.example.employees.infrastructure.web;

import de.ph.example.employees.application.EmployeeRepository;
import de.ph.example.employees.application.HireEmployee;
import de.ph.example.employees.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@WebFluxTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {EmployeeRoutes.class, EmployeeHandler.class}))
class EmployeeWebFluxTests {

    @Autowired
    private EmployeeRoutes routes;

    @MockBean
    private EmployeeRepository employees;
    @MockBean
    private HireEmployee hireEmployee;

    @Test
    void postToEmployees_shouldHireEmployee_when() {
        HireEmployeeRequest hireEmployeeRequest = new HireEmployeeRequest("Steve", "Rogers", LocalDate.of(1918, 7, 4));
        Employee employee = new Employee(EmployeeId.random(), new FirstName(hireEmployeeRequest.firstName()), new LastName(hireEmployeeRequest.lastName()), new Birthdate(hireEmployeeRequest.birthdate()));
        when(hireEmployee.with(employee.getFirstName(), employee.getLastName(), employee.getBirthdate()))
                .thenReturn(employee);

        WebTestClient client = WebTestClient
                .bindToRouterFunction(routes.routes())
                .build();
        client.post()
                .uri("/hired-employees")
                .bodyValue(hireEmployeeRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION)
                .expectHeader().valueEquals(HttpHeaders.LOCATION, "/employees/%s".formatted(employee.getId().value()));
    }

}
