package com.udacity.jdnd.critter.service;

import com.udacity.jdnd.critter.data.Employee;
import com.udacity.jdnd.critter.enums.EmployeeSkill;
import com.udacity.jdnd.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
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
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()){
            return optionalEmployee.get();
        }
        return null;
    }

    public List<Employee> findBySkills(Set<EmployeeSkill> skills){
        return employeeRepository.findBySkills(skills);
    }
}
