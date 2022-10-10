package de.ph.example.schedules.infrastructure.storage;

import de.ph.example.schedules.application.HolidayRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class InMemoryHolidayRepository implements HolidayRepository {
    public List<LocalDate> findByYear(int year) {
        return List.of(
                LocalDate.of(year, 1, 1),
                LocalDate.of(year, 12, 24),
                LocalDate.of(year, 12, 25),
                LocalDate.of(year, 12, 26)
        );
    }

    @Override
    public List<LocalDate> findByYears(int... years) {
        List<LocalDate> holidays = new ArrayList<>();
        for (int year : years) {
            holidays.addAll(findByYear(year));
        }
        return holidays;
    }
}
