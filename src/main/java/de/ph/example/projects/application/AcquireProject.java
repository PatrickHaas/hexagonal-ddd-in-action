package de.ph.example.projects.application;

import de.ph.example.projects.domain.Project;
import de.ph.example.projects.domain.ProjectName;
import de.ph.example.shared.DatePeriod;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.annotation.Service;

@Service
@RequiredArgsConstructor
public class AcquireProject {

    private final Projects projects;

    public Project with(ProjectName name, DatePeriod datePeriod) {
        Project project = new Project(null, name, datePeriod);
        project.acquire();
        return projects.save(project);
    }
}
