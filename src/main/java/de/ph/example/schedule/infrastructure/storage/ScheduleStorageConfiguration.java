package de.ph.example.schedule.infrastructure.storage;

import de.ph.example.schedule.domain.Employees;
import de.ph.example.schedule.domain.Holidays;
import de.ph.example.schedule.domain.VacationRequests;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ScheduleStorageConfiguration {
    @Bean
    Employees employeeRepository(VacationRequests vacationRequestRepository) {
        return new InMemoryEmployees(vacationRequestRepository);
    }

    @Bean
    VacationRequests vacationRequestRepository() {
        return new InMemoryVacationRequests();
    }

    @Bean
    Holidays holidayRepository() {
        return new InMemoryHolidays();
    }
}
