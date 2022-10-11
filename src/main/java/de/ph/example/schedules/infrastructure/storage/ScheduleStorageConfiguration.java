package de.ph.example.schedules.infrastructure.storage;

import de.ph.example.schedules.application.Employees;
import de.ph.example.schedules.application.Holidays;
import de.ph.example.schedules.application.VacationRequests;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ScheduleStorageConfiguration {
    @Bean
    Employees employeeRepository(VacationRequests vacationRequests) {
        return new InMemoryEmployees(vacationRequests);
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
