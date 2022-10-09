package de.ph.example.schedule.infrastructure.storage;

import de.ph.example.schedule.application.Holidays;

import java.time.LocalDate;
import java.util.List;

class InMemoryHolidays implements Holidays {
    @Override
    public List<LocalDate> findByYear(int year) {
        return List.of(
                LocalDate.of(year, 1, 1),
                LocalDate.of(year, 12, 24),
                LocalDate.of(year, 12, 25),
                LocalDate.of(year, 12, 26)
        );
    }
}
