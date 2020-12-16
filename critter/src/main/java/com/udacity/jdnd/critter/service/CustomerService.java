package com.udacity.jdnd.critter.service;

import com.udacity.jdnd.critter.data.Customer;
import com.udacity.jdnd.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        return customerRepository.findById(id).get();
    }

}
