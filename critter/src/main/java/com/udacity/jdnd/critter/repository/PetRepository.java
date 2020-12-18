package com.udacity.jdnd.critter.repository;

import com.udacity.jdnd.critter.data.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<List<Pet>> findByCustomerId (long ownerId);
}
