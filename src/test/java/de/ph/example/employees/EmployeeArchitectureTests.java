package de.ph.example.employees;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

public class EmployeeArchitectureTests {

    private final JavaClasses classes = new ClassFileImporter().importPackages(
            "de.ph.example.employees");

    @Test
    void onionArchitectureIsRespected() {
        Architectures.onionArchitecture()
                .domainModels("de.ph.example.employees.domain")
                .domainServices("de.ph.example.employees.application")
                .adapter("de.ph.example.employees.infrastructure")
                .evaluate(classes);
    }
}
