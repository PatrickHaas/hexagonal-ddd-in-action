package de.ph.example.schedules.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InvalidVacationSpanException extends RuntimeException {
    public InvalidVacationSpanException(LocalDate start, LocalDate end, String additionalInformation) {
        super("The vacation span from %s to %s is invalid: %s".formatted(
                start != null ? start.format(DateTimeFormatter.ISO_DATE) : null,
                end != null ? end.format(DateTimeFormatter.ISO_DATE) : null,
                additionalInformation));
    }
}