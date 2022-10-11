package de.ph.example.schedules.domain;

import de.ph.example.employees.domain.EmployeeId;
import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@AggregateRoot
public class SickNote {
    @Identity
    @Getter
    private final SickNoteId id;
    @Getter
    private final EmployeeId employeeId;
    @Getter
    private final List<LocalDate> days;

    public SickNote(SickNoteId id, EmployeeId employeeId, List<LocalDate> days) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Sick note must be associated with an employee");
        }
        if (days == null || days.isEmpty()) {
            throw new IllegalArgumentException("Sick note must at least contain one day");
        }
        this.id = id;
        this.employeeId = employeeId;
        this.days = Collections.unmodifiableList(days);
    }
}
