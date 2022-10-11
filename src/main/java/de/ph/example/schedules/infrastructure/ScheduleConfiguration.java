package de.ph.example.schedules.infrastructure;

import de.ph.example.schedules.application.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ScheduleConfiguration {
    @Bean
    RequestVacation requestVacation(CalculateRemainingLeave calculateRemainingLeave, VacationRequests vacationRequests, Holidays holidays) {
        return new RequestVacation(calculateRemainingLeave, vacationRequests, holidays);
    }

    @Bean
    CalculateRemainingLeave calculateRemainingLeave(VacationRequests vacationRequests, Employees employees) {
        return new CalculateRemainingLeave(vacationRequests, employees);
    }
}
