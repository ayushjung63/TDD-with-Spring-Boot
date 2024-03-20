package com.ayush.tdd.model;

import com.ayush.tdd.enums.TransactionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "transaction_seq_gen")
    @SequenceGenerator(name = "transaction_seq_gen",initialValue = 1,sequenceName = "transaction_seq")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "sender_id",nullable = false,foreignKey = @ForeignKey(name = "FK_account_sender"))
    private Customer sender;

    @OneToOne
    @JoinColumn(name = "receiver_id",nullable = false,foreignKey = @ForeignKey(name = "FK_account_receiver"))
    private Customer receiver;

    @Column(name = "amount",nullable = false)
    private BigDecimal amount;

    @Column(name = "amount",nullable = false)
    private TransactionStatus transactionStatus;

    @Column(name = "transaction_id",nullable = false)
    private String transactionId;

    public Transaction(Customer sender, Customer receiver, BigDecimal amount, TransactionStatus transactionStatus, String transactionId) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.transactionStatus = transactionStatus;
        this.transactionId = transactionId;
    }

    public Transaction(Integer id, Customer sender, Customer receiver, BigDecimal amount, TransactionStatus transactionStatus, String transactionId) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.transactionStatus = transactionStatus;
        this.transactionId = transactionId;
    }
}
