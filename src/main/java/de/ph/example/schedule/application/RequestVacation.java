package de.ph.example.schedule.application;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedule.domain.InvalidVacationRequestException;
import de.ph.example.schedule.domain.VacationRequest;
import de.ph.example.schedule.domain.VacationRequests;
import de.ph.example.schedule.domain.VacationSpan;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequestVacation {

    private final VacationRequests vacationRequestRepository;
    private final Holidays holidayRepository;
    private final Employees employeeRepository;

    public RequestVacation(VacationRequests vacationRequestRepository, Holidays holidayRepository, Employees employeeRepository) {
        this.vacationRequestRepository = vacationRequestRepository;
        this.holidayRepository = holidayRepository;
        this.employeeRepository = employeeRepository;
    }

    public Mono<VacationRequest> with(EmployeeId employeeId, VacationSpan span) {
        return Mono.just(span)
                .map(this::findHolidays)
                .map(holidays -> {
                    VacationRequest vacationRequest = new VacationRequest(null, employeeId, span);
                    vacationRequest.calculateVacationDays(holidays);
                    validate(vacationRequest);
                    return vacationRequest;
                })
                .flatMap(vacationRequestRepository::save);
    }

    List<LocalDate> findHolidays(VacationSpan span) {
        LocalDate start = span.start();
        LocalDate end = span.end();
        List<LocalDate> holidays = new ArrayList<>(holidayRepository.findByYear(start.getYear()));
        if (start.getYear() != end.getYear()) {
            holidays.addAll(holidayRepository.findByYear(end.getYear()));
        }
        return holidays;
    }

    void validate(VacationRequest vacationRequest) {
        LocalDate start = vacationRequest.getSpan().start();
        LocalDate end = vacationRequest.getSpan().end();
        validateByDate(vacationRequest, start);
        if (start.getYear() != end.getYear()) {
            validateByDate(vacationRequest, end);
        }
    }

    private void validateByDate(VacationRequest vacationRequest, LocalDate date) {
        int leftOverVacationInStartingYear = employeeRepository.calculateLeftOverVacationDays(vacationRequest.getEmployeeId(), date.getYear());
        int countOfRequestedVacationInStartingYear = (int) vacationRequest.getVacationDays().stream().filter(vacationDay -> vacationDay.getYear() == vacationDay.getYear()).count();
        if (leftOverVacationInStartingYear == 0) {
            throw new InvalidVacationRequestException(InvalidVacationRequestException.InvalidVacationRequestReason.NO_MORE_VACATION_LEFT);
        } else if (countOfRequestedVacationInStartingYear > leftOverVacationInStartingYear) {
            throw new InvalidVacationRequestException(InvalidVacationRequestException.InvalidVacationRequestReason.NOT_ENOUGH_VACATION_LEFT);
        }
    }
}
