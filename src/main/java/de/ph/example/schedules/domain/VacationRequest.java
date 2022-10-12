package de.ph.example.schedules.domain;

import de.ph.example.shared.DatePeriod;
import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AggregateRoot
public class VacationRequest {
    @Identity
    @Getter
    private final VacationRequestId id;
    @Getter
    private final EmployeeId employeeId;
    @Getter
    private final DatePeriod period;
    @Getter
    private VacationRequestStatus status;
    private List<VacationDay> vacationDays;

    public VacationRequest(VacationRequestId id, EmployeeId employeeId, DatePeriod vacationPeriod, List<LocalDate> holidays, List<RemainingLeave> remainingLeaves) {
        this(id, employeeId, vacationPeriod, null, VacationRequestStatus.CREATED);
        calculateVacationDays(holidays);
        // Validate remaining leave
        if (remainingLeaves.stream().anyMatch(remainingLeave -> remainingLeave.days() == 0)) {
            throw new InvalidVacationRequestException(InvalidVacationRequestException.InvalidVacationRequestReason.NO_MORE_VACATION_LEFT);
        } else if (remainingLeaves.stream().anyMatch(remainingLeave -> remainingLeave.days() - getVacationDaysByYear(remainingLeave.year()).size() < 0)) {
            throw new InvalidVacationRequestException(InvalidVacationRequestException.InvalidVacationRequestReason.NOT_ENOUGH_VACATION_LEFT);
        }
    }

    public VacationRequest(VacationRequestId id, EmployeeId employeeId, DatePeriod vacationPeriod, List<VacationDay> vacationDays, VacationRequestStatus status) {
        this.id = id;
        this.employeeId = employeeId;
        this.period = vacationPeriod;
        this.vacationDays = vacationDays;
        this.status = status;
    }

    public boolean matchesYear(int year) {
        return period.matchesYear(year);
    }

    private void calculateVacationDays(List<LocalDate> holidays) {
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

    public List<VacationDay> getVacationDaysByYear(int year) {
        return getVacationDays().stream().filter(vacationDay -> vacationDay.value().getYear() == year).toList();
    }
}
