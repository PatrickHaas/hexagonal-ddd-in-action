package de.ph.example.schedules.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvalidDateTimePeriodException extends RuntimeException {
    public InvalidDateTimePeriodException(LocalDateTime start, LocalDateTime end, String additionalInformation) {
        super("The date time period span from %s to %s is invalid: %s".formatted(
                start != null ? start.format(DateTimeFormatter.ISO_DATE) : null,
                end != null ? end.format(DateTimeFormatter.ISO_DATE) : null,
                additionalInformation));
    }
}