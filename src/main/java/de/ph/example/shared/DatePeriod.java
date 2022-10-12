package de.ph.example.shared;

import de.ph.example.schedules.domain.InvalidDatePeriodException;
import org.jmolecules.ddd.annotation.ValueObject;

import java.time.LocalDate;

@ValueObject
public record DatePeriod(LocalDate start, LocalDate end) {

    public DatePeriod {
        if (start == null || end == null) {
            throw new InvalidDatePeriodException(start, end, "either start or end is null");
        } else if (start.isAfter(end)) {
            throw new InvalidDatePeriodException(start, end, "start after end");
        }
    }

    public boolean matchesYear(int year) {
        return start.getYear() == year || end.getYear() == year;
    }

    public boolean contains(DateTimePeriod childPeriod) {
        return (start.isBefore(childPeriod.start().toLocalDate()) || start.isEqual(childPeriod.start().toLocalDate())) && (end.isAfter(childPeriod.end().toLocalDate()) || end.isEqual(childPeriod.end().toLocalDate()));
    }
}
