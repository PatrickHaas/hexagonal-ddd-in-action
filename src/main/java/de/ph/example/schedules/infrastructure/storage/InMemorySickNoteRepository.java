package de.ph.example.schedules.infrastructure.storage;

import de.ph.example.schedules.application.SickNotes;
import de.ph.example.schedules.domain.SickNote;
import de.ph.example.schedules.domain.SickNoteId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemorySickNoteRepository implements SickNotes {

    private final Map<SickNoteId, SickNote> sickNotes = new HashMap<>();

    @Override
    public SickNote save(SickNote sickNote) {
        SickNoteId sickNoteId = Optional.ofNullable(sickNote.getId()).orElseGet(SickNoteId::random);
        SickNote savedSickNote = new SickNote(sickNoteId, sickNote.getEmployeeId(), sickNote.getDays());
        sickNotes.put(savedSickNote.getId(), savedSickNote);
        return savedSickNote;
    }
}
