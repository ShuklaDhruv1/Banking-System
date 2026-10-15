import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Scanner;

public class BankingSystem {

    static ArrayList<BankAccount> accounts =
            BankFileHandler.loadAccounts();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Saved accounts loaded: " + accounts.size());

        while (true) {

            System.out.println("\n================================");
            System.out.println("         BANKING SYSTEM");
            System.out.println("================================");
            System.out.println("1. Create Account");
            System.out.println("2. View All Accounts");
            System.out.println("3. Check Balance");
            System.out.println("4. Deposit Money");
            System.out.println("5. Withdraw Money");
            System.out.println("6. Exit");
            System.out.println("================================");

            System.out.print("Enter your choice: ");
            int choice;

            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
                continue;
            }

            switch (choice) {

                case 1:
                    createAccount();
                    break;

                case 2:
                    viewAccounts();
                    break;

                case 3:
                    checkBalance();
                    break;

                case 4:
                    depositMoney();
                    break;

                case 5:
                    withdrawMoney();
                    break;

                case 6:
                    System.out.println("Thank you for using Banking System!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    public static void createAccount() {

        System.out.print("Enter Account Number: ");
        int number = sc.nextInt();
        sc.nextLine();

        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == number) {
                System.out.println("Account number already exists!");
                return;
            }
        }

        System.out.print("Enter Account Holder Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        double balance = sc.nextDouble();

        if (number <= 0 || name.trim().isEmpty() || balance < 0) {
            System.out.println("Invalid account details!");
            return;
        }

        accounts.add(new BankAccount(number, name, balance));
        BankFileHandler.saveAccounts(accounts);

        System.out.println("Account created successfully!");
    }

    public static void viewAccounts() {

        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }

        for (BankAccount account : accounts) {
            account.displayAccount();
        }
    }

    public static void checkBalance() {

        BankAccount account = findAccount();

        if (account != null) {
            System.out.printf(
                "Available Balance: %.2f%n",
                account.getBalance()
            );
        }
    }

    public static void depositMoney() {

        BankAccount account = findAccount();

        if (account != null) {
            System.out.print("Enter deposit amount: ");
            double amount = sc.nextDouble();

            account.deposit(amount);

            if (amount > 0) {
                BankFileHandler.saveAccounts(accounts);
            }
        }
    }

    public static void withdrawMoney() {

        BankAccount account = findAccount();

        if (account != null) {
            System.out.print("Enter withdrawal amount: ");
            double amount = sc.nextDouble();

            if (amount > 0 && amount <= account.getBalance()) {
                account.withdraw(amount);
                BankFileHandler.saveAccounts(accounts);
            } else {
                account.withdraw(amount);
            }
        }
    }

    public static BankAccount findAccount() {

        System.out.print("Enter Account Number: ");
        int number = sc.nextInt();

        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == number) {
                return account;
            }
        }

        System.out.println("Account not found!");
        return null;
    }
}
