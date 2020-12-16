package com.udacity.jdnd.critter.repository;

import com.udacity.jdnd.critter.data.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
