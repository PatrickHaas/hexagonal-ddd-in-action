package de.ph.example.schedule.application;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedule.domain.RemainingLeave;

public class CalculateRemainingLeave {

    private final VacationRequestRepository vacationRequestRepository;

    public CalculateRemainingLeave(VacationRequestRepository vacationRequestRepository) {
        this.vacationRequestRepository = vacationRequestRepository;
    }

    RemainingLeave with(EmployeeId employeeId, int... years) {
        return new RemainingLeave(employeeId, vacationRequestRepository.findByEmployeeIdAndYear(employeeId, years)
                .stream().map(vacationRequests -> vacationRequests.getVacationDays().size())
                .reduce(Integer::sum)
                .orElse(0));
    }


}
