package de.ph.example.schedules.application;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedules.domain.PermittedLeave;
import de.ph.example.schedules.domain.RemainingLeave;
import de.ph.example.schedules.domain.RemainingLeaveCalculator;
import de.ph.example.schedules.domain.TimePeriod;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CalculateRemainingLeave {

    private final VacationRequestRepository vacationRequestRepository;
    private final EmployeeRepository employeeRepository;

    private final RemainingLeaveCalculator remainingLeaveCalculator = new RemainingLeaveCalculator();

    List<RemainingLeave> with(EmployeeId employeeId, TimePeriod period) {
        PermittedLeave permittedLeave = employeeRepository.calculatePermittedLeave(employeeId);
        return remainingLeaveCalculator.byPeriod(employeeId, period, permittedLeave, (e, year) -> vacationRequestRepository.findByEmployeeIdAndYear(e, year)
                .stream().map(vacationRequests -> vacationRequests.getVacationDays().size())
                .reduce(Integer::sum).orElse(0));
    }


}
