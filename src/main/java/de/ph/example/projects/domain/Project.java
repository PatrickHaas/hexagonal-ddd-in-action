package de.ph.example.projects.domain;

import de.ph.example.shared.DatePeriod;
import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

import java.time.LocalDate;

@AggregateRoot

public class Project {

    @Identity
    @Getter
    private final ProjectId id;
    @Getter
    private final ProjectName name;
    @Getter
    private final DatePeriod period;
    @Getter
    private AcquireDate acquireDate;

    public Project(ProjectId id, ProjectName name, DatePeriod period) {
        this.id = id;
        this.name = name;
        this.period = period;
    }

    public Project(ProjectId id, ProjectName name, DatePeriod period, AcquireDate acquireDate) {
        this.id = id;
        this.name = name;
        this.period = period;
        this.acquireDate = acquireDate;
    }

    public void acquire() {
        this.acquireDate = new AcquireDate(LocalDate.now());
    }
}
