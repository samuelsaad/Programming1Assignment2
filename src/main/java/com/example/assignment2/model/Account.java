package com.example.assignment2.model;


public abstract class Account {
    private String firstname;
    private String username;
    public String password;
    private String lastname;



    // Here is the constructor, the variables being passed here have the same names declared as the object member variables
    // it is no modifier due to it only being used in the account structure which is in the same package.
    Account(String username, String password, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getPassword() {
        return this.password;
    }
    public String getUsername() {
        return this.username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    // Here is the mandatory no modifier method that will return the account type. This will be used in order to write the account type to the file
    // in order for the object reconstruction methods to know what instance this object is. This method may be renamed to getType,
    // but this would possibly be confused with others. There may be an interface that makes this
    // method compulsory but this would not be necessary as the account type will be used only in the subclasses of this class.
    // Here the return type may be of type String Array or Char Array but this would not be necessary as accessing and specifying
    // different indexes is not needed for the use of this method.
    abstract String getAccountType();


    // Here is the method that will be used by all sub-classes alike, it will simply return a string with all the general details that the
    // subclasses may choose to override and add respectively. It has been named getDetails as it simply gets all the details of the object,
    // it may be renamed to getAccountDetails but this would be redundant and too specific.
    // Again Here the return type may be of type String Array or Char Array but this would not be necessary as accessing and specifying
    // different indexes is not needed for the use of this method.
    String getDetails() {
        return  "First Name : " + this.firstname + "\n\t Last Name : " + this.lastname + "\n\t Type of Account : " + this.getAccountType() + "\n\t Username : " + this.username;
    }

}