package com.xyz.bank.repository;

import com.xyz.bank.exception.AccountAlreadyPresent;
import com.xyz.bank.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xyz.bank.model.Customer;

import java.math.BigDecimal;

public interface AccountRepository {

    Account getAccountDetail(long customerId);
    Account createCurrentAccount(long customerId, BigDecimal amount) throws AccountAlreadyPresent;
}
