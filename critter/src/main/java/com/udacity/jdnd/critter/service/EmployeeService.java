package com.udacity.jdnd.critter.service;

import com.udacity.jdnd.critter.data.Customer;
import com.udacity.jdnd.critter.data.Employee;
import com.udacity.jdnd.critter.repository.CustomerRepository;
import com.udacity.jdnd.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Long save(Employee employee) {
        return employeeRepository.save(employee).getId();
    }

    public List<Employee> employees() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).get();
    }

}
