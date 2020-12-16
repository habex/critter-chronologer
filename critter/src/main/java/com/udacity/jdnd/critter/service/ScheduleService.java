package com.udacity.jdnd.critter.service;

import com.udacity.jdnd.critter.data.Schedule;
import com.udacity.jdnd.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
}
