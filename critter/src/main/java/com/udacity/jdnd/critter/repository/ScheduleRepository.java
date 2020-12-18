package com.udacity.jdnd.critter.repository;

import com.udacity.jdnd.critter.data.Employee;
import com.udacity.jdnd.critter.data.Pet;
import com.udacity.jdnd.critter.data.Schedule;
import com.udacity.jdnd.critter.enums.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    @Query("SELECT s FROM Schedule s WHERE :pet member of s.pets")
    List<Schedule> findByPet(@Param("pet") Pet pet);

    @Query("SELECT s FROM Schedule s WHERE :employee member of s.employees")
    Set<Schedule> findByEmployee(@Param("employee") Employee employee);

    @Query("SELECT s FROM Schedule s WHERE s.date = :date" )
    List<Schedule> findByDate(LocalDate date);

    //List<Employee> findByActivities(Set<EmployeeSkill> activities);
    //@Query("SELECT s FROM Schedule s WHERE s.date = :date AND :activities member of s.activities")

    @Query("SELECT s FROM Schedule s WHERE s.date = :date And s.activities = :activities")
    List<Schedule> findByDateAndActivities(@Param("date") LocalDate date, @Param("activities") Set<EmployeeSkill> activities);

    @Query("SELECT s.employees FROM Schedule s WHERE s.date = :date ")
    List<Employee> findEmployeesByDate(@Param("date") LocalDate date);

}
