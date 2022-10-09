package de.ph.example.schedule.domain;

import de.ph.example.employees.domain.EmployeeId;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class VacationRequest {
    private final VacationRequestId id;
    private final EmployeeId employeeId;
    private final VacationSpan span;
    private List<LocalDate> vacationDays;
    private VacationRequestStatus status;

    public VacationRequest(VacationRequestId id, EmployeeId employeeId, VacationSpan vacationSpan) {
        this(id, employeeId, vacationSpan, null, VacationRequestStatus.CREATED);
    }

    public VacationRequest(VacationRequestId id, EmployeeId employeeId, VacationSpan vacationSpan, List<LocalDate> vacationDays, VacationRequestStatus status) {
        this.id = id;
        this.employeeId = employeeId;
        this.span = vacationSpan;
        this.vacationDays = vacationDays;
        this.status = status;
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
            vacationDays.add(possibleVacationDay);
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

    public VacationRequestId getId() {
        return id;
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public List<LocalDate> getVacationDays() {
        return vacationDays;
    }

    public VacationSpan getSpan() {
        return span;
    }

    public VacationRequestStatus getStatus() {
        return status;
    }
}
