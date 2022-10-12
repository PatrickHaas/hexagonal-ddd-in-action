package de.ph.example.schedules.application;

import de.ph.example.shared.DatePeriod;
import org.jmolecules.ddd.annotation.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface Holidays {
    List<LocalDate> findByPeriod(DatePeriod period);
}
