package com.xyz.bank.service;

import com.xyz.bank.exception.AccountAlreadyPresent;
import com.xyz.bank.exception.CustomerNotFoundException;
import com.xyz.bank.model.Account;
import com.xyz.bank.model.Customer;
import com.xyz.bank.repository.AccountRepository;
import com.xyz.bank.repository.AccountTransactionRepository;
import com.xyz.bank.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private AccountTransactionRepository accountTransactionRepository;
    private CustomerRepository customerRepository;

    @Override
    public Customer getCustomer(long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.getCustomer(customerId);
        customer.setAccount(accountRepository.getAccountDetail(customerId));
        return customer;
    }

    @Override
    public Account createCurrentAccount(long customerId, BigDecimal amount) throws CustomerNotFoundException, AccountAlreadyPresent {
        if(customerRepository.checkCustomerExist(customerId)) {
            Account account = accountRepository.createCurrentAccount(customerId, amount);
            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                accountTransactionRepository.initiateTransaction(amount, account);
            }
            account = accountRepository.getAccountDetail(customerId);
            return account;
        }else{
            throw new CustomerNotFoundException(customerId);
        }
    }
}
