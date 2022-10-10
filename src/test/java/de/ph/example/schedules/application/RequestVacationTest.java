package de.ph.example.schedules.application;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedules.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestVacationTest {

    @Mock
    private CalculateRemainingLeave calculateRemainingLeave;
    @Mock
    private VacationRequestRepository vacationRequestRepository;
    @Mock
    private HolidayRepository holidayRepository;
    @Spy
    private VacationRequestFactory vacationRequestFactory; // Allows us to auto-inject the real implementation
    @InjectMocks
    private RequestVacation requestVacation;

    @Test
    void requestVacation_shouldCalculateVacationDaysAndSaveTheRequest() {
        LocalDate thisWeeksMonday = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate nextWeeksMonday = thisWeeksMonday.plusDays(7);
        LocalDate lastSundayOfATwoWeekVacationSpan = nextWeeksMonday.plusDays(13);

        EmployeeId employeeId = EmployeeId.random();
        VacationSpan vacationSpan = new VacationSpan(nextWeeksMonday, lastSundayOfATwoWeekVacationSpan);

        int[] years = Stream.of(nextWeeksMonday.getYear(), lastSundayOfATwoWeekVacationSpan.getYear()).mapToInt(Integer::intValue).distinct().toArray();
        RemainingLeave remainingLeave = new RemainingLeave(employeeId, 30);
        List<LocalDate> holidays = List.of(nextWeeksMonday);
        when(calculateRemainingLeave.with(employeeId, years)).thenReturn(remainingLeave);
        when(holidayRepository.findByYears(years)).thenReturn(holidays);
        when(vacationRequestRepository.save(Mockito.any()))
                .then(invocation -> {
                    VacationRequest givenVacationRequest = invocation.getArgument(0, VacationRequest.class);
                    return new VacationRequest(
                            VacationRequestId.random(),
                            givenVacationRequest.getEmployeeId(),
                            givenVacationRequest.getSpan(),
                            givenVacationRequest.getVacationDays(),
                            VacationRequestStatus.CREATED);
                });

        VacationRequest vacationRequest = requestVacation.with(employeeId, vacationSpan);
        assertThat(vacationRequest.getId()).isNotNull();
        assertThat(vacationRequest.getEmployeeId()).isEqualTo(employeeId);
        assertThat(vacationRequest.getSpan()).isEqualTo(vacationSpan);
        assertThat(vacationRequest.getVacationDays()).containsExactlyInAnyOrder(
                new VacationDay(nextWeeksMonday.plusDays(1)),
                new VacationDay(nextWeeksMonday.plusDays(2)),
                new VacationDay(nextWeeksMonday.plusDays(3)),
                new VacationDay(nextWeeksMonday.plusDays(4)),
                new VacationDay(nextWeeksMonday.plusDays(7)),
                new VacationDay(nextWeeksMonday.plusDays(8)),
                new VacationDay(nextWeeksMonday.plusDays(9)),
                new VacationDay(nextWeeksMonday.plusDays(10)),
                new VacationDay(nextWeeksMonday.plusDays(11))
        );
    }

}
