package com.xyz.bank.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Account {

    private Long accNum;
    private Long customerId;
    private BigDecimal balance;
    private List<AccountTransaction> transactions;

}
