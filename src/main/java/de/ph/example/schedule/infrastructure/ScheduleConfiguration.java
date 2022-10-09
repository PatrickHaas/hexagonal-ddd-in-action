package de.ph.example.schedule.infrastructure;

import de.ph.example.schedule.application.CalculateRemainingLeave;
import de.ph.example.schedule.application.HolidayRepository;
import de.ph.example.schedule.application.RequestVacation;
import de.ph.example.schedule.application.VacationRequestRepository;
import de.ph.example.schedule.domain.VacationRequestFactory;
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
