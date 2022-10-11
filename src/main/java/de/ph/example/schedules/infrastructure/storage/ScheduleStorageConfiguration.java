package de.ph.example.schedules.infrastructure.storage;

import de.ph.example.schedules.application.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ScheduleStorageConfiguration {
    @Bean
    Employees employeeRepository(VacationRequests vacationRequests) {
        return new InMemoryEmployeeRepository(vacationRequests);
    }

    @Bean
    VacationRequests vacationRequestRepository() {
        return new InMemoryVacationRequestRepository();
    }

    @Bean
    Holidays holidayRepository() {
        return new InMemoryHolidayRepository();
    }

    @Bean
    ProjectAssignments projectAssignmentRepository() {
        return new InMemoryProjectAssignmentsRepository();
    }

    @Bean
    SickNotes sickNoteRepository() {
        return new InMemorySickNoteRepository();
    }
}
