package de.ph.example.schedules.infrastructure.storage;

import de.ph.example.schedules.application.Holidays;
import de.ph.example.schedules.domain.DatePeriod;

import java.time.LocalDate;
import java.util.List;

class InMemoryHolidays implements Holidays {
    public List<LocalDate> findByYear(int year) {
        return List.of(
                LocalDate.of(year, 1, 1),
                LocalDate.of(year, 12, 24),
                LocalDate.of(year, 12, 25),
                LocalDate.of(year, 12, 26)
        );
    }

    @Override
    public List<LocalDate> findByPeriod(DatePeriod period) {
        return null;
    }
}
