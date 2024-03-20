package com.ayush.tdd.service;

import com.ayush.tdd.dto.TransactionRequestDto;

public interface TransactionService {
    String executeTransaction(TransactionRequestDto transactionRequestDto);
}
