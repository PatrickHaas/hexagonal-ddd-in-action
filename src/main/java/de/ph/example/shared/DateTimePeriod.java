package de.ph.example.shared;

import de.ph.example.schedules.domain.InvalidDateTimePeriodException;
import org.jmolecules.ddd.annotation.ValueObject;

import java.time.Duration;
import java.time.LocalDateTime;

@ValueObject
public record DateTimePeriod(LocalDateTime start, LocalDateTime end) {

    public DateTimePeriod {
        if (start == null || end == null) {
            throw new InvalidDateTimePeriodException(start, end, "either start or end is null");
        } else if (start.isAfter(end)) {
            throw new InvalidDateTimePeriodException(start, end, "start after end");
        }
    }

    public double asHours() {
        return Duration.between(start, end).toMillis() / 1000.0 / 60.0 / 60.0;
    }

}
