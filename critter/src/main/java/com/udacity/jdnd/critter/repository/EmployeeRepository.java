package com.udacity.jdnd.critter.repository;

import com.udacity.jdnd.critter.data.Employee;
import com.udacity.jdnd.critter.enums.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDaysAvailableInAndSkillsIn(Set<DayOfWeek> daysAvailable, Set<EmployeeSkill> skills);

}
