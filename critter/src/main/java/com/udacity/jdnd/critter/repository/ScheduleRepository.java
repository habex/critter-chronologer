package com.udacity.jdnd.critter.repository;

import com.udacity.jdnd.critter.data.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
}
