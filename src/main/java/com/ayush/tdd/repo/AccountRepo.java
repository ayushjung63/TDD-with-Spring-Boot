package com.ayush.tdd.repo;

import com.ayush.tdd.model.Account;
import com.ayush.tdd.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account,Integer> {

    @Query(
            nativeQuery=true,
            value="select a.* from account a where  a.customer_id =  ?1"
    )
    Optional<Account> findByCustomerId(Integer customerId);
}
