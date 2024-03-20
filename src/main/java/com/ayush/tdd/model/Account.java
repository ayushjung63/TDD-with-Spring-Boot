package com.ayush.tdd.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
@Getter
@Setter
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "account_seq_gen")
    @SequenceGenerator(name = "account_seq_gen",initialValue = 1,sequenceName = "account_seq")
    private Integer id;

    @Column(name = "balance",nullable = false)
    private BigDecimal balance;

    @Column(name = "status",columnDefinition = "boolean default true")
    private boolean status;

    @OneToOne
    @JoinColumn(name = "customer_id",nullable = false,foreignKey = @ForeignKey(name = "FK_account_customer"))
    private Customer customer;

    public Account(Integer id, BigDecimal balance, boolean status, Customer customer) {
        this.id = id;
        this.balance = balance;
        this.status = status;
        this.customer = customer;
    }

    public Account() {
    }
}
