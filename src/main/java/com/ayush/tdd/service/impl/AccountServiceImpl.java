package com.ayush.tdd.service.impl;

import com.ayush.tdd.exception.ResourceNotFoundException;
import com.ayush.tdd.model.Account;
import com.ayush.tdd.repo.AccountRepo;
import com.ayush.tdd.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;

    @Override
    public Account getAccount(Integer id) {
        return accountRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Account not found with id: "+id)
        );
    }

    @Override
    public Account getAccountByCustomerId(Integer customerId) {
        return accountRepo.findByCustomerId(customerId).orElseThrow(
                ()-> new ResourceNotFoundException("Account not found with customer id: "+customerId)
        );
    }
}
