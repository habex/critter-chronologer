package com.udacity.jdnd.critter.service;

import com.udacity.jdnd.critter.data.Pet;
import com.udacity.jdnd.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
        Optional<Pet> optionalPet = petRepository.findById(id);
        if (optionalPet.isPresent()){
            return optionalPet.get();
        }
        return null;
    }

    public List<Pet> findAllById(List<Long> petIds){
        if(petIds != null){
           return petRepository.findAllById(petIds);
        }
        return null;
    }

    public List<Pet> findByCustomerId(long ownerId){
        Optional<List<Pet>> pets = petRepository.findByCustomerId(ownerId);
        if(pets.isPresent()){
            return pets.get();
        }
        return null;
    }
}
