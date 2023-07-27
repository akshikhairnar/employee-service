package com.employeeservice.employee.service;

import com.employeeservice.employee.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployee();

    Employee getEmployee(Long id);

    Employee addEmployee(Employee e);

    Employee updateEmployee(Employee e, Long id);

    void deleteAllEmployees();


}
