package de.ph.example.schedules.infrastructure;

import de.ph.example.schedules.application.*;
import de.ph.example.schedules.domain.VacationRequestFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ScheduleConfiguration {
    @Bean
    RequestVacation requestVacation(CalculateRemainingLeave calculateRemainingLeave, VacationRequests vacationRequests, Holidays holidays) {
        return new RequestVacation(calculateRemainingLeave, new VacationRequestFactory(), vacationRequests, holidays);
    }

    @Bean
    CalculateRemainingLeave calculateRemainingLeave(VacationRequests vacationRequests, Employees employees) {
        return new CalculateRemainingLeave(vacationRequests, employees);
    }
}
