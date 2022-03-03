package com.xyz.bank.controller;

import com.xyz.bank.exception.CustomerNotFoundException;
import com.xyz.bank.model.Customer;
import com.xyz.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private AccountService accountService;

    @GetMapping("{id}")
    public Customer getCustomerDetails(@PathVariable("id") long customerId) throws CustomerNotFoundException {
        return accountService.getCustomer(customerId);
    }
}
