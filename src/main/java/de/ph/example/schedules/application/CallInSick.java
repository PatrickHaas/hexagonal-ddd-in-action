package de.ph.example.schedules.application;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedules.domain.SickNote;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class CallInSick {

    private final SickNoteRepository sickNoteRepository;

    SickNote with(EmployeeId employeeId, LocalDate... dates) {
        return with(employeeId, List.of(dates));
    }

    SickNote with(EmployeeId employeeId, List<LocalDate> dates) {
        SickNote sickNote = new SickNote(null, employeeId, dates);
        return sickNoteRepository.save(sickNote);
    }

}
