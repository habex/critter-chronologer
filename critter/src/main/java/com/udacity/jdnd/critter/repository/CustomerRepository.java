package com.udacity.jdnd.critter.repository;

import com.udacity.jdnd.critter.data.Customer;
import com.udacity.jdnd.critter.data.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
      Optional<Customer> findByPets( Pet pet);
}
