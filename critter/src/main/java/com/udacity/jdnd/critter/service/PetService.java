package com.udacity.jdnd.critter.service;

import com.udacity.jdnd.critter.data.Customer;
import com.udacity.jdnd.critter.data.Pet;
import com.udacity.jdnd.critter.data.Schedule;
import com.udacity.jdnd.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerService customerService;

    public Long save(Pet pet) {
        petRepository.save(pet);
        addPetToCustomer(pet, pet.getCustomer());
        return pet.getId();
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Pet findById(Long id) {
        Optional<Pet> optionalPet = petRepository.findById(id);
        if (optionalPet.isPresent()) {
            return optionalPet.get();
        }
        return null;
    }

    public List<Pet> findAllById(List<Long> petIds) {
        if (petIds != null) {
            return petRepository.findAllById(petIds);
        }
        return null;
    }

    public List<Pet> findAllByCustomer(Customer customer) {
        Optional<List<Pet>> pets = petRepository.findAllByCustomer(customer);
        if (pets.isPresent()) {
            return pets.get();
        }
        return null;
    }

    public void addScheduleForPet(List<Pet> petList, Schedule schedule) {
        petList = new CopyOnWriteArrayList<>(petList);
        if (petList != null) {
            for (Pet pet : petList) {
                Set<Schedule> schedules = pet.getSchedules();
                if (schedules != null) {
                    schedules.add(schedule);
                } else {
                    schedules = new HashSet<>();
                    schedules.add(schedule);
                }
                pet.setSchedules(schedules);
                petRepository.save(pet);
            }
        }
    }

    public void addPetToCustomer(Pet pet, Customer customer) {
        if (customer != null) {
            List<Pet> pets = customer.getPets();
            if (pets != null) {
                pets.add(pet);
            } else {
                pets = new ArrayList<>();
                pets.add(pet);
            }
            customer.setPets(pets);
        }
    }
}
