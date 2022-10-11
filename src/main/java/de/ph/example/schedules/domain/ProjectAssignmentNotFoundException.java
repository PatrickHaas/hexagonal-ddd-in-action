package de.ph.example.schedules.domain;

import de.ph.example.schedules.domain.ProjectAssignmentId;

public class ProjectAssignmentNotFoundException  extends RuntimeException {
    public ProjectAssignmentNotFoundException(ProjectAssignmentId projectAssignmentId) {
        super("a project assignment with the id %s could not be found".formatted(projectAssignmentId == null ? null : projectAssignmentId.value()));
    }
}
