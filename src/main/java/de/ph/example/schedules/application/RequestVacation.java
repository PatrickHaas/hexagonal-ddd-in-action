package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.*;
import org.jmolecules.ddd.annotation.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RequestVacation {

    private final CalculateRemainingLeave calculateRemainingLeave;
    private final VacationRequestFactory vacationRequestFactory;
    private final VacationRequests vacationRequests;
    private final Holidays holidays;

    public RequestVacation(CalculateRemainingLeave calculateRemainingLeave, VacationRequestFactory vacationRequestFactory, VacationRequests vacationRequests, Holidays holidays) {
        this.calculateRemainingLeave = calculateRemainingLeave;
        this.vacationRequestFactory = vacationRequestFactory;
        this.vacationRequests = vacationRequests;
        this.holidays = holidays;
    }

    public VacationRequest with(EmployeeId employeeId, TimePeriod period) {
        List<LocalDate> holidays = this.holidays.findByPeriod(period);
        List<RemainingLeave> remainingLeave = calculateRemainingLeave.with(employeeId, period);
        VacationRequest vacationRequest = vacationRequestFactory.create(employeeId, period, holidays, remainingLeave);
        return vacationRequests.save(vacationRequest);
    }

}
