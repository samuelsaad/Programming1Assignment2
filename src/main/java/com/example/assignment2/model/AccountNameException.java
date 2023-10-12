package com.example.assignment2.model;

// When the user chooses to create an account, this exception will be thrown if an invalid name input seems to have occurred
// By this point, it is initially thought that there could be one AccountException that is thrown everytime an account
// related error occurs, but in order to specify the precise error that must be handled in the frontend,
// several account exceptions are created and thrown.
public class AccountNameException extends Exception {
    // the String that is passed into the constructor is simply the message that may be passed into the frontend as e.
    // It may be replaced with errorMessage, but this would be too specific for the use of this variable.
    public AccountNameException(String message) {
        super(message);
    }
}
