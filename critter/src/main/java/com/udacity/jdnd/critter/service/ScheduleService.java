package com.udacity.jdnd.critter.service;

import com.udacity.jdnd.critter.data.Employee;
import com.udacity.jdnd.critter.data.Pet;
import com.udacity.jdnd.critter.data.Schedule;
import com.udacity.jdnd.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Long save(Schedule schedule){
        return scheduleRepository.save(schedule).getId();
    }

    public List<Schedule> findAll(){
        return scheduleRepository.findAll();
    }

    public Schedule findById (Long id){
        return scheduleRepository.findById(id).get();
    }

    public List<Schedule> findByDate(LocalDate date){
        return scheduleRepository.findByDate(date);
    }

    public List<Employee> findEmployeesByDate(LocalDate date){
        return scheduleRepository.findEmployeesByDate(date);
    }

    public List<Schedule> findByPet(Pet pet){
        return scheduleRepository.findByPets(pet);
    }

    public List<Schedule> findByEmployee(Employee employee){
        return scheduleRepository.findAllByEmployees(employee);
    }
}
