package com.ayush.tdd.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@Getter
@Setter
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "customer_seq_gen")
    @SequenceGenerator(name = "customer_seq_gen",initialValue = 1,sequenceName = "customer_seq")
    private Integer id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "contact",nullable = false)
    private String contact;

    @Column(name = "status",columnDefinition = "boolean default true")
    private boolean status;

    public Customer(Integer id, String name) {
        this.id=id;
        this.name=name;
    }

    public Customer() {
    }

    public Customer(Integer id, String name, String email, String contact, boolean status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.status = status;
    }
}
