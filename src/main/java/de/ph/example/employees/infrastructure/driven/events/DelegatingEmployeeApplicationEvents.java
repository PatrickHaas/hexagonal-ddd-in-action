package de.ph.example.employees.infrastructure.driven.events;

import de.ph.example.employees.application.EmployeeApplicationEvents;
import de.ph.example.employees.domain.EmployeeFired;
import de.ph.example.employees.domain.EmployeeHired;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DelegatingEmployeeApplicationEvents implements EmployeeApplicationEvents {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void employeeHired(EmployeeHired event) {
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public void employeeFired(EmployeeFired event) {
        applicationEventPublisher.publishEvent(event);
    }
}
