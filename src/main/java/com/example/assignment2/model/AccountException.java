package com.example.assignment2.model;
public class AccountException extends Exception {
    // the String that is passed into the constructor is simply the message that may be passed into the frontend as e.
    // It may be replaced with errorMessage, but this would be too specific for the use of this variable.
    public AccountException(String message) {
        super(message);
    }
}
