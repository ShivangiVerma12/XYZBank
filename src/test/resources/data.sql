INSERT INTO Customer ( firstName, lastName)
VALUES ( 'Dua', 'Lipa');

INSERT INTO Customer (firstName, lastName)
VALUES ( 'Taylor', 'Swift');

INSERT INTO Customer ( firstName, lastName)
VALUES ( 'Shawn', 'Mendes');

INSERT INTO ACCOUNT(ACCNUM, BALANCE,CUSTOMERID)
values (100000001,1000,1);

INSERT into Account_Transaction(amount,description,executedDate,AccountNum )
values (1000,'initial credit',CURRENT_TIMESTAMP,100000001);