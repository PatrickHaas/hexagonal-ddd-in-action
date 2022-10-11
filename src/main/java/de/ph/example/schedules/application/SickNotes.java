package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.SickNote;
import org.jmolecules.ddd.annotation.Repository;

@Repository
public interface SickNotes {
    SickNote save(SickNote sickNote);
}
