package com.xyz.bank.repository;

import com.xyz.bank.exception.CustomerNotFoundException;
import com.xyz.bank.model.Customer;

public interface CustomerRepository {

    boolean checkCustomerExist(long customerId);
    Customer getCustomer(long customerId) throws CustomerNotFoundException;
}
