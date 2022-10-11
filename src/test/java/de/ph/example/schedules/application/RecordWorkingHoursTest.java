package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecordWorkingHoursTest {

    @Mock
    private ProjectAssignments projectAssignments;

    @InjectMocks
    private RecordWorkingHours recordWorkingHours;

    @Test
    void recordWorkingHours_shouldSaveAWorkingHourRecord() {
        DateTimePeriod period = new DateTimePeriod(
                LocalDateTime.of(2022, 10, 11, 8, 0),
                LocalDateTime.of(2022, 10, 11, 12, 0)
        );
        EmployeeId employeeId = EmployeeId.random();
        ProjectAssignmentId projectAssignmentId = ProjectAssignmentId.random();
        when(projectAssignments.findById(projectAssignmentId))
                .thenReturn(Optional.of(new ProjectAssignment(ProjectAssignmentId.random(), employeeId, new DatePeriod(LocalDate.of(2022, 10, 1), LocalDate.of(2022, 10, 31)), 20.0)));
        when(projectAssignments.save(Mockito.any()))
                .thenAnswer(invocation -> invocation.getArgument(0, ProjectAssignment.class));

        ProjectAssignment savedProjectAssignment = recordWorkingHours.with(projectAssignmentId, period);
        assertThat(savedProjectAssignment.getRecords()).hasSize(1);
    }

    @Test
    void recordWorkingHours_shouldFail_whenTheAssignmentHasNotEnoughHoursLeft() {

        DateTimePeriod period = new DateTimePeriod(
                LocalDateTime.of(2022, 10, 11, 8, 0),
                LocalDateTime.of(2022, 10, 11, 12, 0)
        );
        EmployeeId employeeId = EmployeeId.random();
        ProjectAssignmentId projectAssignmentId = ProjectAssignmentId.random();
        when(projectAssignments.findById(projectAssignmentId))
                .thenReturn(Optional.of(new ProjectAssignment(null, employeeId, new DatePeriod(LocalDate.of(2022, 10, 1), LocalDate.of(2022, 10, 31)), 10.0)));
        assertThatThrownBy(() -> recordWorkingHours.with(projectAssignmentId, period))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
