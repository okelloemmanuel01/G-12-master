package Only.Server;


import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final Map<String, User> users = new HashMap<>(); // Store user info
    private static final Map<String, Boolean> loggedInUsers = new HashMap<>(); // Track logged-in users

    public static void main(String[] args) {
        int port = 8000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Handle client request in a separate thread
                new Thread(() -> handleClientRequest(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClientRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String clientRequest;
            while ((clientRequest = in.readLine()) != null) {
                System.out.println("Received from client: " + clientRequest);

                // Process client request (menu options)
                String response = processClientRequest(clientRequest);

                // Send response back to client
                out.println(response);
            }

            System.out.println("Client disconnected: " + clientSocket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processClientRequest(String request) {
        String[] parts = request.split(":", 2);
        String command = parts[0];
        String data = parts.length > 1 ? parts[1] : "";

        switch (command.toLowerCase()) {
            case "register":
                return handleRegister(data);
            case "login":
                return handleLogin(data);
            case "viewchallenge":
                return handleViewChallenge(data);
            case "attempt":
                return handleAttemptChallenge(data);
            case "viewparticipant":
                return handleViewParticipant(data);
            default:
                return "Invalid option. Please try again.";
        }
    }

    private static String handleRegister(String data) {
        String[] fields = data.split(";");
        if (fields.length != 7) { // Expect seven fields
            return "Invalid registration data.";
        }

        String username = fields[0];
        if (users.containsKey(username)) {
            return "Username already exists.";
        }

        User newUser = new User(username, fields[1], fields[2], fields[3], fields[4], fields[5], fields[6]); // Include schoolRegNumber
        users.put(username, newUser);
        System.out.println("Registered new user: " + username);

        return "Registration successful.";
    }

    private static String handleLogin(String data) {
        String[] fields = data.split(";");
        if (fields.length != 2) {
            return "Invalid login data.";
        }

        String username = fields[0];
        String password = fields[1];
        User user = users.get(username);

        if (user != null && user.getPassword().equals(password)) {
            loggedInUsers.put(username, true);
            System.out.println("User logged in: " + username);
            return "Login successful.";
        } else {
            return "Login failed. Please check your username and password.";
        }
    }


    private static String handleViewChallenge(String username) {
        if (isLoggedIn(username)) {
            return "Challenges: Challenge1, Challenge2, Challenge3";
        } else {
            return "Please login to view challenges.";
        }
    }

    private static String handleAttemptChallenge(String data) {
        String[] fields = data.split(";");
        if (fields.length != 2) {
            return "Invalid attempt data.";
        }

        String username = fields[0];
        if (isLoggedIn(username)) {
            String challengeId = fields[1];
            return "Challenge " + challengeId + " attempted successfully.";
        } else {
            return "Please login to attempt challenges.";
        }
    }

    private static String handleViewParticipant(String username) {
        if (isLoggedIn(username)) {
            return "Participants: User1, User2, User3";
        } else {
            return "Please login to view participants.";
        }
    }

    private static boolean isLoggedIn(String username) {
        return loggedInUsers.getOrDefault(username, false);
    }
}

class User {
    private final String username ,password ,firstName, lastName, email , dob, schoolRegNumber;
    /*private final String password;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String dob;
    private final String schoolRegNumber; */// New field

    public User(String username, String password, String firstName, String lastName, String email, String dob, String schoolRegNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.schoolRegNumber = schoolRegNumber; // Initialize new field
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getSchoolRegNumber() {
        return schoolRegNumber;
    }
}