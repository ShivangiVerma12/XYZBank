package com.xyz.bank.repository;

import com.xyz.bank.exception.CustomerNotFoundException;
import com.xyz.bank.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired
    JdbcTemplate template;

    @Override
    public boolean checkCustomerExist(long customerId) {
        return (template.queryForObject("select count(1) from customer where ID =?",Integer.class,new Object[]{customerId})).equals(1);
    }

    @Override
    public Customer getCustomer(long customerId) throws CustomerNotFoundException {
        try {
            return template.queryForObject("select * from customer where ID =?", new BeanPropertyRowMapper<>(Customer.class), new Object[]{customerId});
        }catch (IncorrectResultSizeDataAccessException e){
            throw new CustomerNotFoundException(customerId);
        }
    }
}
