package de.ph.example.schedules.application;

import de.ph.example.schedules.domain.VacationRequest;
import de.ph.example.schedules.domain.VacationRequestId;
import de.ph.example.schedules.domain.VacationRequestNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApproveVacationRequest {

    private final VacationRequestRepository vacationRequestRepository;

    public VacationRequest with(VacationRequestId id) {
        VacationRequest vacationRequest = vacationRequestRepository.findById(id).orElseThrow(() -> new VacationRequestNotFoundException(id));
        vacationRequest.approve();
        return vacationRequestRepository.save(vacationRequest);
    }

}
