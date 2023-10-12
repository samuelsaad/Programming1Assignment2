package com.example.assignment2.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Post implements DetailPrintable{
    private final int ID;
    private final String content;
    private final String author;
    private final int numOfLikes;
    private final int shares;
    private final Date date;
    private final String accountUsername;
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public Post(int ID, String content, String author, int numOfLikes, int shares, Date date, String accountUsername) {
        this.ID = ID;
        this.content = content;
        this.author = author;
        this.numOfLikes = numOfLikes;
        this.shares = shares;
        this.date = date;
        this.accountUsername = accountUsername;
    }

    public int getID() {
        return ID;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public int getLikes() {
        return numOfLikes;
    }

    public int getShares() {
        return shares;
    }

    public Date getDate() {
        return date;
    }

    public String getPostCSVFormattedDetails() {
        String formattedDateTime = DATE_FORMATTER.format(date);

        return ID + "," + content + "," + author + "," + numOfLikes + "," + shares + "," + formattedDateTime + "\n";
    }


    @Override
    public String getDetails() {
        return String.format(
                "Post ID: %d%n" +
                        "Author: %s%n" +
                        "Content: %s%n" +
                        "Number of Likes: %d%n" +
                        "Shares: %d%n" +
                        "Date: %s%n",
                this.ID,
                this.author,
                this.content,
                this.numOfLikes,
                this.shares,
                this.date
        );
    }
}
