package com.xyz.bank.controller;


import com.xyz.bank.exception.AccountAlreadyPresent;
import com.xyz.bank.exception.CustomerNotFoundException;
import com.xyz.bank.model.Account;
import com.xyz.bank.model.OpenAccountReq;
import com.xyz.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

	 @Autowired
	 private AccountService accountService;

	 @PostMapping
	 public Account openAccount(@Valid @RequestBody OpenAccountReq req) throws CustomerNotFoundException, AccountAlreadyPresent {
	        return accountService.createCurrentAccount(req.getCustomerId(), req.getInitialCredit());
	 }

}
