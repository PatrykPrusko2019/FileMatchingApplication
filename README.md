# FileMatchingApplication


 The program creates 2 text files:
 - main file
 - transactional file
 he later compares these two files, by customer account number:
 - if the account numbers in both files match, it checks what the transaction was.
 - if the numbers do not match, it either adds the wrong account number to the log
 file or the given record without changes.

 As a result, it shows the results on the console in a new text file and log file

 displays 2 new files: result.txt and log.txt:
 - result.txt -> list of customers with their transactions (shopping or payment);
 if the purchase transaction is a positive transaction amount,
 if the payment transaction is a negative transaction amount

 - log.txt -> list of account numbers that are incorrect (from the transaction file)


 assumptions for transactions -> if they are positive (purchases) we add to the
 customer's balance -> 1000 + 25 = 1025 balance (purchases),
 if they are negative (payments) we also add to the customer's balance -> 1000 + (-25) = 975 balance (payment)
 
 
 
example of program operation:

******************

full file -> filesText, there are text files : main file and transaction file

******************

******************
File Comparison Program
******************
what you want to do, select the appropriate option:
1. Creates new 2 text files by user
2. Creates new 2 text files automatically
3. show current 2 files
4. use the current two files and show the result
5. exit 
> 2
*******************************
WE CREATE 2 NEW FILES AUTOMATICALLY: main file and transactional file
*******************************

created mainFile.txt
created transactionFile.txt
currently created 2 files automatically:
account number in the main file  name and surname     balance
100                              Jan Kowalski           24.98
200                              Anna Nowak           -345.67
800                              Jakub Sroka           224.62
400                              Ola Rudnik            -42.16
300                              Zofia Czekaj            0.00
500                              Jakub Sroka           224.62
700                              Artur Mistrz          800.00
500                              Jakub Sroka            32.00
0                                Jakub Sroka            32.00
-13                              Jakub Sroka            32.00
300                              Zo Fa                   0.00



account number in the transaction file  transaction amount
100                                     27.14
300                                     0.00
900                                     82.17
400                                     66.56
400                                     -6.56
500                                     -30.12
400                                     0.00
200                                     23.56
200                                     -36.56
90                                      -36.56
-900                                    -36.56
700                                     -30.00




deletes the given duplicate account number -> 1 ...


deletes the given duplicate account number -> 300 ...


deletes the given duplicate account number -> 500 ...

displays 2 files after increasing segregation (account number): 
account number in the main file  name and surname     balance
1                                Jakub Sroka             32.0
100                              Jan Kowalski           24.98
200                              Anna Nowak           -345.67
300                              Zofia Czekaj             0.0
400                              Ola Rudnik            -42.16
500                              Jakub Sroka           224.62
700                              Artur Mistrz           800.0
800                              Jakub Sroka           224.62



account number in the transaction file  transaction amount
-900                                    -36.56
90                                      -36.56
100                                     27.14
200                                     23.56
200                                     -36.56
300                                     0.00
400                                     66.56
400                                     -6.56
400                                     0.00
500                                     -30.12
700                                     -30.00
900                                     82.17



displays 2 new files: result.txt and log.txt:
- result.txt -> list of customers with their transactions (shopping or payment)
- log.txt -> list of account numbers that are incorrect (from the transaction file)

*********************************
show file result: 


1. customer of bank
Account{accountNumber=1, firstName='Jakub', lastName='Sroka', balance=32.0}
account balance: 32.00, no transactions
2. customer of bank
Account{accountNumber=100, firstName='Jan', lastName='Kowalski', balance=24.98}
account balance: 52.12 -> shopping transaction
3. customer of bank
Account{accountNumber=200, firstName='Anna', lastName='Nowak', balance=-345.67}
account balance: -322.11 -> shopping transaction
account balance: -358.67 -> payment transaction
4. customer of bank
Account{accountNumber=300, firstName='Zofia', lastName='Czekaj', balance=0.0}
account balance: 0.00 -> zero transaction
5. customer of bank
Account{accountNumber=400, firstName='Ola', lastName='Rudnik', balance=-42.16}
account balance: 24.40 -> shopping transaction
account balance: 17.84 -> payment transaction
account balance: 17.84 -> zero transaction
6. customer of bank
Account{accountNumber=500, firstName='Jakub', lastName='Sroka', balance=224.62}
account balance: 194.50 -> payment transaction
7. customer of bank
Account{accountNumber=700, firstName='Artur', lastName='Mistrz', balance=800.0}
account balance: 770.00 -> payment transaction
8. customer of bank
Account{accountNumber=800, firstName='Jakub', lastName='Sroka', balance=224.62}
account balance: 224.62, no transactions

*********************************


*********************************
show file log: 


1. Invalid account number:
 -900 -36.56
2. Invalid account number:
 90 -36.56
3. Invalid account number:
 900 82.17

*********************************
