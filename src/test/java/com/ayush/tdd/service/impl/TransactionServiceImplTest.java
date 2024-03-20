package com.ayush.tdd.service.impl;

import com.ayush.tdd.dto.TransactionRequestDto;
import com.ayush.tdd.enums.TransactionStatus;
import com.ayush.tdd.exception.InsufficientBalanceException;
import com.ayush.tdd.exception.ResourceNotFoundException;
import com.ayush.tdd.model.Account;
import com.ayush.tdd.model.Customer;
import com.ayush.tdd.model.Transaction;
import com.ayush.tdd.repo.TransactionRepo;
import com.ayush.tdd.service.AccountService;
import com.ayush.tdd.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private AccountService accountService;

    @Mock
    private CustomerService customerService;

    @Mock
    private TransactionRepo transactionRepo;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private TransactionRequestDto transactionRequestDto;
    private Customer sender,receiver;
    private Account senderAccount,receiverAccount;
    private Transaction transaction,savedTransaction;

    @BeforeEach
    void setup() {
        transactionRequestDto=TransactionRequestDto.builder()
                .senderId(1)
                .receiverId(2)
                .build();
        sender=new Customer(1,"Shyam Bahadur");
        receiver=new Customer(1,"Ram Bahaudur");
        senderAccount=new Account(1,BigDecimal.valueOf(10000),true,sender);
        receiverAccount=new Account(2,BigDecimal.valueOf(0),true,receiver);
        transaction = new Transaction(sender, receiver, transactionRequestDto.getAmount(), TransactionStatus.SUCCESS, UUID.randomUUID().toString());
        savedTransaction = new Transaction(1,sender, receiver, transactionRequestDto.getAmount(), TransactionStatus.SUCCESS, UUID.randomUUID().toString());
    }

    @Test
    void executeTransaction_throwException_whenSenderBalanceIsLessThanTransactionAmount() {
        transactionRequestDto.setAmount(BigDecimal.valueOf(11000));
        Mockito.when(accountService.getAccountByCustomerId(transactionRequestDto.getSenderId())).thenReturn(senderAccount);
        assertThrows(
                InsufficientBalanceException.class,
                () -> transactionService.executeTransaction(transactionRequestDto)
        );
    }

    @Test
    void executeTransaction_throwException_whenReceiverNotFound() {
        transactionRequestDto.setAmount(BigDecimal.valueOf(9000));
        Mockito.when(accountService.getAccountByCustomerId(transactionRequestDto.getSenderId())).thenReturn(senderAccount);
        Mockito.when(customerService.getCustomer(transactionRequestDto.getSenderId())).thenReturn(sender);
        Mockito.when(customerService.getCustomer(transactionRequestDto.getReceiverId())).thenThrow(ResourceNotFoundException.class);
        assertThrows(
                ResourceNotFoundException.class,
                () -> transactionService.executeTransaction(transactionRequestDto)
        );
    }

    @Test
    void executeTransaction_subtractFromSender_whenSuccessfulTransaction() {
        BigDecimal senderBalance = BigDecimal.valueOf(10000);
        BigDecimal transactionAmount = BigDecimal.valueOf(9000);
        senderAccount.setBalance(senderBalance);
        transactionRequestDto.setAmount(transactionAmount);

        Mockito.when(accountService.getAccountByCustomerId(transactionRequestDto.getSenderId())).thenReturn(senderAccount);
        Mockito.when(customerService.getCustomer(transactionRequestDto.getSenderId())).thenReturn(sender);
        Mockito.when(customerService.getCustomer(transactionRequestDto.getReceiverId())).thenReturn(receiver);
        Mockito.when(accountService.getAccountByCustomerId(transactionRequestDto.getReceiverId())).thenReturn(receiverAccount);
        Mockito.when(transactionRepo.save(Mockito.any(Transaction.class))).thenReturn(savedTransaction);

        transactionService.executeTransaction(transactionRequestDto);
        assertEquals(senderBalance.subtract(transactionAmount),senderAccount.getBalance());
    }

    @Test
    void executeTransaction_addToReceiver_whenSuccessfulTransaction() {
        BigDecimal senderBalance = BigDecimal.valueOf(10000);
        BigDecimal receiverBalance = BigDecimal.valueOf(20000);
        BigDecimal transactionAmount = BigDecimal.valueOf(9000);
        senderAccount.setBalance(senderBalance);
        receiverAccount.setBalance(receiverBalance);
        transactionRequestDto.setAmount(transactionAmount);

        Mockito.when(accountService.getAccountByCustomerId(transactionRequestDto.getSenderId())).thenReturn(senderAccount);
        Mockito.when(customerService.getCustomer(transactionRequestDto.getSenderId())).thenReturn(sender);
        Mockito.when(customerService.getCustomer(transactionRequestDto.getReceiverId())).thenReturn(receiver);
        Mockito.when(accountService.getAccountByCustomerId(transactionRequestDto.getReceiverId())).thenReturn(receiverAccount);
        Mockito.when(transactionRepo.save(Mockito.any(Transaction.class))).thenReturn(savedTransaction);
        transactionService.executeTransaction(transactionRequestDto);
        assertEquals(receiverBalance.add(transactionAmount),receiverAccount.getBalance());
    }
}