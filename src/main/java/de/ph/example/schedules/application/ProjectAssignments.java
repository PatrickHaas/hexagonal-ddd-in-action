package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.ProjectAssignment;
import de.ph.example.schedules.domain.ProjectAssignmentId;

import java.util.Optional;

public interface ProjectAssignments {
    double getAssignedHours(ProjectAssignmentId projectAssignmentId);

    Optional<ProjectAssignment> findById(ProjectAssignmentId projectAssignmentId);

    ProjectAssignment save(ProjectAssignment projectAssignment);
}
