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
    private final VacationSpan span;
    @Getter
    private VacationRequestStatus status;
    private List<VacationDay> vacationDays;

    public VacationRequest(VacationRequestId id, EmployeeId employeeId, VacationSpan vacationSpan) {
        this(id, employeeId, vacationSpan, null, VacationRequestStatus.CREATED);
    }

    public VacationRequest(VacationRequestId id, EmployeeId employeeId, VacationSpan vacationSpan, List<VacationDay> vacationDays, VacationRequestStatus status) {
        this.id = id;
        this.employeeId = employeeId;
        this.span = vacationSpan;
        this.vacationDays = vacationDays;
        this.status = status;
    }

    public boolean matchesYear(int year) {
        return span.matchesYear(year);
    }

    public void calculateVacationDays(List<LocalDate> holidays) {
        vacationDays = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(span.start(), span.end());
        for (int index = 0; index <= daysBetween; index++) {
            LocalDate possibleVacationDay = span.start().plusDays(index);
            if (List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(possibleVacationDay.getDayOfWeek())
                    || holidays.contains(possibleVacationDay)) {
                continue;
            }
            vacationDays.add(new VacationDay(possibleVacationDay));
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
