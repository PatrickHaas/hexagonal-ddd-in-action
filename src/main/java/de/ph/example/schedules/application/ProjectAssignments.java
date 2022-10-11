package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.DateTimePeriod;
import de.ph.example.schedules.domain.ProjectAssignmentId;

public interface ProjectAssignments {
    double getAssignedHours(ProjectAssignmentId projectAssignmentId, DateTimePeriod period);
}
