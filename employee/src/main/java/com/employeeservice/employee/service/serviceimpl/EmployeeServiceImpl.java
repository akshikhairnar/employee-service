package com.employeeservice.employee.service.serviceimpl;

import com.employeeservice.employee.entity.Employee;
import com.employeeservice.employee.exceptions.employeeexceptions.EmployeeAlreadyExistsException;
import com.employeeservice.employee.exceptions.employeeexceptions.NoSuchEmployeeExistsException;
import com.employeeservice.employee.repository.EmployeeRepository;
import com.employeeservice.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployee(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new NoSuchEmployeeExistsException("No Employee Exist With Id : " + id));
    }

    @Override
    public Employee addEmployee(Employee employee) {
        Employee existEmployee = getEmployee(employee.getEmpId());
        if (existEmployee == null) {
            return employeeRepository.save(employee);
        } else {
            throw new EmployeeAlreadyExistsException("Employee Already Exist With Id : " + existEmployee.getEmpId());
        }
    }

    @Override
    public Employee updateEmployee(Employee employee, Long id) {
        Employee existedEmployee = getEmployee(id);
        if (existedEmployee != null) {
            return employeeRepository.save(employee);
        }
        return null;
    }

    @Override
    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }


}
