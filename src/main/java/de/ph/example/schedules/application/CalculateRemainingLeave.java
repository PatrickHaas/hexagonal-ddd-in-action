package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.EmployeeId;
import de.ph.example.schedules.domain.PermittedLeave;
import de.ph.example.schedules.domain.RemainingLeave;
import de.ph.example.schedules.domain.DatePeriod;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.annotation.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CalculateRemainingLeave {

    private final VacationRequests vacationRequests;
    private final Employees employees;

    List<RemainingLeave> with(EmployeeId employeeId, DatePeriod period) {
        PermittedLeave permittedLeave = employees.calculatePermittedLeave(employeeId);
        Set<Integer> years = Set.of(period.start().getYear(), period.end().getYear());
        List<RemainingLeave> remainingLeaves = new ArrayList<>();
        for (int year : years.stream().sorted().toList()) {
            remainingLeaves.add(new RemainingLeave(employeeId, year, permittedLeave.days() - vacationRequests.findByEmployeeIdAndYear(employeeId, year)
                    .stream().map(vacationRequests -> vacationRequests.getVacationDays().size())
                    .reduce(Integer::sum).orElse(0)));
        }
        return remainingLeaves;

    }


}
