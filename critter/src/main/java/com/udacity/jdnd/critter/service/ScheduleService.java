package com.udacity.jdnd.critter.service;

import com.udacity.jdnd.critter.data.Employee;
import com.udacity.jdnd.critter.data.Pet;
import com.udacity.jdnd.critter.data.Schedule;
import com.udacity.jdnd.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public Schedule findById(Long id) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        if (optionalSchedule.isPresent()) {
            return optionalSchedule.get();
        }
        return null;
    }

    public List<Schedule> findByPet(Pet pet) {
        Optional<List<Schedule>> optionalPets = scheduleRepository.findAllByPets(pet);
        if (optionalPets.isPresent()) {
            return optionalPets.get();
        }
        return null;
    }

    public List<Schedule> findByEmployee(Employee employee) {
        Optional<List<Schedule>> optionalPets = scheduleRepository.findAllByEmployees(employee);
        if (optionalPets.isPresent()) {
            return optionalPets.get();
        }
        return null;
    }
}
