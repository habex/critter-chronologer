package com.udacity.jdnd.critter.controller;

import com.udacity.jdnd.critter.data.Customer;
import com.udacity.jdnd.critter.data.Pet;
import com.udacity.jdnd.critter.dto.PetDTO;
import com.udacity.jdnd.critter.service.CustomerService;
import com.udacity.jdnd.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;
    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToPet(petDTO, new Pet());
        petService.save(pet);
        //convert pet to petDTO and return
        PetDTO petDTO1 = convertPetToPetDTO(pet, new PetDTO());
        return petDTO1;
    }

    @PutMapping("/{petId}")
    public void updatePet(@RequestBody PetDTO petDTO, @PathVariable long petId) {
        Pet pet = petService.findById(petId);
        convertPetDTOToPet(petDTO, pet);
        petService.save(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.findById(petId);
        return convertPetToPetDTO(pet, new PetDTO());
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<PetDTO> petDTOs = new ArrayList<>();
        petService.findAll().forEach(pet -> petDTOs.add(convertPetToPetDTO(pet, new PetDTO())));
        return petDTOs;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> petDTOs = new ArrayList<>();
        petService.findByCustomerId(ownerId).forEach(pet -> petDTOs.add(convertPetToPetDTO(pet, new PetDTO())));
        return petDTOs;
    }

    //<editor-fold desc="DTO converters">
    private PetDTO convertPetToPetDTO(Pet pet, PetDTO petDTO) {
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getCustomer() != null) {
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO, Pet pet) {
        //Copy everything
        BeanUtils.copyProperties(petDTO, pet);
        if (customerService.findById(petDTO.getOwnerId()) == null) {
            return null;
        }
        //Setting ownerId to PetDTO and customer to Pet
        Customer owner = customerService.findById(petDTO.getOwnerId());
        pet.setCustomer(owner);
        owner.getPets().add(pet);
        return pet;
    }
    //</editor-fold>
}
