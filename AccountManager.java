
package org.project;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {

    private static final String ACCOUNTS_FILE = "accounts.dat"; // File to store account data
    private Map<String, String[]> accounts; // Map to hold username as key, password and email as values

    public AccountManager() {
        accounts = new HashMap<>();
        loadAccounts();
    }

    // Method to load accounts from the file
    private void loadAccounts() {
        File file = new File(ACCOUNTS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                accounts = (Map<String, String[]>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading accounts: " + e.getMessage());
            }
        }
    }

    // Method to save accounts to the file
    private void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ACCOUNTS_FILE))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }

    // Method to register a new account
    public boolean register(String username, String password, String email) {
        if (accounts.containsKey(username)) {
            return false; // Username already exists
        }
        accounts.put(username, new String[]{password, email});
        saveAccounts();
        return true;
    }

    // Method to verify login credentials
    public boolean verify(String username, String password) {
        if (accounts.containsKey(username)) {
            return accounts.get(username)[0].equals(password); // Check if the password matches
        }
        return false;
    }
}