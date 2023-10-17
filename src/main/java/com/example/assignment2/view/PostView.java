package com.example.assignment2.view;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.assignment2.controller.PostController;
import com.example.assignment2.model.*;

public class PostView {
    public AccountManager accountManager;
    private final PostController postController;
    public Account account;
    public PostView() {
        this.postController = new PostController();
        accountManager = new AccountManager();
        Account account  = null;

    }

    public void readFileAndCreatePosts() {
        String filePath = "posts.csv";
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int ID = Integer.parseInt(data[0]);
                String content = data[1];
                String author = data[2];
                int numOfLikes = Integer.parseInt(data[3]);
                int shares = Integer.parseInt(data[4]);
                Date date = dateTimeFormat.parse(data[5]);
                String accountUsername = data[6];
                Post post = new Post(ID, content, author, numOfLikes, shares, date, accountUsername);
                postController.addPost(post);
            }
        } catch (IOException | ParseException e) {
            System.out.println("Sorry something on our end went wrong");
        }

    }





    public void getMenuOption() {
        readFileAndCreatePosts();
        try {
            accountManager.readAccountsFromFile("AccountInfo.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String signupOption;

        while (this.account == null){
            signupOption = getValidStringInput("Would you like to signup or login? please write S or L");
            if (signupOption.equalsIgnoreCase("S")){
                String username = getValidStringInput("Please enter a username: ");
                String firstname = getValidStringInput("Please enter a FirstName: ");
                String lastname = getValidStringInput("Please enter a LastName: ");
                String password = null;
                boolean passwordsMatch = false;
                while (!passwordsMatch){
                    password = getValidStringInput("Please enter a password you would like to use: ");
                    String confirmPassword = getValidStringInput("Confirm your password: ");

                if (password.equals(confirmPassword)) {
                    passwordsMatch = true;
                }
                }

                boolean signupSuccess = false;
                while (!signupSuccess){
                    try {
                        this.account = this.accountManager.signup(firstname, lastname, username, password);
                        signupSuccess = true;
                    } catch (AccountException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

        String username = getValidStringInput("Username: ");
        String password = getValidStringInput("Password: ");
        boolean loginSucess = false;
        while(!loginSucess){
            try {
                this.account = accountManager.login(username, password);
                loginSucess = true;
            } catch (PasswordException e) {
                throw new RuntimeException(e);
            }
        }

        }

        String menuChoice = "";
        while (!menuChoice.equals("6")) {
            menuChoice = getValidStringInput("1) Add a social media post\n" +
                    "2) Delete an existing social media post\n" +
                    "3) Retrieve a social media post\n" +
                    "4) Retrieve the top N posts with most likes\n" +
                    "5) Retrieve the top N posts with most shares\n" +
                    "6) Exit");

            if (menuChoice.equals("1")) {
                int ID = getValidIntInput("Please provide the post ID: ");
                String content = getValidStringInput("Please provide the post content: ");
                String author = getValidStringInput("Please provide the post author: ");
                int numOfLikes = getValidIntInput("Please provide the number of likes of the post:");
                int shares = getValidIntInput("Please provide the number of shares of the post: ");
                Date date = getValidDateTimeInput("Please provide the date and time of the post in the format of DD/MM/YYYY HH:MM:");
                Post post = new Post(ID, content, author, numOfLikes, shares, date, this.account.getUsername());
                postController.addPost(post);
                System.out.println("The post was successfully posted!");
                this.postController.updateCSVFile();

            } else if (menuChoice.equals("2")) {
                Integer postID = null;
                while (postID == null) {

                    try {
                        postID = getValidIntInput("Please enter a valid post ID");
                        postController.deletePostByID(postID);
                        this.postController.updateCSVFile();
                    } catch (PostException e) {
                        System.out.println(e.getMessage());
                        postID = null;
                    }
                }


            } else if (menuChoice.equals("3")) {
                Integer postID = null;
                while (postID == null) {

                    try {
                        postID = getValidIntInput("Please enter a valid post ID");
                        System.out.println(this.postController.getPostDetails(postID));
                    } catch (PostException e) {
                        System.out.println(e.getMessage());
                        postID = null;
                    }
                }
                this.postController.updateCSVFile();
            }
            else if (menuChoice.equals("4")){
               Integer numOfPosts = null;
               while(numOfPosts == null){
                   try{
                       numOfPosts = getValidIntInput("Please specify the number of posts to retrieve (N): ");
                       System.out.println(this.postController.getTopNPostsFormattedDetails(numOfPosts));
                   }
                   catch (PostException e){
                       numOfPosts = null;
                       System.out.println(e.getMessage());
                   }

               }
                this.postController.updateCSVFile();


            }
            else if (menuChoice.equals("5")){
                Integer numOfPosts = null;
                while(numOfPosts == null){
                    try{
                        numOfPosts = getValidIntInput("Please specify the number of posts to retrieve (N): ");
                        System.out.println(this.postController.getTopNPostsFormattedDetailsByShares(numOfPosts));
                    }
                    catch (PostException e){
                        numOfPosts = null;
                        System.out.println(e.getMessage());
                    }

                }
                this.postController.updateCSVFile();
            }
        }
    }

    public void printAllPosts() {
        for (Post post : postController.getAllPosts().values()) {
            System.out.println(post.getPostCSVFormattedDetails());
        }
    }

    private int getValidIntInput(String prompt) {
        System.out.println(prompt);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer userInput = null;
        while (userInput == null) {
            try {
                userInput = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                System.out.println("That was an invalid input \n" + prompt);
                userInput = null;
            } catch (IOException e) {
                System.out.println("There is a possibility that something went wrong, please try again later");

            }
        }
        return userInput;
    }

    private String getValidStringInput(String prompt) {
        System.out.println(prompt);
        String userInput = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            userInput = reader.readLine();
            while (userInput == null || userInput.length() == 0) {
                System.out.println("That was an invalid input\n" + prompt);
                userInput = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("There is a possibility that something went wrong, please try again later");
        }
        return userInput;
    }

    private Date getValidDateTimeInput(String prompt) {
        System.out.println(prompt);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Date userInput = null;
        while (userInput == null) {
            try {
                String input = reader.readLine();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                userInput = dateFormat.parse(input);
            } catch (IOException | ParseException e) {
                System.out.println("That was an invalid input \n" + prompt);
                userInput = null;
            }
        }
        return userInput;
    }
}


