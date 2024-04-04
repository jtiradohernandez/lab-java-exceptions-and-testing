package com.example.Spring.service;


import com.example.Spring.EmployeeStatus;
import com.example.Spring.model.Employee;
import com.example.Spring.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeId(int ID) {
        return employeeRepository.findByEmployeeId(ID);
    }

    public List<Employee> getEmployeesByStatus(Optional<EmployeeStatus> status) {
        return employeeRepository.findByStatus(status);
    }

    public List<Employee> getEmployeesByDepartment(Optional<String> department) {
        return employeeRepository.findAllByDepartment(department);
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateStatus(int id, EmployeeStatus status){
        Employee employee = employeeRepository.findByEmployeeId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        employee.setStatus(status);
        employeeRepository.save(employee);
        return employee;
    }

    public Employee updateDepartment(int id, String department) {
        Employee employee = employeeRepository.findByEmployeeId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        employee.setDepartment(department);
        employeeRepository.save(employee);
        return employee;
    }
}
