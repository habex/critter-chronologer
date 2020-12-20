package com.udacity.jdnd.critter.service;

import com.udacity.jdnd.critter.data.Employee;
import com.udacity.jdnd.critter.enums.EmployeeSkill;
import com.udacity.jdnd.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

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

    public List<Employee> findAllById(List<Long> empIds){
        if(empIds != null){
           return employeeRepository.findAllById(empIds);
        }
        return null;
    }

    public List<Employee> findByDateAndSkills(LocalDate date, Set<EmployeeSkill> skills){
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        Set<DayOfWeek> daySet = new HashSet<>();
        daySet.add(dayOfWeek);
        List<Employee> employees = employeeRepository.findByDaysAvailableInAndSkillsIn(daySet,skills);
        List<Employee> employeeList = new ArrayList<>();
        for(Employee employee: employees){
            if(employee.getSkills().containsAll(skills)){
                employeeList.add(employee);
            }
        }
        return employeeList;
    }
}
