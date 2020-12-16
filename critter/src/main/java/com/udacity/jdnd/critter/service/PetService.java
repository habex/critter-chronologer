package com.udacity.jdnd.critter.service;

import com.udacity.jdnd.critter.data.Pet;
import com.udacity.jdnd.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    public Long save(Pet pet) {
        return petRepository.save(pet).getId();
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Pet findById(Long id) {
        return petRepository.findById(id).get();
    }

}
