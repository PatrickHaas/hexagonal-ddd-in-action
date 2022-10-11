package de.ph.example.schedules.application;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedules.domain.PermittedLeave;
import de.ph.example.schedules.domain.RemainingLeave;
import de.ph.example.schedules.domain.TimePeriod;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class CalculateRemainingLeave {

    private final VacationRequestRepository vacationRequestRepository;
    private final EmployeeRepository employeeRepository;

    List<RemainingLeave> with(EmployeeId employeeId, TimePeriod period) {
        PermittedLeave permittedLeave = employeeRepository.calculatePermittedLeave(employeeId);
        Set<Integer> years = Set.of(period.start().getYear(), period.end().getYear());
        List<RemainingLeave> remainingLeaves = new ArrayList<>();
        for (int year : years.stream().sorted().toList()) {
            remainingLeaves.add(new RemainingLeave(employeeId, year, permittedLeave.days() - vacationRequestRepository.findByEmployeeIdAndYear(employeeId, year)
                    .stream().map(vacationRequests -> vacationRequests.getVacationDays().size())
                    .reduce(Integer::sum).orElse(0)));
        }
        return remainingLeaves;

    }


}
