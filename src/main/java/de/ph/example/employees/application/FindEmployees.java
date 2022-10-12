package de.ph.example.employees.application;

import de.ph.example.employees.domain.Employee;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.annotation.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindEmployees {

    private final Employees employees;

    public List<Employee> findAll() {
        return employees.findAll();
    }

}
