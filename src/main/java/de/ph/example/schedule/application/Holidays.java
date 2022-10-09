package de.ph.example.schedule.application;

import java.time.LocalDate;
import java.util.List;

public interface Holidays {
    List<LocalDate> findByYear(int year);
}
