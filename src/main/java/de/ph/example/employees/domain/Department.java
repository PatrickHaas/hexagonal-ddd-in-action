package de.ph.example.employees.domain;

import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

@AggregateRoot
public class Department {

    @Getter
    @Identity
    private final DepartmentId id;
    @Getter
    private final DepartmentCode code;
    @Getter
    private final DepartmentName name;

    public Department(DepartmentId id, DepartmentCode code, DepartmentName name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}
