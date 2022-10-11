package de.ph.example.schedules.domain;

import java.time.LocalDate;

public record TimePeriod(LocalDate start, LocalDate end) {

    public TimePeriod {
        if (start == null || end == null) {
            throw new InvalidVacationSpanException(start, end, "either start or end is null");
        } else if (start.isAfter(end)) {
            throw new InvalidVacationSpanException(start, end, "start after end");
        }
    }

    public boolean matchesYear(int year) {
        return start.getYear() == year || end.getYear() == year;
    }

}
