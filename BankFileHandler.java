
import java.io.*;
import java.util.ArrayList;

public class BankFileHandler {

    private static final String FILE_NAME = "accounts.txt";

    public static void saveAccounts(ArrayList<BankAccount> accounts) {

        try (BufferedWriter writer =
                new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (BankAccount account : accounts) {

                writer.write(
                    account.getAccountNumber() + "|" +
                    account.getAccountHolder().replace("|", "/") + "|" +
                    account.getBalance()
                );

                writer.newLine();
            }

            System.out.println("Account data saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    public static ArrayList<BankAccount> loadAccounts() {

        ArrayList<BankAccount> accounts = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return accounts;
        }

        try (BufferedReader reader =
                new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|", -1);

                if (data.length == 3) {
                    try {
                        int number = Integer.parseInt(data[0]);
                        String name = data[1];
                        double balance = Double.parseDouble(data[2]);

                        if (balance >= 0) {
                            accounts.add(
                                new BankAccount(number, name, balance)
                            );
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid account record.");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }

        return accounts;
    }
}
