package de.ph.example.schedules.infrastructure.driven.storage;

import de.ph.example.schedules.application.ProjectAssignments;
import de.ph.example.schedules.domain.ProjectAssignment;
import de.ph.example.schedules.domain.ProjectAssignmentId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemoryProjectAssignmentsRepository implements ProjectAssignments {

    private final Map<ProjectAssignmentId, ProjectAssignment> projectAssignments = new HashMap<>();

    @Override
    public Optional<ProjectAssignment> findById(ProjectAssignmentId projectAssignmentId) {
        return Optional.ofNullable(projectAssignments.get(projectAssignmentId));
    }

    @Override
    public ProjectAssignment save(ProjectAssignment projectAssignment) {
        ProjectAssignmentId projectAssignmentId = Optional.ofNullable(projectAssignment.getId()).orElseGet(ProjectAssignmentId::random);
        ProjectAssignment savedProjectAssignment = new ProjectAssignment(projectAssignmentId, projectAssignment.getProjectId(), projectAssignment.getEmployeeId(), projectAssignment.getPeriod(), projectAssignment.getAssignedHours());
        projectAssignments.put(projectAssignmentId, savedProjectAssignment);
        return savedProjectAssignment;
    }
}
