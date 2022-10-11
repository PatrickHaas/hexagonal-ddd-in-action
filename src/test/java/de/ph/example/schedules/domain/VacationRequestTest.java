package de.ph.example.schedules.domain;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VacationRequestTest {

    @Test
    void matchesYear_shouldReturnFalse_whenNeitherStartNorEndIsInGivenYear() {
        VacationRequest vacationRequest = new VacationRequest(
                null,
                EmployeeId.random(),
                new TimePeriod(LocalDate.of(2022, 12, 19), LocalDate.of(2023, 1, 6))
        );
        assertThat(vacationRequest.matchesYear(1999)).isFalse();
        assertThat(vacationRequest.matchesYear(2021)).isFalse();
        assertThat(vacationRequest.matchesYear(2024)).isFalse();
    }

    @Test
    void matchesYear_shouldReturnTrue_whenEitherStartOrEndIsInGivenYear() {
        VacationRequest vacationRequest = new VacationRequest(
                null,
                EmployeeId.random(),
                new TimePeriod(LocalDate.of(2022, 12, 19), LocalDate.of(2023, 1, 6))
        );
        assertThat(vacationRequest.matchesYear(2022)).isTrue();
        assertThat(vacationRequest.matchesYear(2023)).isTrue();
    }

    @Test
    void calculateVacationDays_shouldIgnoreSaturdaysAndSundaysAndConsiderPassedHolidays() {
        VacationRequest vacationRequest = new VacationRequest(
                null,
                EmployeeId.random(),
                new TimePeriod(LocalDate.of(2022, 12, 19), LocalDate.of(2023, 1, 6))
        );
        vacationRequest.calculateVacationDays(List.of(
                LocalDate.of(2022, 12, 24),
                LocalDate.of(2022, 12, 25),
                LocalDate.of(2022, 12, 26),
                LocalDate.of(2023, 1, 1)
        ));

        assertThat(vacationRequest.getVacationDays()).containsExactlyInAnyOrder(
                new VacationDay(LocalDate.of(2022, 12, 19)),
                new VacationDay(LocalDate.of(2022, 12, 20)),
                new VacationDay(LocalDate.of(2022, 12, 21)),
                new VacationDay(LocalDate.of(2022, 12, 22)),
                new VacationDay(LocalDate.of(2022, 12, 23)),
                new VacationDay(LocalDate.of(2022, 12, 27)),
                new VacationDay(LocalDate.of(2022, 12, 28)),
                new VacationDay(LocalDate.of(2022, 12, 29)),
                new VacationDay(LocalDate.of(2022, 12, 30)),
                new VacationDay(LocalDate.of(2023, 1, 2)),
                new VacationDay(LocalDate.of(2023, 1, 3)),
                new VacationDay(LocalDate.of(2023, 1, 4)),
                new VacationDay(LocalDate.of(2023, 1, 5)),
                new VacationDay(LocalDate.of(2023, 1, 6))
        );
    }

    @Test
    void calculateVacationDays_shouldFail_whenNoPossibleVacationDaysAreInPeriod() {
        VacationRequest vacationRequest = new VacationRequest(
                null,
                EmployeeId.random(),
                new TimePeriod(LocalDate.of(2022, 12, 24), LocalDate.of(2022, 12, 26))
        );
        assertThatThrownBy(() -> vacationRequest.calculateVacationDays(List.of(
                LocalDate.of(2022, 12, 24),
                LocalDate.of(2022, 12, 25),
                LocalDate.of(2022, 12, 26),
                LocalDate.of(2023, 1, 1)
        ))).isInstanceOf(IllegalArgumentException.class);
    }

}
