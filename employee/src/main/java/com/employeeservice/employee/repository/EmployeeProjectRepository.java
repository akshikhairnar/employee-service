package com.employeeservice.employee.repository;

import com.employeeservice.employee.entity.EmployeeProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeProjectRepository  extends JpaRepository<EmployeeProject, Long> {
}
