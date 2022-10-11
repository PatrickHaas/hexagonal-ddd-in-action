package de.ph.example.schedules.application;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedules.domain.SickNote;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.annotation.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CallInSick {

    private final SickNotes sickNotes;

    SickNote with(EmployeeId employeeId, LocalDate... dates) {
        return with(employeeId, List.of(dates));
    }

    SickNote with(EmployeeId employeeId, List<LocalDate> dates) {
        SickNote sickNote = new SickNote(null, employeeId, dates);
        return sickNotes.save(sickNote);
    }

}
