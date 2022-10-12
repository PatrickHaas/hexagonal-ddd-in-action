package de.ph.example.projects.application;

import de.ph.example.projects.domain.AcquireDate;
import de.ph.example.projects.domain.Project;
import de.ph.example.projects.domain.ProjectId;
import de.ph.example.projects.domain.ProjectName;
import de.ph.example.shared.DatePeriod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AcquireProjectTest {

    @Mock
    private Projects projects;

    @InjectMocks
    private AcquireProject acquireProject;

    @Test
    void acquireProject_shouldCreateAndSaveProject() {
        when(projects.save(Mockito.any())).thenAnswer(invocation -> {
            Project project = invocation.getArgument(0, Project.class);
            return new Project(ProjectId.random(), project.getName(), project.getPeriod(), project.getAcquireDate());
        });


        Project project = acquireProject.with(new ProjectName("Project X"), new DatePeriod(LocalDate.of(2023, 1, 1), LocalDate.of(2024, 12, 1)));
        assertThat(project.getId()).isNotNull();
        assertThat(project.getAcquireDate()).isEqualTo(new AcquireDate(LocalDate.now()));
        assertThat(project.getName()).isEqualTo(new ProjectName("Project X"));
        assertThat(project.getPeriod()).isEqualTo(new DatePeriod(LocalDate.of(2023, 1, 1), LocalDate.of(2024, 12, 1)));
    }

}
