package de.ph.example.schedule.application;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository {
    List<LocalDate> findByYears(int... years);

}
