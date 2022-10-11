package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.DateTimePeriod;
import de.ph.example.schedules.domain.EmployeeId;
import de.ph.example.schedules.domain.ProjectAssignmentId;
import de.ph.example.schedules.domain.WorkingHoursRecord;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.annotation.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordWorkingHours {

    private final ProjectAssignments projectAssignments;
    private final WorkingHours workingHours;

    public WorkingHoursRecord with(EmployeeId employeeId, ProjectAssignmentId projectAssignmentId, DateTimePeriod period) {
        double assignedHours = projectAssignments.getAssignedHours(projectAssignmentId, period);
        List<WorkingHoursRecord> workingHourRecords = workingHours.findRecordsByProjectAssignmentId(projectAssignmentId);
        double alreadyWorkedHoursOnProject = workingHourRecords.stream().mapToDouble(WorkingHoursRecord::calculateHours).sum();
        double remainingHours = assignedHours - alreadyWorkedHoursOnProject;
        double recordHours = period.asHours();
        if (remainingHours < recordHours) {
            throw new IllegalArgumentException("not enough hours left on assignment");
        }
        return workingHours.save(new WorkingHoursRecord(null, employeeId, projectAssignmentId, period));
    }
}
