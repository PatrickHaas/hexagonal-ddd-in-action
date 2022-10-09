package de.ph.example.schedule.domain;

public class InvalidVacationRequestException extends RuntimeException {

    private final InvalidVacationRequestReason reason;

    public InvalidVacationRequestException(InvalidVacationRequestReason reason) {
        super(switch (reason) {
            case NO_MORE_VACATION_LEFT -> "Die Mitarbeiter hat seinen Urlaub bereits aufgebraucht";
            case NOT_ENOUGH_VACATION_LEFT -> "Der Mitarbeiter hat nicht genug Urlaubstage übrig";
        });
        this.reason = reason;
    }

    public InvalidVacationRequestReason getReason() {
        return reason;
    }

    public enum InvalidVacationRequestReason {
        NO_MORE_VACATION_LEFT, NOT_ENOUGH_VACATION_LEFT;
    }
}
