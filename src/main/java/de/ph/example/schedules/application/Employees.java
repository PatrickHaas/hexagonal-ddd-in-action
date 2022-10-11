package de.ph.example.schedules.application;

import de.ph.example.employees.domain.EmployeeId;
import de.ph.example.schedules.domain.PermittedLeave;
import org.jmolecules.ddd.annotation.Repository;

@Repository
public interface Employees {

    PermittedLeave calculatePermittedLeave(EmployeeId employeeId);
}
