package com.example.assignment2.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.example.assignment2.model.Post;
import com.example.assignment2.model.PostException;
public class PostController {
    private final Map<Integer, Post> posts;
    public PostController() {
        this.posts = new HashMap<>();
    }
    public void addPost(Post post) {
        posts.put(post.getID(), post);
    }
    public void deletePostByID(int postID) throws PostException {
        if (posts.containsKey(postID)) {
            posts.remove(postID);
        } else {
            throw new PostException("Please select a valid post ID number!");
        }
    }

    public List<String> getTopNPostsFormattedDetails(int N) throws PostException {
        int totalPosts = posts.size();

        if (N > totalPosts) {
            throw new PostException("The value of N is greater than the number of posts.");
        }

        List<Post> sortedPosts = posts.values()
                .stream()
                .sorted(Comparator.comparingInt(Post::getLikes).reversed())
                .limit(N)
                .collect(Collectors.toList());

        List<String> formattedDetails = new ArrayList<>();
        for (Post post : sortedPosts) {
            formattedDetails.add(getPostFormattedDetails(post));
        }

        return formattedDetails;
    }

    public String getPostDetails(int postID) throws PostException {
        try {
            Post post = null;
            if (this.posts.containsKey(postID)) {
                post = this.posts.get(postID);
            } else {
                throw new PostException("Please select a valid post ID number!");
            }

            // Check if post is not null before calling the method
            if (post != null) {
                return getPostFormattedDetails(post);
            } else {
                return "Post not found";
            }
        } catch (Exception e) {
            // Handle the exception (e.g., log it or provide a default error message)
            throw new PostException("Error occurred while fetching post details.");
        }
    }
    public List<String> getTopNPostsFormattedDetailsByShares(int N) throws PostException {
        int totalPosts = posts.size();

        if (N > totalPosts) {
            throw new PostException("The value of N is greater than the number of posts.");
        }

        List<Post> sortedPosts = posts.values()
                .stream()
                .sorted(Comparator.comparingInt(Post::getShares).reversed())
                .limit(N)
                .collect(Collectors.toList());

        List<String> formattedDetails = new ArrayList<>();
        for (Post post : sortedPosts) {
            formattedDetails.add(getPostFormattedDetails(post));
        }

        return formattedDetails;
    }

    public Map<Integer, Post> getAllPosts() {
        return posts;
    }
    public String getPostFormattedDetails(Post post) {

        return String.format(
                "Post ID: %d%n" +
                        "Author: %s%n" +
                        "Content: %s%n" +
                        "Number of Likes: %d%n" +
                        "Shares: %d%n" +
                        "Date: %s%n",
                post.getID(),
                post.getAuthor(),
                post.getContent(),
                post.getLikes(),
                post.getShares(),
                post.getDate()
        );
    }


    public void updateCSVFile() {
        try {
            // Open the CSV file for writing
            BufferedWriter writer = new BufferedWriter(new FileWriter("posts.csv"));
            writer.write("ID," + "Content," + "Author," + "NumOfLikes," + "Shares," + "Date\n");
            // Write the updated posts to the file
            for (Post post : this.posts.values()) {
                writer.write(post.getPostCSVFormattedDetails());
            }

            // Close the writer
            writer.close();

            System.out.println("CSV file updated successfully.");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error updating CSV file: " + e.getMessage());
        }
    }
}