package com.udacity.jdnd.critter.service;

import com.udacity.jdnd.critter.data.Customer;
import com.udacity.jdnd.critter.data.Pet;
import com.udacity.jdnd.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Long save(Customer customer) {
        return customerRepository.save(customer).getId();
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(optionalCustomer.isPresent()){
            return optionalCustomer.get();
        }
        return null;
    }
    public Customer findByPet(Pet pet) {
        Optional<Customer> optionalCustomer = customerRepository.findByPets(pet);
        if(optionalCustomer.isPresent()){
            return optionalCustomer.get();
        }
        return null;
    }

}
