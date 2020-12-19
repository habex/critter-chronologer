package com.udacity.jdnd.critter.repository;

import com.udacity.jdnd.critter.data.Employee;
import com.udacity.jdnd.critter.data.Pet;
import com.udacity.jdnd.critter.data.Schedule;
import com.udacity.jdnd.critter.enums.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    //@Query("SELECT s FROM Schedule s WHERE :pet member of s.pets")
    List<Schedule> findByPets( Pet pet);

    //@Query("SELECT s FROM Schedule s WHERE :employee member of s.employees")
    List<Schedule> findAllByEmployees(Employee employee);

    //List<Schedule> findAllByEmployees(Employee employee);

    @Query("SELECT s FROM Schedule s WHERE s.date = :date" )
    List<Schedule> findByDate(LocalDate date);

    @Query("SELECT s.employees FROM Schedule s WHERE s.date = :date ")
    List<Employee> findEmployeesByDate(@Param("date") LocalDate date);

}
