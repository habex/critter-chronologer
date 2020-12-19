package com.udacity.jdnd.critter.repository;

import com.udacity.jdnd.critter.data.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<List<Pet>> findByCustomerId (long ownerId);
}
