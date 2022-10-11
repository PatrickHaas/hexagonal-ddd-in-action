package de.ph.example;


import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = {
        "de.ph.example.employees",
        "de.ph.example.schedules"
})
class ArchTests {

    @ArchTest
    static final ArchRule contextSeparationEmployeesToSchedules =
            noClasses().that().resideInAPackage("..employees..")
                    .should().dependOnClassesThat().resideInAPackage("..schedules..");

    @ArchTest
    static final ArchRule contextSeparationSchedulesToEmployees =
            noClasses().that().resideInAPackage("..schedules..")
                    .should().dependOnClassesThat().resideInAPackage("..employees..");
}
