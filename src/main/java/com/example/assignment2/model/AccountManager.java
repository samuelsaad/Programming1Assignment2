package com.example.assignment2.model;

import java.io.*;

public class AccountManager {
    private Account[] accounts;
    private static int arrayCount = 0;

    public AccountManager() {
        this.accounts = new Account[10];
    }

      public Account createDefaultAccount(String username, String password, String firstname, String lastname, int numPost) {
        this.accounts[AccountManager.arrayCount++] = new DefaultAccount(username, password, firstname, lastname, numPost);
        return this.accounts[AccountManager.arrayCount - 1];
    }

    public Account createPremiumAccount(String username, String password, String firstname, String lastname, String securityQuestion) {
        this.accounts[AccountManager.arrayCount++] = new PremiumAccount( username, password, firstname, lastname, securityQuestion);
        return this.accounts[AccountManager.arrayCount - 1];
    }

    public int getArrayCount() {
        return AccountManager.arrayCount;
    }

    public Account[] getAccountArray() {
        return this.accounts;
    }

    public void readAccountsFromFile(String accountInfoFile) throws FileNotFoundException, IOException {
        if (!accountInfoFile.equals("AccountInfo.csv")) {
            throw new FileNotFoundException("Account File Invalid");
        } else {
            BufferedReader accountReader = new BufferedReader(new FileReader(accountInfoFile));
            String accountLine;
            while ((accountLine = accountReader.readLine()) != null) {
                String[] arrayOfLine = accountLine.split(",");
                if (arrayOfLine[0].equals("default")) {
                    createDefaultAccount(arrayOfLine[1], arrayOfLine[2], arrayOfLine[3], arrayOfLine[4], Integer.parseInt(arrayOfLine[5]));
                } else if (arrayOfLine[0].equals("premium")) {
                    createPremiumAccount(arrayOfLine[1], arrayOfLine[2], arrayOfLine[3], arrayOfLine[4], arrayOfLine[5]);
                }

            }
            accountReader.close();
        }

    }

    public String[] getAccountsFormattedInfo() {
        String[] formattedAccounts = new String[AccountManager.arrayCount + 1];
        int i = 0;
        while (i < AccountManager.arrayCount) {
            formattedAccounts[i] = "\n " + (i + 1 + ". ") + this.accounts[i].getDetails() + "\n";
            i++;
        }
        return formattedAccounts;
    }
    
    public void writeAccountsArray(String accountInfoFile) throws FileNotFoundException, IOException {
        String[] accountDetails = new String[10];
        int i = 0;
        while (i < accountDetails.length && this.accounts[i] != null) {
            if (accounts[i] instanceof DefaultAccount) {
                accountDetails[i] = this.accounts[i].getAccountType() + "," + this.accounts[i].getUsername() + "," + this.accounts[i].password + "," + ((DefaultAccount) this.accounts[i]).getNumOrders() + "\n";
            } else if (accounts[i] instanceof PremiumAccount) {
                accountDetails[i] = this.accounts[i].getAccountType() + "," + this.accounts[i].getUsername() + "," +  this.accounts[i].password + "," + ((PremiumAccount) this.accounts[i]).getSecurityQuestion() + "\n";
            }
            i++;
        }
        if (!accountInfoFile.equals("AccountInfo.csv")) {
            throw new FileNotFoundException("File Invalid For Writing To Accounts");
        }
        BufferedWriter accountWriter = new BufferedWriter(new FileWriter(accountInfoFile));
        i = 0;
        while (i < accountDetails.length && accountDetails[i] != null) {
            accountWriter.write(accountDetails[i]);
            i++;
        }
        accountWriter.close();
    }
    public Account login(String username, String password) throws PasswordException {
        // Find the account with the given username
        Account foundAccount = null;
        for (int i = 0; i < AccountManager.arrayCount; i++) {
            if (accounts[i] != null && accounts[i].getUsername().equals(username)) {
                foundAccount = accounts[i];
                break;
            }
        }

        // Check if the username exists
        if (foundAccount == null) {
            throw new PasswordException("Username not found. Please check your credentials.");
        }

        // Check if the password matches the stored password
        if (!foundAccount.getPassword().equals(password)) {
            throw new PasswordException("Incorrect password. Please check your credentials.");
        }

        // Successful login, return the account
        return foundAccount;
    }
    public Account signup(String firstname, String lastname, String username, String password) throws AccountException {
        // Check if the username already exists
        for (int i = 0; i < AccountManager.arrayCount; i++) {
            if (accounts[i] != null && accounts[i].getUsername().equals(username)) {
                throw new AccountException("Username already exists. Please choose a different username.");
            }
        }

        // Ensure that all fields are not empty
        if (firstname.isEmpty() || lastname.isEmpty() || username.isEmpty() || password.isEmpty()) {
            throw new AccountException("All fields (firstname, lastname, username, and password) are required.");
        }

        // Prompt the user to confirm the password
        Console console = System.console();
        if (console == null) {
            throw new AccountException("Cannot read the password securely.");
        }

        char[] passwordChars = console.readPassword("Enter your password: ");
        char[] confirmPasswordChars = console.readPassword("Confirm your password: ");

        String enteredPassword = new String(passwordChars);
        String confirmedPassword = new String(confirmPasswordChars);

        if (!enteredPassword.equals(confirmedPassword)) {
            throw new AccountException("Passwords do not match. Please try again.");
        }

        // Create a new default account
        DefaultAccount newAccount = new DefaultAccount(username, enteredPassword, firstname, lastname, 0); // Initialize with 0 posts
        accounts[AccountManager.arrayCount++] = newAccount;
        return newAccount;
    }



}

