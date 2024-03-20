package com.ayush.tdd.service.impl;

import com.ayush.tdd.dto.TransactionRequestDto;
import com.ayush.tdd.enums.TransactionStatus;
import com.ayush.tdd.exception.InsufficientBalanceException;
import com.ayush.tdd.model.Account;
import com.ayush.tdd.model.Customer;
import com.ayush.tdd.model.Transaction;
import com.ayush.tdd.repo.TransactionRepo;
import com.ayush.tdd.service.AccountService;
import com.ayush.tdd.service.CustomerService;
import com.ayush.tdd.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepo transactionRepo;
    private final CustomerService customerService;
    private final AccountService accountService;

    @Override
    public String executeTransaction(TransactionRequestDto transactionRequestDto) {
        Account senderAccount = accountService.getAccountByCustomerId(transactionRequestDto.getSenderId());
        validateSenderBalance(senderAccount,transactionRequestDto);

        Customer sender = customerService.getCustomer(transactionRequestDto.getSenderId());
        Customer receiver = customerService.getCustomer(transactionRequestDto.getReceiverId());
        Account receiverAccount = accountService.getAccountByCustomerId(transactionRequestDto.getReceiverId());

        BigDecimal receiverBalance=receiverAccount.getBalance().add(transactionRequestDto.getAmount());
        BigDecimal senderBalance=senderAccount.getBalance().subtract(transactionRequestDto.getAmount());
        senderAccount.setBalance(senderBalance);
        receiverAccount.setBalance(receiverBalance);
        Transaction transaction = new Transaction(sender, receiver, transactionRequestDto.getAmount(), TransactionStatus.SUCCESS, UUID.randomUUID().toString());
        Transaction save = transactionRepo.save(transaction);
        return transaction.getTransactionId();
    }

    public void validateSenderBalance(Account senderAccount,TransactionRequestDto transactionRequestDto){
        BigDecimal remainingBalance = senderAccount.getBalance().subtract(transactionRequestDto.getAmount());
        if (remainingBalance.compareTo(BigDecimal.ZERO) == -1){
            throw new InsufficientBalanceException("Insufficient Balance");
        }
    }

}
