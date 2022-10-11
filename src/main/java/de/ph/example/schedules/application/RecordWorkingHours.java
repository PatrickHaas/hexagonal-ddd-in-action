package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.DateTimePeriod;
import de.ph.example.schedules.domain.ProjectAssignment;
import de.ph.example.schedules.domain.ProjectAssignmentId;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.annotation.Service;

@Service
@RequiredArgsConstructor
public class RecordWorkingHours {

    private final ProjectAssignments projectAssignments;

    public ProjectAssignment with(ProjectAssignmentId projectAssignmentId, DateTimePeriod period) {
        ProjectAssignment projectAssignment = projectAssignments.findById(projectAssignmentId).orElseThrow(() -> new ProjectAssignmentNotFoundException(projectAssignmentId));
        projectAssignment.recordWorkingHours(period);
        return projectAssignments.save(projectAssignment);
    }
}
