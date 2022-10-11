package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.ProjectAssignmentId;
import de.ph.example.schedules.domain.WorkingHoursRecord;

import java.util.List;

public interface WorkingHours {
    WorkingHoursRecord save(WorkingHoursRecord record);
    List<WorkingHoursRecord> findRecordsByProjectAssignmentId(ProjectAssignmentId projectAssignmentId);
}
