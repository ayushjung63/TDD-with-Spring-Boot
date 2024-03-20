package com.ayush.tdd.service.impl;

import com.ayush.tdd.exception.ResourceNotFoundException;
import com.ayush.tdd.model.Customer;
import com.ayush.tdd.repo.CustomerRepo;
import com.ayush.tdd.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;

    @Override
    public Customer getCustomer(Integer id) {
        return customerRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Customer not found with id: "+id)
        );
    }
}
