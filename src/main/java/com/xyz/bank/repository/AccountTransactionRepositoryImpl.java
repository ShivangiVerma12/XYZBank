package com.xyz.bank.repository;

import com.xyz.bank.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class AccountTransactionRepositoryImpl implements AccountTransactionRepository {

    @Autowired
    JdbcTemplate template;

    private static final String INITIAL_DESCRIPTION = "initial credit";

    @Override
    public boolean initiateTransaction(BigDecimal amount, Account account) {
        int i = template.update("INSERT into ACCOUNT_TRANSACTION (amount,description,executedDate,AccountNum ) values (?,?,CURRENT_TIMESTAMP,?);",amount,INITIAL_DESCRIPTION,account.getAccNum());
        return i==1;
    }
}
