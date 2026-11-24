# Bank Management System (PS No. 64)

A console-based **Bank Management System** built in **Java** using core **Data Structures and Algorithms (DSA)** and file handling. It manages bank accounts, processes transactions and pending requests, validates inputs, and saves data between program runs.

## Project Details
- **Project Title:** Bank Management System (PS No. 64)
- **Main Source File:** `BankManagementSystem.java`
- **Course:** Data Structures and Algorithms (DSA)
- **Institution:** Rungta International Skills University, Bhilai
- **Data Storage:** Text file persistence (`bank_accounts.txt`)

## Features
- Create accounts with unique IDs (such as `ACC101`) and search accounts quickly
- Deposit money, withdraw money, check balance, and view recent transactions (latest first)
- Undo the last transaction using stack history
- Queue pending service requests and process them in First-In, First-Out (FIFO) order
- Validate inputs to reject negative amounts, withdrawals exceeding balance, and unknown account IDs
- Load account records on startup and save updated balances on exit

## Data Structures & File Handling
- **Linked List:** Stores all account records dynamically
- **HashMap:** Provides fast account lookup by account number
- **Queue:** Holds pending service requests in FIFO order
- **Stack:** Tracks recent transactions (LIFO) and supports undo
- **File Handling:** Reads and writes account data via `bank_accounts.txt`

## How to Run
1. Open the `BankManagementSystem` project folder in VS Code or a terminal with Java installed.
2. Compile the Java source file:
   ```bash
   javac BankManagementSystem.java

   
