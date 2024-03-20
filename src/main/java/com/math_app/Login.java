package com.math_app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Login {

    private final String url = "jdbc:mysql://aws.connect.psdb.cloud/mathtutor?sslMode=VERIFY_IDENTITY"; // Database URL
    private final String dbUser = "egnemke8t4xjx4ql3cdy"; // Database username
    private final String dbPassword = "pscale_pw_SPykOoPNaFqvmoeKxikgVFL44ekBEiOG3PS4gSEtuto"; // Database password

    public PasswordField password;
    public TextField username;
    public Button grade_two, grade_three, grade_one, grade_k, grade_four, loginButton;
    public ImageView first_avatar, second_avatar;

    private User user;

    @FXML
    public void initialize() {

        AtomicInteger grade = new AtomicInteger();
        AtomicReference<String> imageUrl = new AtomicReference<>("");
        // Assign handlers to grade buttons
        grade_k.setOnAction(event -> grade.set(0));
        grade_one.setOnAction(event -> grade.set(1));
        grade_two.setOnAction(event -> grade.set(2));
        grade_three.setOnAction(event -> grade.set(3));
        grade_four.setOnAction(event -> grade.set(4));

        // Assign handlers to avatar images
        first_avatar.setOnMouseClicked(event -> {
            if (first_avatar.getImage() != null) {
                imageUrl.set(convertToRelativePath(first_avatar.getImage().getUrl()));

            }
        });

        second_avatar.setOnMouseClicked(event -> {
            if (second_avatar.getImage() != null) {
                imageUrl.set(convertToRelativePath(second_avatar.getImage().getUrl()));
            }
        });

        // Assign handler to login/register button
        loginButton.setOnAction(event -> {
            if (password.getText().length() >= 6) {
                if (userExists(username.getText())) {
                    // Attempt to log in
                    if (loginUser(username.getText(), password.getText())) {
                        if (username.getText() != null) {
                            SessionManager.getInstance().setCurrentUsername(username.getText());
                            changeScene("Logged in successfully.");
                        } else {
                            System.out.println("Failed to retrieve user data.");
                        }
                    } else {
                        System.out.println("Incorrect password.");
                    }
                } else {
                    // User does not exist, so create a new user
                    insertData(username.getText().toLowerCase(), hashPassword(password.getText()), grade.get(), imageUrl.get());

                    if (username.getText() != null) {
                        SessionManager.getInstance().setCurrentUsername(username.getText());

                        changeScene("User created and logged in successfully.");
                    } else {
                        System.out.println("Failed to retrieve user data.");
                    }

                }
            } else {
                System.out.println("Password must be at least 6 characters long.");
            }
        });

    }

    private boolean loginUser(String username, String password) {
        // The query now only retrieves the hashed password from the database
        String query = "SELECT password FROM users WHERE username = ?;";

        try (Connection con = DriverManager.getConnection(DatabaseConfig.getUrl(), DatabaseConfig.getUser(), DatabaseConfig.getPassword());
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve the stored hashed password from the database
                    String storedHashedPassword = rs.getString("password");

                    // Use BCrypt to check if the provided password matches the stored hash
                    if (BCrypt.checkpw(password, storedHashedPassword)) {
                        // If the password matches, return true
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // If the user is not found or the password does not match, return false
        return false;
    }


    /**
     * Hash a password using BCrypt.
     *
     * @param plainTextPassword the password to hash
     * @return a hashed password
     */
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    /**
     * Check if a plaintext password matches the hashed version.
     *
     * @param plainTextPassword the plaintext password to check
     * @param hashedPassword the stored hashed password to check against
     * @return true if the passwords match, false otherwise
     */
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }


    private void changeScene(String message) {
        System.out.println(message);

        try {
            // Load the next scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml")); // Update with the correct path
            Parent root = loader.load();

            // Change the scene
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Example conversion method (very basic, for illustration only)
    private String convertToRelativePath(String fullPath) {
        // Logic to convert fullPath to a relative path format you prefer, e.g., "@../../images/"
        // This is an example and needs to be adapted based on your project structure and requirements
        String basePath = "file:/C:/Users/gimir/IdeaProjects/math_project/target/classes"; // Define your base path appropriately
        return fullPath.replace(basePath, "");
    }

    private boolean userExists(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?;";

        try (Connection con = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void insertData(String username, String password, int grade, String avatar) {
        String query = "INSERT INTO users (username, password, grade, avatar) VALUES (?, ?, ?, ?);";

        try (Connection con = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, grade);
            pstmt.setString(4, avatar);


            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) affected");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
