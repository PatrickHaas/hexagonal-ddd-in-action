package de.ph.example.employees.infrastructure.storage;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MongoEmployeeRepository extends MongoRepository<EmployeeEntity, String> {
}
