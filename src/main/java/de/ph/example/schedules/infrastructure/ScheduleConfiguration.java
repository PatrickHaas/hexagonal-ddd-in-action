package de.ph.example.schedules.infrastructure;

import de.ph.example.schedules.application.*;
import de.ph.example.schedules.domain.VacationRequestFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ScheduleConfiguration {
    @Bean
    RequestVacation requestVacation(CalculateRemainingLeave calculateRemainingLeave, VacationRequestRepository vacationRequestRepository, HolidayRepository holidayRepository) {
        return new RequestVacation(calculateRemainingLeave, new VacationRequestFactory(), vacationRequestRepository, holidayRepository);
    }

    @Bean
    CalculateRemainingLeave calculateRemainingLeave(VacationRequestRepository vacationRequestRepository, EmployeeRepository employeeRepository) {
        return new CalculateRemainingLeave(vacationRequestRepository, employeeRepository);
    }
}
