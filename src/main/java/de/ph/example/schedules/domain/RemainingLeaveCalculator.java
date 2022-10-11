package de.ph.example.schedules.domain;

import de.ph.example.employees.domain.EmployeeId;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class RemainingLeaveCalculator {

    public List<RemainingLeave> byPeriod(EmployeeId employeeId, TimePeriod period, PermittedLeave permittedLeave, BiFunction<EmployeeId, Integer, Integer> calculateTakenLeave) {
        Set<Integer> years = Set.of(period.start().getYear(), period.end().getYear());
        List<RemainingLeave> remainingLeaves = new ArrayList<>();
        for (int year : years.stream().sorted().toList()) {
            remainingLeaves.add(new RemainingLeave(employeeId, year, permittedLeave.days() - calculateTakenLeave.apply(employeeId, year)));
        }
        return remainingLeaves;
    }

}
