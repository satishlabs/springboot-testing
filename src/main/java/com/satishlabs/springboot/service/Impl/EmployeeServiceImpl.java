package com.satishlabs.springboot.service.Impl;

import com.satishlabs.springboot.exception.ResourceNotFoundException;
import com.satishlabs.springboot.model.Employee;
import com.satishlabs.springboot.repository.EmployeeRepository;
import com.satishlabs.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    //
    //
    // @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
       Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
       if(savedEmployee.isPresent()){
           throw  new ResourceNotFoundException("Employee already exist with given email "+employee.getEmail());
       }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee updatedEmployee) {
        return employeeRepository.save(updatedEmployee);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }
}
