CREATE TABLE Customer (
    id identity,
    firstName varchar(50),
    lastName varchar(50),
    PRIMARY KEY (id)
);

CREATE TABLE Account (
    accNum BIGINT,
    balance NUMERIC(20,2),
    customerId INTEGER(20) UNIQUE,
    FOREIGN KEY (customerId) REFERENCES Customer(id),
    PRIMARY KEY (accNum)
);

CREATE TABLE Account_Transaction (
    amount NUMERIC(20,2),
    description varchar(25),
    executedDate timestamp,
    AccountNum INTEGER(20),
    FOREIGN KEY (AccountNum) REFERENCES Account(accNum)
);