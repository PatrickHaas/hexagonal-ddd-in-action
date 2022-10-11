package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.EmployeeId;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static de.ph.example.schedules.domain.InvalidVacationRequestException.InvalidVacationRequestReason.NOT_ENOUGH_VACATION_LEFT;
import static de.ph.example.schedules.domain.InvalidVacationRequestException.InvalidVacationRequestReason.NO_MORE_VACATION_LEFT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestVacationTest {

    @Mock
    private CalculateRemainingLeave calculateRemainingLeave;
    @Mock
    private VacationRequests vacationRequests;
    @Mock
    private Holidays holidays;
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
        DatePeriod vacationPeriod = new DatePeriod(nextWeeksMonday, lastSundayOfATwoWeekVacationSpan);

        int[] years = Stream.of(nextWeeksMonday.getYear(), lastSundayOfATwoWeekVacationSpan.getYear()).mapToInt(Integer::intValue).distinct().toArray();
        List<RemainingLeave> remainingLeaves = new ArrayList<>();
        for (int year : years) {
            remainingLeaves.add(new RemainingLeave(employeeId, year, 30));
        }
        List<LocalDate> holidays = List.of(nextWeeksMonday, nextWeeksMonday.plusDays(2));
        when(calculateRemainingLeave.with(employeeId, vacationPeriod)).thenReturn(remainingLeaves);
        when(this.holidays.findByPeriod(vacationPeriod)).thenReturn(holidays);
        when(vacationRequests.save(Mockito.any()))
                .then(invocation -> {
                    VacationRequest givenVacationRequest = invocation.getArgument(0, VacationRequest.class);
                    return new VacationRequest(
                            VacationRequestId.random(),
                            givenVacationRequest.getEmployeeId(),
                            givenVacationRequest.getPeriod(),
                            givenVacationRequest.getVacationDays(),
                            VacationRequestStatus.CREATED);
                });

        VacationRequest vacationRequest = requestVacation.with(employeeId, vacationPeriod);
        assertThat(vacationRequest.getId()).isNotNull();
        assertThat(vacationRequest.getEmployeeId()).isEqualTo(employeeId);
        assertThat(vacationRequest.getPeriod()).isEqualTo(vacationPeriod);
        assertThat(vacationRequest.getVacationDays()).containsExactlyInAnyOrder(
                new VacationDay(nextWeeksMonday.plusDays(1)),
                new VacationDay(nextWeeksMonday.plusDays(3)),
                new VacationDay(nextWeeksMonday.plusDays(4)),
                new VacationDay(nextWeeksMonday.plusDays(7)),
                new VacationDay(nextWeeksMonday.plusDays(8)),
                new VacationDay(nextWeeksMonday.plusDays(9)),
                new VacationDay(nextWeeksMonday.plusDays(10)),
                new VacationDay(nextWeeksMonday.plusDays(11))
        );
    }

    @Test
    void requestVacation_shouldFail_whenTheRequestSpansTwoYearsAndTheRemainingLeaveOfTheFirstYearIsNotEnough() {
        DatePeriod vacationSpan = new DatePeriod(LocalDate.of(2022, 12, 26), LocalDate.of(2023, 1, 6));

        EmployeeId employeeId = EmployeeId.random();
        when(calculateRemainingLeave.with(employeeId, vacationSpan)).thenReturn(List.of(
                new RemainingLeave(employeeId, 2022, 2),
                new RemainingLeave(employeeId, 2023, 30)
        ));
        when(holidays.findByPeriod(vacationSpan)).thenReturn(List.of(
                LocalDate.of(2022, 12, 26),
                LocalDate.of(2023, 1, 1)
        ));

        assertThatThrownBy(() -> requestVacation.with(employeeId, vacationSpan))
                .isInstanceOf(InvalidVacationRequestException.class)
                .matches(ex -> NOT_ENOUGH_VACATION_LEFT.equals(((InvalidVacationRequestException) ex).getReason()));
    }

    @Test
    void requestVacation_shouldFail_whenTheRequestSpansTwoYearsAndTheRemainingLeaveOfTheFirstYearIsZero() {
        DatePeriod vacationSpan = new DatePeriod(LocalDate.of(2022, 12, 26), LocalDate.of(2023, 1, 6));

        EmployeeId employeeId = EmployeeId.random();
        when(calculateRemainingLeave.with(employeeId, vacationSpan)).thenReturn(List.of(
                new RemainingLeave(employeeId, 2022, 0),
                new RemainingLeave(employeeId, 2023, 30)
        ));
        when(holidays.findByPeriod(vacationSpan)).thenReturn(List.of(
                LocalDate.of(2022, 12, 26),
                LocalDate.of(2023, 1, 1)
        ));

        assertThatThrownBy(() -> requestVacation.with(employeeId, vacationSpan))
                .isInstanceOf(InvalidVacationRequestException.class)
                .matches(ex -> NO_MORE_VACATION_LEFT.equals(((InvalidVacationRequestException) ex).getReason()));
    }

    @Test
    void requestVacation_shouldFail_whenTheRequestSpansTwoYearsAndTheRemainingLeaveOfTheSecondYearIsNotEnough() {
        DatePeriod vacationSpan = new DatePeriod(LocalDate.of(2022, 12, 26), LocalDate.of(2023, 1, 6));

        EmployeeId employeeId = EmployeeId.random();
        when(calculateRemainingLeave.with(employeeId, vacationSpan)).thenReturn(List.of(
                new RemainingLeave(employeeId, 2022, 10),
                new RemainingLeave(employeeId, 2023, 2)
        ));
        when(holidays.findByPeriod(vacationSpan)).thenReturn(List.of(
                LocalDate.of(2022, 12, 26),
                LocalDate.of(2023, 1, 1)
        ));

        assertThatThrownBy(() -> requestVacation.with(employeeId, vacationSpan))
                .isInstanceOf(InvalidVacationRequestException.class)
                .matches(ex -> NOT_ENOUGH_VACATION_LEFT.equals(((InvalidVacationRequestException) ex).getReason()));
    }

    @Test
    void requestVacation_shouldFail_whenTheRequestSpansTwoYearsAndTheRemainingLeaveOfTheSecondYearIsZero() {
        DatePeriod vacationSpan = new DatePeriod(LocalDate.of(2022, 12, 26), LocalDate.of(2023, 1, 6));

        EmployeeId employeeId = EmployeeId.random();
        when(calculateRemainingLeave.with(employeeId, vacationSpan)).thenReturn(List.of(
                new RemainingLeave(employeeId, 2022, 10),
                new RemainingLeave(employeeId, 2023, 0)
        ));
        when(holidays.findByPeriod(vacationSpan)).thenReturn(List.of(
                LocalDate.of(2022, 12, 26),
                LocalDate.of(2023, 1, 1)
        ));

        assertThatThrownBy(() -> requestVacation.with(employeeId, vacationSpan))
                .isInstanceOf(InvalidVacationRequestException.class)
                .matches(ex -> NO_MORE_VACATION_LEFT.equals(((InvalidVacationRequestException) ex).getReason()));
    }

    @Test
    void requestVacation_shouldCalculateVacationDaysAndSaveTheRequest_whenTheRemainingLeaveJustFits() {
        DatePeriod vacationPeriod = new DatePeriod(LocalDate.of(2022, 12, 26), LocalDate.of(2023, 1, 6));

        EmployeeId employeeId = EmployeeId.random();
        when(calculateRemainingLeave.with(employeeId, vacationPeriod)).thenReturn(List.of(
                new RemainingLeave(employeeId, 2022, 4),
                new RemainingLeave(employeeId, 2023, 5)
        ));
        when(holidays.findByPeriod(vacationPeriod)).thenReturn(List.of(
                LocalDate.of(2022, 12, 26),
                LocalDate.of(2023, 1, 1)
        ));
        when(vacationRequests.save(Mockito.any()))
                .then(invocation -> {
                    VacationRequest givenVacationRequest = invocation.getArgument(0, VacationRequest.class);
                    return new VacationRequest(
                            VacationRequestId.random(),
                            givenVacationRequest.getEmployeeId(),
                            givenVacationRequest.getPeriod(),
                            givenVacationRequest.getVacationDays(),
                            VacationRequestStatus.CREATED);
                });

        VacationRequest vacationRequest = requestVacation.with(employeeId, vacationPeriod);
        assertThat(vacationRequest.getId()).isNotNull();
        assertThat(vacationRequest.getEmployeeId()).isEqualTo(employeeId);
        assertThat(vacationRequest.getPeriod()).isEqualTo(vacationPeriod);
        assertThat(vacationRequest.getVacationDays()).containsExactlyInAnyOrder(
                new VacationDay(vacationPeriod.start().plusDays(1)),
                new VacationDay(vacationPeriod.start().plusDays(2)),
                new VacationDay(vacationPeriod.start().plusDays(3)),
                new VacationDay(vacationPeriod.start().plusDays(4)),
                new VacationDay(vacationPeriod.start().plusDays(7)),
                new VacationDay(vacationPeriod.start().plusDays(8)),
                new VacationDay(vacationPeriod.start().plusDays(9)),
                new VacationDay(vacationPeriod.start().plusDays(10)),
                new VacationDay(vacationPeriod.start().plusDays(11))
        );
    }

}
