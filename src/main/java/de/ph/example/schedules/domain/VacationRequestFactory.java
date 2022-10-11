package de.ph.example.schedules.domain;

import de.ph.example.employees.domain.EmployeeId;
import org.jmolecules.ddd.annotation.Factory;

import java.time.LocalDate;
import java.util.List;

@Factory
public class VacationRequestFactory {

    // TODO not sure if the factory should ensure rules, that can not be ensured by the entity
    public VacationRequest create(EmployeeId employeeId, TimePeriod period, List<LocalDate> holidays, List<RemainingLeave> remainingLeaves) {
        VacationRequest vacationRequest = new VacationRequest(null, employeeId, period);
        vacationRequest.calculateVacationDays(holidays);

        // Validate remaining leave
        if (remainingLeaves.stream().anyMatch(remainingLeave -> remainingLeave.days() == 0)) {
            throw new InvalidVacationRequestException(InvalidVacationRequestException.InvalidVacationRequestReason.NO_MORE_VACATION_LEFT);
        } else if (remainingLeaves.stream().anyMatch(remainingLeave -> remainingLeave.days() - vacationRequest.getVacationDays().size() < 0)) {
            throw new InvalidVacationRequestException(InvalidVacationRequestException.InvalidVacationRequestReason.NOT_ENOUGH_VACATION_LEFT);
        }
        return vacationRequest;
    }
}
