package com.xyz.bank.service;

import com.xyz.bank.exception.AccountAlreadyPresent;
import com.xyz.bank.exception.CustomerNotFoundException;
import com.xyz.bank.model.Account;
import com.xyz.bank.model.Customer;
import com.xyz.bank.repository.AccountRepository;
import com.xyz.bank.repository.AccountTransactionRepository;
import com.xyz.bank.repository.CustomerRepository;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    AccountServiceImpl service;

    @Mock
    AccountRepository accountRepository;

    @Mock
    CustomerRepository customerRepo;

    @Mock
    AccountTransactionRepository accountTransactionRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getCustomer() throws CustomerNotFoundException {
        long inputValue = 1;
        Account acc = createAccount();
        Customer customer = createCustomer(1L,"Dua","Lipa",acc);
        Mockito.when(accountRepository.getAccountDetail(inputValue)).thenReturn(acc);
        Mockito.when(customerRepo.getCustomer(inputValue)).thenReturn(customer);
        Customer actual=service.getCustomer(inputValue);
        assertEquals(customer,actual);
    }

    @Test
    void getCustomerExceptionCase() throws CustomerNotFoundException {
        long inputValue = 10;
        Mockito.when(customerRepo.getCustomer(inputValue)).thenThrow(new CustomerNotFoundException(inputValue));
        CustomerNotFoundException cfe =Assertions.assertThrows(CustomerNotFoundException.class,() -> service.getCustomer(inputValue));
        assertEquals(CustomerNotFoundException.message+inputValue,cfe.getMessage());
    }

    @Test
    void createCurrentAccount() throws CustomerNotFoundException, AccountAlreadyPresent {
        Long customerId=1L;
        BigDecimal amount = new BigDecimal(1000);
        Account expected = createAccount();
        Mockito.when(customerRepo.checkCustomerExist(customerId)).thenReturn(true);
        Mockito.when(accountRepository.createCurrentAccount(customerId,amount)).thenReturn(expected);
        Mockito.when(accountRepository.getAccountDetail(customerId)).thenReturn(expected);
        Account acc = service.createCurrentAccount(customerId,amount);
        Mockito.verify(accountTransactionRepository).initiateTransaction(ArgumentMatchers.eq(amount),ArgumentMatchers.eq(expected));
        Assertions.assertEquals(expected,acc);
    }

    @Test
    void createCurrentAccountWithAmountZero() throws CustomerNotFoundException, AccountAlreadyPresent {
        Long customerId=1L;
        BigDecimal amount = new BigDecimal(0);
        Account expected = createAccount();
        Mockito.when(customerRepo.checkCustomerExist(customerId)).thenReturn(true);
        Mockito.when(accountRepository.createCurrentAccount(customerId,amount)).thenReturn(expected);
        Mockito.when(accountRepository.getAccountDetail(customerId)).thenReturn(expected);
        Account acc = service.createCurrentAccount(customerId,amount);
        Mockito.verifyNoInteractions(accountTransactionRepository);
        Assertions.assertEquals(expected,acc);
    }

    @Test
    void createCurrentAccountExceptionCase() throws CustomerNotFoundException, AccountAlreadyPresent {
        Long customerId=1L;
        BigDecimal amount = new BigDecimal(1000);
        Account expected = createAccount();
        Mockito.when(customerRepo.checkCustomerExist(customerId)).thenReturn(false);
        CustomerNotFoundException cfe = Assertions.assertThrows(CustomerNotFoundException.class,() -> service.createCurrentAccount(customerId,amount));
        assertEquals(CustomerNotFoundException.message+customerId,cfe.getMessage());
    }

    @Test
    void createCurrentAccountExceptionCase2() throws CustomerNotFoundException, AccountAlreadyPresent {
        Long customerId=1L;
        BigDecimal amount = new BigDecimal(1000);
        Account expected = createAccount();
        Mockito.when(customerRepo.checkCustomerExist(customerId)).thenReturn(true);
        Mockito.when(accountRepository.createCurrentAccount(customerId,amount)).thenThrow(new AccountAlreadyPresent(customerId));
        AccountAlreadyPresent cfe = Assertions.assertThrows(AccountAlreadyPresent.class,() -> service.createCurrentAccount(customerId,amount));
        assertEquals(AccountAlreadyPresent.message+customerId,cfe.getMessage());
    }

    private Customer createCustomer(Long id,String firstName,String lastName){
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        return customer;
    }

    private Customer createCustomer(Long id,String firstName,String lastName, Account acc){
        Customer customer= createCustomer(id,firstName,lastName);
        customer.setAccount(acc);
        return customer;
    }

    private Account createAccount(){
        Account acc = new Account();
        return acc;
    }
}