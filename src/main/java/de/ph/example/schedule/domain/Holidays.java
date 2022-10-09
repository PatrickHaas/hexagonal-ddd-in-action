package de.ph.example.schedule.domain;

import java.time.LocalDate;
import java.util.List;

public interface Holidays {
    List<LocalDate> findByYear(int year);
}
