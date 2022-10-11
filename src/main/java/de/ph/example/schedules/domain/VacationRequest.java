package de.ph.example.schedules.domain;

import de.ph.example.employees.domain.EmployeeId;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VacationRequest {
    @Getter
    private final VacationRequestId id;
    @Getter
    private final EmployeeId employeeId;
    @Getter
    private final VacationPeriod period;
    @Getter
    private VacationRequestStatus status;
    private List<VacationDay> vacationDays;

    public VacationRequest(VacationRequestId id, EmployeeId employeeId, VacationPeriod vacationPeriod) {
        this(id, employeeId, vacationPeriod, null, VacationRequestStatus.CREATED);
    }

    public VacationRequest(VacationRequestId id, EmployeeId employeeId, VacationPeriod vacationPeriod, List<VacationDay> vacationDays, VacationRequestStatus status) {
        this.id = id;
        this.employeeId = employeeId;
        this.period = vacationPeriod;
        this.vacationDays = vacationDays;
        this.status = status;
    }

    public boolean matchesYear(int year) {
        return period.matchesYear(year);
    }

    // TODO A vacation request is not automatically valid since we need the list of holidays to calculate the vacation days of this request, may be pass the holidays to the constructor?
    public void calculateVacationDays(List<LocalDate> holidays) {
        vacationDays = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(period.start(), period.end());
        for (int index = 0; index <= daysBetween; index++) {
            LocalDate possibleVacationDay = period.start().plusDays(index);
            if (List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(possibleVacationDay.getDayOfWeek())
                    || holidays.contains(possibleVacationDay)) {
                continue;
            }
            vacationDays.add(new VacationDay(possibleVacationDay));
        }
        if (vacationDays.isEmpty()) {
            throw new IllegalArgumentException("Vacation request must at least contain one vacation day");
        }
    }

    public void approve() {
        if (status == null || status == VacationRequestStatus.CREATED) {
            this.status = VacationRequestStatus.APPROVED;
        } else {
            throw new IllegalVacationRequestStatusChangeException(status, VacationRequestStatus.APPROVED);
        }
    }

    public void decline() {
        if (status == null || status == VacationRequestStatus.CREATED) {
            this.status = VacationRequestStatus.DECLINED;
        } else {
            throw new IllegalVacationRequestStatusChangeException(status, VacationRequestStatus.DECLINED);
        }
    }

    public List<VacationDay> getVacationDays() {
        return vacationDays == null ? Collections.emptyList() : Collections.unmodifiableList(vacationDays);
    }
}
