package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.TimePeriod;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository {
    List<LocalDate> findByPeriod(TimePeriod period);
}
