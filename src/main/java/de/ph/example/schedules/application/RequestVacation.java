package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.DatePeriod;
import de.ph.example.schedules.domain.EmployeeId;
import de.ph.example.schedules.domain.RemainingLeave;
import de.ph.example.schedules.domain.VacationRequest;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.annotation.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestVacation {

    private final CalculateRemainingLeave calculateRemainingLeave;
    private final VacationRequests vacationRequests;
    private final Holidays holidays;

    public VacationRequest with(EmployeeId employeeId, DatePeriod period) {
        List<LocalDate> holidays = this.holidays.findByPeriod(period);
        List<RemainingLeave> remainingLeave = calculateRemainingLeave.with(employeeId, period);
        VacationRequest vacationRequest = new VacationRequest(null, employeeId, period, holidays, remainingLeave);
        return vacationRequests.save(vacationRequest);
    }

}
