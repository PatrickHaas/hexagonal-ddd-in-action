package de.ph.example.employees.infrastructure.storage;

import de.ph.example.employees.application.EmployeeRepository;
import de.ph.example.employees.domain.Birthdate;
import de.ph.example.employees.domain.Employee;
import de.ph.example.employees.domain.FirstName;
import de.ph.example.employees.domain.LastName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {EmployeeStorageConfiguration.class}
))
@Testcontainers
class EmployeeStorageTests {
    @Container
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo"));
    @Autowired
    private EmployeeRepository employees;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void save_shouldPersistEmployeeAndGiveHimAnId() {
        Employee savedEmployee = employees.save(new Employee(null, new FirstName("Tony"), new LastName("Stark"), new Birthdate(LocalDate.of(1970, 5, 29))));
        assertThat(savedEmployee.getId()).isNotNull();

        Employee loadedEmployee = employees.findById(savedEmployee.getId()).orElse(null);
        assertThat(loadedEmployee).isNotNull();
        assertThat(loadedEmployee.getFirstName().value()).isEqualTo("Tony");
        assertThat(loadedEmployee.getLastName().value()).isEqualTo("Stark");
        assertThat(loadedEmployee.getBirthdate().value()).isEqualTo(LocalDate.of(1970, 5, 29));
    }
}
