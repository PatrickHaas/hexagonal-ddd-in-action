package de.ph.example.employees.application;

import de.ph.example.employees.domain.EmployeeFired;
import de.ph.example.employees.domain.EmployeeHired;

public interface ApplicationEvents {

    void employeeHired(EmployeeHired event);

    void employeeFired(EmployeeFired event);

}
