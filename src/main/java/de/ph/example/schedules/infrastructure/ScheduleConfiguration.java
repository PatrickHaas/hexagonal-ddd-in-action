package de.ph.example.schedules.infrastructure;

import de.ph.example.schedules.application.CalculateRemainingLeave;
import de.ph.example.schedules.application.HolidayRepository;
import de.ph.example.schedules.application.RequestVacation;
import de.ph.example.schedules.application.VacationRequestRepository;
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
    CalculateRemainingLeave calculateRemainingLeave(VacationRequestRepository vacationRequestRepository) {
        return new CalculateRemainingLeave(vacationRequestRepository);
    }
}
