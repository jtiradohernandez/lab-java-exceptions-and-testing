package com.example.Spring.controller;

import com.example.Spring.EmployeeDTO;
import com.example.Spring.EmployeeStatus;
import com.example.Spring.EmployeeDTO;
import com.example.Spring.model.Employee;
import com.example.Spring.model.Patient;
import com.example.Spring.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployees(@RequestParam Optional<EmployeeStatus> status,@RequestParam Optional<String> department) {
        if (status.isPresent()) {
            return employeeService.getEmployeesByStatus(status);
        }else if(department.isPresent()){
            return employeeService.getEmployeesByDepartment(department);
        }else{
            return employeeService.getAllEmployees();
        }
    }

    @GetMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Employee> getEmployeeByID(@PathVariable(name="id") int employeeId) {
        return employeeService.getEmployeeId(employeeId);
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody @Valid Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @PatchMapping("/employees/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Employee partialUpdateStatus(@PathVariable(name="id") int employeeId, @RequestBody EmployeeDTO employeeDTO) {
        if(employeeDTO.getStatus() != null) {
            return employeeService.updateStatus(employeeId, employeeDTO.getStatus());
        }
        else if(employeeDTO.getDepartment() != null){
            return employeeService.updateDepartment(employeeId, employeeDTO.getDepartment());
        }else {
            return null;
        }
    }
}




