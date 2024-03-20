package com.ayush.tdd.service;

import com.ayush.tdd.model.Account;
import com.ayush.tdd.model.Customer;

public interface AccountService {
    Account getAccount(Integer id);
    Account getAccountByCustomerId(Integer customerId);
}
