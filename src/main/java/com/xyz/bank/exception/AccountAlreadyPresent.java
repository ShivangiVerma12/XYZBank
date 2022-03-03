package com.xyz.bank.exception;

public class AccountAlreadyPresent extends Exception{
        private static final long serialVersionUID = 1L;
        public static final String message = "Account Already present for customer id ";

        public AccountAlreadyPresent(long id){
            super(message+id);
        }
}
