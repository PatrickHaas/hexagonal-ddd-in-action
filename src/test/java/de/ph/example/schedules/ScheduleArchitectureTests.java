package de.ph.example.schedules;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

public class ScheduleArchitectureTests {

    private final JavaClasses classes = new ClassFileImporter().importPackages(
            "de.ph.example.schedules");

    @Test
    void onionArchitectureIsRespected() {
        Architectures.onionArchitecture()
                .domainModels("de.ph.example.schedules.domain")
                .domainServices("de.ph.example.schedules.application")
                .adapter("de.ph.example.schedules.infrastructure")
                .evaluate(classes);
    }
}
