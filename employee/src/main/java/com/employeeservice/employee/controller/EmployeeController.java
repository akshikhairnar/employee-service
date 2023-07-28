package com.employeeservice.employee.controller;

import com.employeeservice.employee.dto.DepartmentDTO;
import com.employeeservice.employee.dto.EmployeeDTO;
import com.employeeservice.employee.dto.EmployeeDepartmetData;
import com.employeeservice.employee.entity.Employee;
import com.employeeservice.employee.mapper.EmployeeMapper;
import com.employeeservice.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee() {
        List<Employee> employees = employeeService.getAllEmployee();
        List<EmployeeDTO> employeeDTOList = employees.stream().map(EmployeeMapper::employeeDTOMapper).toList();
        return new ResponseEntity<>(employeeDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable("id") Long id) {
        Employee employee = employeeService.getEmployee(id);
        EmployeeDTO employeeDTO = EmployeeMapper.employeeDTOMapper(employee);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employeeToAdd = EmployeeMapper.employeeMapper(employeeDTO);
        Employee employee = employeeService.addEmployee(employeeToAdd);
        EmployeeDTO employeeDTOAdded = EmployeeMapper.employeeDTOMapper(employee);
        return new ResponseEntity<>(employeeDTOAdded, HttpStatus.OK);
    }

    //updating employee by id
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO, @PathVariable("id") Long id) {
        Employee employeeToUpdate = EmployeeMapper.employeeMapper(employeeDTO);
        Employee employee = employeeService.updateEmployee(employeeToUpdate, id);
        EmployeeDTO updatedEmployeeDTO = EmployeeMapper.employeeDTOMapper(employee);
        return new ResponseEntity<>(updatedEmployeeDTO, HttpStatus.OK);
    }

    // deleting all employees
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAllEmployees() {
        employeeService.deleteAllEmployees();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/add-employee-department")
    public ResponseEntity<EmployeeDTO> AddEmployeeDepartment(@RequestBody EmployeeDepartmetData employeeDepartmetData) {
        HttpHeaders headers=new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<DepartmentDTO> entity=new HttpEntity<DepartmentDTO>(headers);
        String httpReuest="http://localhost:8083/departments/"+employeeDepartmetData.getDepartmentId();
        DepartmentDTO department= restTemplate.exchange(httpReuest,HttpMethod.GET,entity,DepartmentDTO.class).getBody();
        Employee employee=employeeService.getEmployee(employeeDepartmetData.getEmployeeId());
        employee.setDepartmentId(department.getDepartmentId());
        Employee updatedEmployee= employeeService.updateEmployee(employee,employeeDepartmetData.getEmployeeId());
        return new ResponseEntity<EmployeeDTO>((MultiValueMap<String, String>) updatedEmployee,HttpStatus.OK);
    }


}
