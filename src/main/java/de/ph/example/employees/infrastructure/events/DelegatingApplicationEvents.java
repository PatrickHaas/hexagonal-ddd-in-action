package de.ph.example.employees.infrastructure.events;

import de.ph.example.employees.application.ApplicationEvents;
import de.ph.example.employees.domain.EmployeeFired;
import de.ph.example.employees.domain.EmployeeHired;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DelegatingApplicationEvents implements ApplicationEvents {

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
