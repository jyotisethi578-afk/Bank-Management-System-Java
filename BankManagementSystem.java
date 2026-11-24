import java.io.*;
import java.util.*;

class Account {
    String accountId;
    String name;
    double balance;

    Account(String accountId, String name, double balance) {
        this.accountId = accountId;
        this.name = name;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return accountId + "," + name + "," + balance;
    }
}

public class BankManagementSystem {
    // 1. Linked List me saare accounts
    static LinkedList<Account> accountList = new LinkedList<>();
    // 2. Hashing se fast search
    static HashMap<String, Account> accountMap = new HashMap<>();
    // 3. Queue - Pending service requests
    static Queue<String> serviceQueue = new LinkedList<>();
    // 4. Stack - Recent Transactions
    static Stack<String> transactionStack = new Stack<>();

    static final String FILE_NAME = "bank_accounts.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadFromFile();

        int choice;
        do {
            System.out.println("\n===== BANK MANAGEMENT SYSTEM =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Search Account");
            System.out.println("6. View Recent Transactions");
            System.out.println("7. Add Service Request");
            System.out.println("8. Process Service Request");
            System.out.println("9. Exit & Save");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // buffer clear

            switch (choice) {
                case 1: createAccount(); break;
                case 2: deposit(); break;
                case 3: withdraw(); break;
                case 4: checkBalance(); break;
                case 5: searchAccount(); break;
                case 6: viewTransactions(); break;
                case 7: addServiceRequest(); break;
                case 8: processServiceRequest(); break;
                case 9: saveToFile(); System.out.println("Data saved. Thank You!"); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 9);
    }

    // --- CREATE ACCOUNT ---
    static void createAccount() {
        System.out.print("Enter Account ID (e.g. ACC101): ");
        String id = sc.nextLine();
        if (accountMap.containsKey(id)) {
            System.out.println("Account already exists!");
            return;
        }
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Opening Balance: ");
        double bal = sc.nextDouble();
        sc.nextLine();

        if (bal < 0) {
            System.out.println("Balance cannot be negative!");
            return;
        }
        Account acc = new Account(id, name, bal);
        accountList.add(acc);
        accountMap.put(id, acc);
        System.out.println("Account created: " + id + " | " + name + " | Balance " + (int)bal);
    }

    // --- DEPOSIT ---
    static void deposit() {
        System.out.print("Enter Account ID: ");
        String id = sc.nextLine();
        Account acc = accountMap.get(id);
        if (acc == null) {
            System.out.println("Account " + id + " not found.");
            return;
        }
        System.out.print("Enter Amount to Deposit: ");
        double amt = sc.nextDouble();
        sc.nextLine();
        if (amt < 0) {
            System.out.println("Amount cannot be negative!");
            return;
        }
        acc.balance += amt;
        transactionStack.push("Deposit " + (int)amt + " to " + id);
        System.out.println("Deposited " + (int)amt + ". New Balance: " + (int)acc.balance);
    }

    // --- WITHDRAW ---
    static void withdraw() {
        System.out.print("Enter Account ID: ");
        String id = sc.nextLine();
        Account acc = accountMap.get(id);
        if (acc == null) {
            System.out.println("Account " + id + " not found.");
            return;
        }
        System.out.print("Enter Amount to Withdraw: ");
        double amt = sc.nextDouble();
        sc.nextLine();
        if (amt < 0) {
            System.out.println("Amount cannot be negative!");
            return;
        }
        if (amt > acc.balance) {
            System.out.println("Insufficient Balance! Available: " + (int)acc.balance);
            return;
        }
        acc.balance -= amt;
        transactionStack.push("Withdraw " + (int)amt + " from " + id);
        System.out.println("Withdrawn " + (int)amt + ". New Balance: " + (int)acc.balance);
    }

    // --- CHECK BALANCE ---
    static void checkBalance() {
        System.out.print("Enter Account ID: ");
        String id = sc.nextLine();
        Account acc = accountMap.get(id);
        if (acc == null) {
            System.out.println("Account " + id + " not found.");
            return;
        }
        System.out.println("Balance: " + (int)acc.balance);
    }

    // --- SEARCH ---
    static void searchAccount() {
        System.out.print("Enter Account ID to Search: ");
        String id = sc.nextLine();
        Account acc = accountMap.get(id);
        if (acc == null) {
            System.out.println("Account " + id + " not found.");
        } else {
            System.out.println("Found: " + acc.accountId + " | " + acc.name + " | Balance " + (int)acc.balance);
        }
    }

    // --- TRANSACTIONS (STACK) ---
    static void viewTransactions() {
        if (transactionStack.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        System.out.print("Recent transactions: ");
        // Stack ko top se print karna hai - sabse recent pehle
        for (int i = transactionStack.size() - 1; i >= 0; i--) {
            String t = transactionStack.get(i);
            // Sirf "Withdraw 200" jaisa dikhana hai
            System.out.print(t.split(" to ")[0].split(" from ")[0]);
            if (i != 0) System.out.print(", ");
        }
        System.out.println();
    }

    // --- QUEUE ---
    static void addServiceRequest() {
        System.out.print("Enter Service Request (e.g. Update Address): ");
        String req = sc.nextLine();
        serviceQueue.add(req);
        System.out.println("Request added to queue.");
    }

    static void processServiceRequest() {
        if (serviceQueue.isEmpty()) {
            System.out.println("No pending requests.");
        } else {
            System.out.println("Processing: " + serviceQueue.poll());
        }
    }

    // --- FILE HANDLING ---
    static void loadFromFile() {
        try {
            File f = new File(FILE_NAME);
            if (!f.exists()) return;
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Account acc = new Account(parts[0], parts[1], Double.parseDouble(parts[2]));
                    accountList.add(acc);
                    accountMap.put(parts[0], acc);
                }
            }
            br.close();
            System.out.println("Previous data loaded from " + FILE_NAME);
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    static void saveToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));
            for (Account acc : accountList) {
                bw.write(acc.toString());
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}