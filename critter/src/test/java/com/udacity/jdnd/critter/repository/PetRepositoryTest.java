package com.udacity.jdnd.critter.repository;


import com.udacity.jdnd.critter.data.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;

    Pet pet = new Pet();


    public void before(){

    }

    @Test
    public void save() {

    }

    @Test
    public void getByName() {

    }

}
