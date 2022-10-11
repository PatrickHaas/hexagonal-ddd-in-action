package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.DateTimePeriod;
import de.ph.example.schedules.domain.EmployeeId;
import de.ph.example.schedules.domain.ProjectAssignmentId;
import de.ph.example.schedules.domain.WorkingHoursRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecordWorkingHoursTest {

    @Mock
    private ProjectAssignments projectAssignments;
    @Mock
    private WorkingHours workingHours;

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
        when(projectAssignments.getAssignedHours(projectAssignmentId, period))
                .thenReturn(20.0);
        when(workingHours.findRecordsByProjectAssignmentId(projectAssignmentId))
                .thenReturn(List.of(
                        new WorkingHoursRecord(null, employeeId, projectAssignmentId, new DateTimePeriod(
                                LocalDateTime.of(2022, 10, 10, 8, 0),
                                LocalDateTime.of(2022, 10, 10, 12, 0)
                        )),
                        new WorkingHoursRecord(null, employeeId, projectAssignmentId, new DateTimePeriod(
                                LocalDateTime.of(2022, 10, 10, 13, 0),
                                LocalDateTime.of(2022, 10, 10, 17, 0)
                        ))
                ));
        when(workingHours.save(Mockito.any()))
                .thenAnswer(invocation -> invocation.getArgument(0, WorkingHoursRecord.class));

        WorkingHoursRecord workingHoursRecord = recordWorkingHours.with(employeeId, projectAssignmentId, period);
        assertThat(workingHoursRecord.getEmployeeId()).isEqualTo(employeeId);
        assertThat(workingHoursRecord.getProjectAssignmentId()).isEqualTo(projectAssignmentId);
        assertThat(workingHoursRecord.getPeriod()).isEqualTo(period);
    }

}
