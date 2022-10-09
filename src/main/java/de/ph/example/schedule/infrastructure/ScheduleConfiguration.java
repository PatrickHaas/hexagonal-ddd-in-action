package de.ph.example.schedule.infrastructure;

import de.ph.example.schedule.domain.Employees;
import de.ph.example.schedule.domain.Holidays;
import de.ph.example.schedule.domain.RequestVacation;
import de.ph.example.schedule.domain.VacationRequests;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ScheduleConfiguration {
    @Bean
    RequestVacation requestVacation(Employees employeeRepository, Holidays holidayRepository, VacationRequests vacationRequestRepository) {
        return new RequestVacation(vacationRequestRepository, holidayRepository, employeeRepository);
    }
}
