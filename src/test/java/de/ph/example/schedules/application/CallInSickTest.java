package de.ph.example.schedules.application;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedules.domain.SickNote;
import de.ph.example.schedules.domain.SickNoteId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CallInSickTest {

    @Mock
    private SickNoteRepository repository;

    @InjectMocks
    private CallInSick callInSick;

    @Test
    void callInSick_shouldCreateAndReturnSickNote() {
        when(repository.save(Mockito.any()))
                .then(invocation -> {
                    SickNote sickNote = invocation.getArgument(0, SickNote.class);
                    return new SickNote(SickNoteId.random(), sickNote.getEmployeeId(), sickNote.getDays());
                });
        EmployeeId employeeId = EmployeeId.random();
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        SickNote sickNote = callInSick.with(employeeId, yesterday, today);
        assertThat(sickNote.getId()).isNotNull();
        assertThat(sickNote.getEmployeeId()).isEqualTo(employeeId);
        assertThat(sickNote.getDays()).containsExactlyInAnyOrder(yesterday, today);

        ArgumentCaptor<SickNote> argumentCaptor = ArgumentCaptor.forClass(SickNote.class);
        verify(repository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getId()).isNull();
        assertThat(argumentCaptor.getValue().getEmployeeId()).isEqualTo(employeeId);
        assertThat(argumentCaptor.getValue().getDays()).containsExactlyInAnyOrder(yesterday, today);
    }

}
