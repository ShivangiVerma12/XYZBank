package com.xyz.bank.repository;

import com.xyz.bank.model.Account;

import java.math.BigDecimal;

public interface AccountTransactionRepository {

    boolean initiateTransaction(BigDecimal amount, Account account);
}
