package de.ph.example.schedules.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InvalidDatePeriodException extends RuntimeException {
    public InvalidDatePeriodException(LocalDate start, LocalDate end, String additionalInformation) {
        super("The date period from %s to %s is invalid: %s".formatted(
                start != null ? start.format(DateTimeFormatter.ISO_DATE) : null,
                end != null ? end.format(DateTimeFormatter.ISO_DATE) : null,
                additionalInformation));
    }
}