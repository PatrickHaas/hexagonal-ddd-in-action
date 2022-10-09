package de.ph.example.employees.infrastructure.storage;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface ReactiveEmployeeRepository extends ReactiveMongoRepository<EmployeeEntity, String> {
}
