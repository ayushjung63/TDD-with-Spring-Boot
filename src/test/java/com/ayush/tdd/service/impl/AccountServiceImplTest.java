package com.ayush.tdd.service.impl;

import com.ayush.tdd.exception.ResourceNotFoundException;
import com.ayush.tdd.model.Account;
import com.ayush.tdd.repo.AccountRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepo accountRepo;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void getAccount_throwException_whenAccountNotFound(){
        Integer id=1;
        Mockito.when(accountRepo.findById(id)).thenReturn(Optional.ofNullable(null));
        assertThrows(
                ResourceNotFoundException.class,
                ()->accountService.getAccount(id)
        );
    }

    @Test
    void getAccount_returnAccount_whenFound(){
        Integer id=1;
        Optional<Account> accountOptional= Optional.of(
                Account.builder()
                .id(1)
                .build());
        Mockito.when(accountRepo.findById(id)).thenReturn(accountOptional);
        Account account = accountService.getAccount(id);
        assertEquals(id,account.getId());
    }

    @Test
    void getAccountByCustomerContact_returnAccount_whenFound(){
        Integer id=1;
        Optional<Account> accountOptional= Optional.of(
                Account.builder()
                .id(1)
                .build());
        Mockito.when(accountRepo.findByCustomerId(id)).thenReturn(accountOptional);
        Account account = accountService.getAccountByCustomerId(id);
        assertEquals(1,account.getId());
    }
}