package com.xyz.bank.repository;

import com.xyz.bank.exception.AccountAlreadyPresent;
import com.xyz.bank.model.Account;
import com.xyz.bank.model.AccountTransaction;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collections;

@Repository
@Slf4j
@AllArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    JdbcTemplate template;
    AccountTransactionRepository accountTransactionRepository;

    @Override
    public Account getAccountDetail(long customerId){
        if(accountExist(customerId)) {
            return template.queryForObject("select * from account a left join account_transaction at on a.accNum=at.AccountNum where CUSTOMERID =?", accountMapper, new Object[]{customerId});
        }
        return null;
    }

    public boolean accountExist(long customerId){
        return template.queryForObject("select count(1) from account where customerid = ?",Integer.class,new Object[]{customerId}) > 0;
    }

    public Account createCurrentAccount(long customerId, BigDecimal amount) throws AccountAlreadyPresent {
        try{
            Account account = new Account();
            account.setAccNum(convertToBigInteger());
            template.update("INSERT INTO ACCOUNT(ACCNUM, BALANCE,CUSTOMERID) " +
                    "VALUES (?,?,?);",account.getAccNum(), amount,customerId);
            return account;
        }catch (DuplicateKeyException e){
            log.error("Account already exist",e);
            throw new AccountAlreadyPresent(customerId);
        }


    }

    private final RowMapper<Account> accountMapper = (rs, rowNum) -> {
        Account a =  (new BeanPropertyRowMapper<>(Account.class)).mapRow(rs,rowNum);
        AccountTransaction at =  (new BeanPropertyRowMapper<>(AccountTransaction.class)).mapRow(rs,rowNum);
        a.setTransactions(Collections.singletonList(at));
        return a;
    };

    private static Long accCount = 1L;
    public long convertToBigInteger()
    {
        long l = 1000000000L+accCount;
        log.info("Generated acc number {}",l);
        accCount++;
        return l;
    }
}
