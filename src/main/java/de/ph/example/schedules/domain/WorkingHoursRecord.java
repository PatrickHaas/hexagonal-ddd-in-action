package de.ph.example.schedules.domain;

import lombok.Getter;
import org.jmolecules.ddd.annotation.Identity;

public class WorkingHoursRecord {

    @Identity
    @Getter
    private final WorkingHoursRecordId id;
    @Getter
    private final ProjectAssignmentId projectAssignmentId;
    @Getter
    private final EmployeeId employeeId;
    @Getter
    private final DateTimePeriod period;
    @Getter
    private boolean released;

    public WorkingHoursRecord(WorkingHoursRecordId id, EmployeeId employeeId, ProjectAssignmentId projectAssignmentId, DateTimePeriod period) {
        if (employeeId == null) {
            throw new IllegalArgumentException("working hours must be associated with an employee");
        }
        if (projectAssignmentId == null) {
            throw new IllegalArgumentException("working hours must be associated with a project");
        }
        if (period == null) {
            throw new IllegalArgumentException("workings hours must contain a valid period");
        }
        this.id = id;
        this.employeeId = employeeId;
        this.projectAssignmentId = projectAssignmentId;
        this.period = period;
    }

    public void release() {
        this.released = true;
    }

    public double calculateHours() {
        return period.asHours();
    }
}
