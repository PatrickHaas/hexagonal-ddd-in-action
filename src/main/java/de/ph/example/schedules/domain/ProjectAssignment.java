package de.ph.example.schedules.domain;

import de.ph.example.shared.DatePeriod;
import de.ph.example.shared.DateTimePeriod;
import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AggregateRoot
public class ProjectAssignment {

    @Identity
    @Getter
    private final ProjectAssignmentId id;
    @Getter
    private final ProjectId projectId;
    @Getter
    private final EmployeeId employeeId;
    @Getter
    private final DatePeriod period;
    @Getter
    private final double assignedHours;

    private final List<WorkingHoursRecord> records;

    public ProjectAssignment(ProjectAssignmentId id, ProjectId projectId, EmployeeId employeeId, DatePeriod period, double assignedHours) {
        this.id = id;
        this.projectId = projectId;
        this.employeeId = employeeId;
        this.period = period;
        this.assignedHours = assignedHours;
        this.records = new ArrayList<>();
    }

    public void recordWorkingHours(DateTimePeriod period) {
        double alreadyWorkedHours = records.stream().mapToDouble(WorkingHoursRecord::calculateHours).sum();
        double remainingHours = assignedHours - alreadyWorkedHours;
        double recordHours = period.asHours();
        if (!getPeriod().contains(period)) {
            throw new IllegalArgumentException("the period %s does not fit the allowed period of %s".formatted(period, getPeriod()));
        }
        if (remainingHours < recordHours) {
            throw new IllegalArgumentException("not enough hours left on assignment");
        }
        records.add(new WorkingHoursRecord(null, id, period));
    }

    public List<WorkingHoursRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }
}