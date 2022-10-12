package de.ph.example.employees.infrastructure.driving.web;

import de.ph.example.employees.application.*;
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
import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {EmployeeRoutes.class, EmployeeHandler.class}))
class EmployeeWebFluxTests {

    @Autowired
    private EmployeeRoutes routes;

    @MockBean
    private Employees employees;
    @MockBean
    private HireEmployee hireEmployee;

    @MockBean
    private FireEmployee fireEmployee;

    @MockBean
    private AssignDepartment assignDepartment;
    @MockBean
    private FindEmployees findEmployees;

    @Test
    void postToHiredEmployees_shouldHireEmployee() {
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

    @Test
    void postToFiredEmployees_shouldFireEmployee() {
        EmployeeId employeeId = EmployeeId.random();
        FireEmployeeRequest fireEmployeeRequest = new FireEmployeeRequest(employeeId.value());
        Employee employee = new Employee(employeeId, new FirstName("Steve"), new LastName("Rogers"), new Birthdate(LocalDate.of(1918, 7, 4)));
        when(fireEmployee.with(employeeId))
                .thenReturn(employee);

        WebTestClient client = WebTestClient
                .bindToRouterFunction(routes.routes())
                .build();
        client.post()
                .uri("/fired-employees")
                .bodyValue(fireEmployeeRequest)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void postToDepartmentsOfEmployee_shouldAssignDepartmentToEmployee() {
        EmployeeId employeeId = EmployeeId.random();
        DepartmentId departmentId = DepartmentId.random();
        AssignDepartmentRequest assignDepartmentRequest = new AssignDepartmentRequest(employeeId.value(), departmentId.value());
        Employee employee = new Employee(employeeId, new FirstName("Steve"), new LastName("Rogers"), new Birthdate(LocalDate.of(1918, 7, 4)));
        employee.assignDepartment(departmentId);
        when(assignDepartment.with(employeeId, departmentId))
                .thenReturn(employee);

        WebTestClient client = WebTestClient
                .bindToRouterFunction(routes.routes())
                .build();
        client.post()
                .uri("/employees/{employeeId}/departments", employeeId.value())
                .bodyValue(assignDepartmentRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeResponse.class)
                .isEqualTo(EmployeeResponse.fromEmployee(employee));
    }

    @Test
    void getEmployees_shouldReturnAListOfEmployees() {
        EmployeeId stevesId = EmployeeId.random();
        Employee steve = new Employee(stevesId, new FirstName("Steve"), new LastName("Rogers"), new Birthdate(LocalDate.of(1918, 7, 4)));

        EmployeeId tonysId = EmployeeId.random();
        Employee tony = new Employee(tonysId, new FirstName("Tony"), new LastName("Stark"), new Birthdate(LocalDate.of(1970, 5, 26)));

        when(findEmployees.findAll())
                .thenReturn(List.of(steve, tony));

        WebTestClient client = WebTestClient
                .bindToRouterFunction(routes.routes())
                .build();
        client.get()
                .uri("/employees")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeResponse.class)
                .contains(EmployeeResponse.fromEmployee(steve), EmployeeResponse.fromEmployee(tony));
    }

}
