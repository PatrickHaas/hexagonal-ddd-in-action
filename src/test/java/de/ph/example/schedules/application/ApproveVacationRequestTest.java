package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApproveVacationRequestTest {

    @Mock
    private VacationRequests vacationRequests;

    @InjectMocks
    private ApproveVacationRequest approveVacationRequest;

    @Test
    void approveVacationRequest_shouldApproveAndSaveRequest_whenItWasFound() {
        VacationRequestId vacationRequestId = VacationRequestId.random();
        when(vacationRequests.findById(vacationRequestId)).thenReturn(Optional.of(new VacationRequest(
                vacationRequestId,
                EmployeeId.random(),
                new TimePeriod(LocalDate.now(), LocalDate.now().plusDays(3)),
                List.of(new VacationDay(LocalDate.now().plusDays(1))),
                VacationRequestStatus.CREATED)));
        when(vacationRequests.save(Mockito.any()))
                .then(invocation -> invocation.getArgument(0, VacationRequest.class));

        VacationRequest approvedVacationRequest = approveVacationRequest.with(vacationRequestId);
        assertThat(approvedVacationRequest.getStatus()).isEqualTo(VacationRequestStatus.APPROVED);
    }

}
