package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.VacationRequest;
import de.ph.example.schedules.domain.VacationRequestId;
import de.ph.example.schedules.domain.VacationRequestNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.annotation.Service;

@Service
@RequiredArgsConstructor
public class DeclineVacationRequest {

    private final VacationRequests vacationRequests;

    public VacationRequest with(VacationRequestId id) {
        VacationRequest vacationRequest = vacationRequests.findById(id).orElseThrow(() -> new VacationRequestNotFoundException(id));
        vacationRequest.decline();
        return vacationRequests.save(vacationRequest);
    }

}
