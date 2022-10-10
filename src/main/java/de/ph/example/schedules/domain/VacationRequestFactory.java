package de.ph.example.schedules.domain;

import de.ph.example.employees.domain.EmployeeId;
import org.jmolecules.ddd.annotation.Factory;

import java.time.LocalDate;
import java.util.List;

@Factory
public class VacationRequestFactory {

    // TODO not sure if the factory should ensure rules, that can not be ensured by the entity
    public VacationRequest create(EmployeeId employeeId, VacationSpan span, List<LocalDate> holidays, RemainingLeave remainingLeave) {
        VacationRequest vacationRequest = new VacationRequest(null, employeeId, span);
        vacationRequest.calculateVacationDays(holidays);
        if (remainingLeave.days() == 0) {
            throw new InvalidVacationRequestException(InvalidVacationRequestException.InvalidVacationRequestReason.NO_MORE_VACATION_LEFT);
        } else if (remainingLeave.days() - vacationRequest.getVacationDays().size() < 0) {
            throw new InvalidVacationRequestException(InvalidVacationRequestException.InvalidVacationRequestReason.NOT_ENOUGH_VACATION_LEFT);
        }
        return vacationRequest;
    }
}
