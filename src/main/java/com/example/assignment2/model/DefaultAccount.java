package com.example.assignment2.model;

public class DefaultAccount extends Account implements DetailPrintable {
    private int numPosts;

    DefaultAccount(String username, String password, int numPosts) {
        super(username, password);
        this.numPosts = numPosts;
    }

    @Override
    public String getDetails() {
        // Here the super method is being called and the neccessary details are being printed according to the class.
        return super.getDetails() + "\n\tNumber of Posts : " + this.numPosts;
    }

    @Override
    String getAccountType() {
        return "default";
    }

    int getNumOrders() {
        return this.numPosts;
    }

    public void addPost() {
        this.numPosts++;
    }

}