package Only.Server;


import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 8000;
        boolean isLoggedIn = false;
        String currentUsername = "";

        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Welcome to the Registration Screen");

                while (true) {
                    System.out.println("Menu:");
                    if (!isLoggedIn) {
                        System.out.println("1. Register");
                        System.out.println("2. Login");
                    } else {
                        System.out.println("1. View Challenges");
                        System.out.println("2. Attempt Challenge");
                        System.out.println("3. View Applicants");
                        System.out.println("4. Logout");
                    }
                    System.out.println("Type 'exit' to quit.");

                    System.out.print("Enter your choice: ");
                    String choice = scanner.nextLine().toLowerCase();

                    if (choice.equals("exit")) {
                        break;
                    }

                    if (!isLoggedIn) {
                        if (choice.equals("1") || choice.equals("register")) {
                            // Registration process
                            System.out.print("Enter Username password firstName lastName email dateOfBirth schoolRegNumber ");

                            String username = scanner.nextLine();
                            String password = scanner.nextLine();
                            String firstName = scanner.nextLine();
                            String lastName = scanner.nextLine();
                            String email = scanner.nextLine();
                            String dateOfBirth = scanner.nextLine();
                            String schoolRegNumber = scanner.nextLine();



                            // registration data
                            String registrationData = String.format("%s;%s;%s;%s;%s;%s;%s",
                                    username, password, firstName, lastName, email, dateOfBirth, schoolRegNumber);
                            out.println("register:" + registrationData);

                            // Wait for server response
                            String serverResponse = in.readLine();
                            System.out.println(serverResponse);
                        } else if (choice.equals("2") || choice.equals("login")) {
                            // Login process
                            System.out.print("Enter Username: ");
                            String username = scanner.nextLine();
                            System.out.print("Enter Password: ");
                            String password = scanner.nextLine();

                            String loginData = String.format("%s;%s", username, password);
                            out.println("login:" + loginData);

                            String serverResponse = in.readLine();
                            if (serverResponse.equals("Login successful.")) {
                                isLoggedIn = true;
                                currentUsername = username;
                                System.out.println("Login successful.");
                            } else {
                                System.out.println("Login failed. Please try again.");
                            }
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                        }
                    } else {
                        if (choice.equals("1") || choice.equals("view challenges")) {
                            // View challenges
                            out.println("viewchallenge:" + currentUsername);
                            String serverResponse = in.readLine();
                            System.out.println(serverResponse);
                        } else if (choice.equals("2") || choice.equals("attempt challenge")) {
                            // Attempt challenge
                            System.out.print("Enter Challenge ID to attempt: ");
                            String challengeId = scanner.nextLine();
                            out.println("attempt:" + currentUsername + ";" + challengeId);
                            String serverResponse = in.readLine();
                            System.out.println(serverResponse);
                        } else if (choice.equals("3") || choice.equals("view participants")) {
                            // View participants
                            out.println("viewparticipant:" + currentUsername);
                            String serverResponse = in.readLine();
                            System.out.println(serverResponse);
                        } else if (choice.equals("4") || choice.equals("logout")) {
                            // Logout process
                            isLoggedIn = false;
                            currentUsername = "";
                            System.out.println("You have been logged out.");
                        } else {
                            System.out.println("Invalid choice. Please try again.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}