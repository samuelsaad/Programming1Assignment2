package com.example.assignment2.model;

// Here is the AccountPasswordException is thrown when the user has attempted to sign
// in to an account and the password is incorrect, this is crucial due to a simple return of a boolean
// easily getting overlooked if a password seems to have been invalid. Again there could simply be an AccountException
// thrown everytime an exception relating to the account is needed, but this is more specific and precise
public class PasswordException extends Exception {
    // the String that is passed into the constructor is simply the message that may be passed into the frontend as e.
    // It may be replaced with errorMessage, but this would be too specific for the use of this variable.
    public PasswordException(String message) {
        super(message);
    }
}
