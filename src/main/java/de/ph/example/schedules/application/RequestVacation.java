package de.ph.example.schedules.application;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedules.domain.RemainingLeave;
import de.ph.example.schedules.domain.TimePeriod;
import de.ph.example.schedules.domain.VacationRequest;
import de.ph.example.schedules.domain.VacationRequestFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class RequestVacation {

    private final CalculateRemainingLeave calculateRemainingLeave;
    private final VacationRequestFactory vacationRequestFactory;
    private final VacationRequestRepository vacationRequestRepository;
    private final HolidayRepository holidayRepository;

    public RequestVacation(CalculateRemainingLeave calculateRemainingLeave, VacationRequestFactory vacationRequestFactory, VacationRequestRepository vacationRequestRepository, HolidayRepository holidayRepository) {
        this.calculateRemainingLeave = calculateRemainingLeave;
        this.vacationRequestFactory = vacationRequestFactory;
        this.vacationRequestRepository = vacationRequestRepository;
        this.holidayRepository = holidayRepository;
    }

    public VacationRequest with(EmployeeId employeeId, TimePeriod period) {
        int[] years = Stream.of(period.start().getYear(), period.end().getYear()).mapToInt(Integer::intValue).distinct().toArray();
        List<LocalDate> holidays = holidayRepository.findByYears(years);
        List<RemainingLeave> remainingLeave = calculateRemainingLeave.with(employeeId, period);
        VacationRequest vacationRequest = vacationRequestFactory.create(employeeId, period, holidays, remainingLeave);
        return vacationRequestRepository.save(vacationRequest);
    }

}
