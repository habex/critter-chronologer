package com.udacity.jdnd.critter.repository;

import com.udacity.jdnd.critter.data.Employee;
import com.udacity.jdnd.critter.enums.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE :skill  in elements(e.skills)")
    List<Employee> findBySkills(@Param("skill") Set<EmployeeSkill> skills);
}
