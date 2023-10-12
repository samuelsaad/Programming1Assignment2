package com.example.assignment2.model;

// Here is the PremiumAccount subclass, it may be renamed to Premium but this would not be specific enough as to what it does.

// Justification Of Datatypes:
// The variables that are set to the type String are due to them simply being a literal
// and may be replaced with a char array. This would not be as simple and would cause confusion and further complication.
public class PremiumAccount extends Account implements DetailPrintable {

    // this is the passcode that the user will use in order to keep secure and sign in to their account
    // this may have been replaced with a pin or "passcode"
    private String securityQuestion;

    // Here is the key that will be used to encrypt the data of the user,
    // all the passcodes in the premium account will be encrypted before entering the files and will only be unencrypted when
    // the user is asked to sign in and the program must match the codes. The key is an object member variable due to it
    // being assigned by the user as the most frequent character or String in their passcode. It may be replaced with
    // replaceChar or replaceString but key is simply more precise.
    private String key;

    // Here is the String that will be used to encrypt all the passcodes of the object
    // it may be replaced with "replacement", however this would be too generic as to what it does.
    private static final String passwordReplacement = "asx101";

    PremiumAccount(String username, String password, String securityQuestion) {
        super(username, password);
        this.securityQuestion = securityQuestion;
    }

    public void encryptPassword() {
        super.password = super.password.replaceAll(this.key, PremiumAccount.passwordReplacement);
    }

    @Override
    String getAccountType() {
        return "premium";
    }

    String getKey() {
        return this.key;
    }

    @Override
    public String getDetails() {
        return super.getDetails();
    }


    public String getSecurityQuestion() {
        return this.securityQuestion;
    }

    public String getUnencryptedPassword() {
        return this.password.replaceAll(PremiumAccount.passwordReplacement, this.key);
    }

}
