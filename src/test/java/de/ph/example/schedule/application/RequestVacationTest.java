package de.ph.example.schedule.application;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedule.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestVacationTest {

    @Mock
    private Employees employees;
    @Mock
    private Holidays holidays;
    @Mock
    private VacationRequests vacationRequests;
    @InjectMocks
    private RequestVacation requestVacation;

    @Test
    void requestVacation_shouldCalculateVacationDaysAndSaveTheRequest() {
        LocalDate thisWeeksMonday = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate nextWeeksMonday = thisWeeksMonday.plusDays(7);
        LocalDate lastSundayOfATwoWeekVacationSpan = nextWeeksMonday.plusDays(13);

        EmployeeId employeeId = EmployeeId.random();

        when(employees.calculateLeftOverVacationDays(employeeId, nextWeeksMonday.getYear()))
                .thenReturn(30);
        if (lastSundayOfATwoWeekVacationSpan.getYear() != nextWeeksMonday.getYear()) {
            when(employees.calculateLeftOverVacationDays(employeeId, lastSundayOfATwoWeekVacationSpan.getYear()))
                    .thenReturn(30);
        }

        when(holidays.findByYear(nextWeeksMonday.getYear()))
                .thenReturn(List.of(nextWeeksMonday));
        if (lastSundayOfATwoWeekVacationSpan.getYear() != nextWeeksMonday.getYear()) {
            when(holidays.findByYear(lastSundayOfATwoWeekVacationSpan.getYear()))
                    .thenReturn(Collections.emptyList());
        }

        when(vacationRequests.save(Mockito.any()))
                .then(invocation -> {
                    VacationRequest givenVacationRequest = invocation.getArgument(0, VacationRequest.class);
                    return Mono.just(new VacationRequest(
                            VacationRequestId.random(),
                            givenVacationRequest.getEmployeeId(),
                            givenVacationRequest.getSpan(),
                            givenVacationRequest.getVacationDays(),
                            VacationRequestStatus.CREATED));
                });

        VacationSpan vacationSpan = new VacationSpan(nextWeeksMonday, lastSundayOfATwoWeekVacationSpan);
        VacationRequest vacationRequest = requestVacation.with(employeeId, vacationSpan).block();
        assertThat(vacationRequest.getId()).isNotNull();
        assertThat(vacationRequest.getEmployeeId()).isEqualTo(employeeId);
        assertThat(vacationRequest.getSpan()).isEqualTo(vacationSpan);
        assertThat(vacationRequest.getVacationDays()).containsExactlyInAnyOrder(
                nextWeeksMonday.plusDays(1),
                nextWeeksMonday.plusDays(2),
                nextWeeksMonday.plusDays(3),
                nextWeeksMonday.plusDays(4),
                nextWeeksMonday.plusDays(7),
                nextWeeksMonday.plusDays(8),
                nextWeeksMonday.plusDays(9),
                nextWeeksMonday.plusDays(10),
                nextWeeksMonday.plusDays(11)
        );
    }

}
