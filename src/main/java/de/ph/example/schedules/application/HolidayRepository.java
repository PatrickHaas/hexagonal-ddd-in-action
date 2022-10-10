package de.ph.example.schedules.application;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository {
    List<LocalDate> findByYears(int... years);

}
