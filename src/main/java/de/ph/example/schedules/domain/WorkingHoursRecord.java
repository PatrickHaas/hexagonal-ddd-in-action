package de.ph.example.schedules.domain;

import lombok.Getter;
import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

@Entity
public class WorkingHoursRecord {

    @Identity
    @Getter
    private final WorkingHoursRecordId id;
    @Getter
    private final DateTimePeriod period;

    public WorkingHoursRecord(WorkingHoursRecordId id, ProjectAssignmentId projectAssignmentId, DateTimePeriod period) {
        if (projectAssignmentId == null) {
            throw new IllegalArgumentException("working hours must be associated with a project assignment");
        }
        if (period == null) {
            throw new IllegalArgumentException("workings hours must contain a valid period");
        }
        this.id = id;
        this.period = period;
    }

    public double calculateHours() {
        return period.asHours();
    }
}
