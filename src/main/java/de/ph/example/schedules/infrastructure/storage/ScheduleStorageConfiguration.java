package de.ph.example.schedules.infrastructure.storage;

import de.ph.example.schedules.application.EmployeeRepository;
import de.ph.example.schedules.application.HolidayRepository;
import de.ph.example.schedules.application.VacationRequestRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ScheduleStorageConfiguration {
    @Bean
    EmployeeRepository employeeRepository(VacationRequestRepository vacationRequestRepository) {
        return new InMemoryEmployeeRepository(vacationRequestRepository);
    }

    @Bean
    VacationRequestRepository vacationRequestRepository() {
        return new InMemoryVacationRequestRepository();
    }

    @Bean
    HolidayRepository holidayRepository() {
        return new InMemoryHolidayRepository();
    }
}
