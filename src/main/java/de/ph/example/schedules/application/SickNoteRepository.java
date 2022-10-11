package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.SickNote;

public interface SickNoteRepository {
    SickNote save(SickNote sickNote);
}
