
package com.project.interactivealgorithmvisualizer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.project.interactivealgorithmvisualizer.SessionManager;
import com.project.interactivealgorithmvisualizer.dao.UserDAO;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    @FXML
    private void handleLogin(ActionEvent event) {

        String email = usernameField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter email and password.");
            return;
        }

        UserDAO userDAO = new UserDAO();

        if (userDAO.login(email, password)) {

            int userId = userDAO.getUserId(email, password);

            // Save logged-in user information
            SessionManager.setCurrentUser(userId, email);

            System.out.println("Login successful!");
            System.out.println("User ID: " + userId);
            System.out.println("Email: " + email);

            SceneSwitcher.switchScene(event, "Dashboard.fxml");

        } else {

            statusLabel.setText("Invalid email or password.");
        }
    }

    @FXML
    private void handleGuestLogin(ActionEvent event) {

        // Guest has no user ID
        SessionManager.logout();

        SceneSwitcher.switchScene(event, "Dashboard.fxml");
    }

    @FXML
    private void handleRegister(ActionEvent event) {

        SceneSwitcher.switchScene(event, "Register.fxml");
    }
}


