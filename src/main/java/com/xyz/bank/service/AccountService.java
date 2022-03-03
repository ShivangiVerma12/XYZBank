package com.xyz.bank.service;

import com.xyz.bank.exception.AccountAlreadyPresent;
import com.xyz.bank.exception.CustomerNotFoundException;
import com.xyz.bank.model.Account;
import com.xyz.bank.model.Customer;

import java.math.BigDecimal;

public interface AccountService {

    Customer getCustomer(long customerId) throws CustomerNotFoundException;
    Account createCurrentAccount(long customerId, BigDecimal amount) throws CustomerNotFoundException, AccountAlreadyPresent;
}
