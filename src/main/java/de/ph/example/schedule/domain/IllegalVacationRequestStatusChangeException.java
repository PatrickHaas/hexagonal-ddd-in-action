package de.ph.example.schedule.domain;

public class IllegalVacationRequestStatusChangeException extends RuntimeException {

    public IllegalVacationRequestStatusChangeException(VacationRequestStatus oldStatus, VacationRequestStatus newStatus) {
        super("a status change from %s to %s is illegal".formatted(oldStatus, newStatus));
    }

}
