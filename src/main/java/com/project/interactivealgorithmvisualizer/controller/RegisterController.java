
package com.project.interactivealgorithmvisualizer.controller;

import com.project.interactivealgorithmvisualizer.dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label statusLabel;


    @FXML
    private void handleRegister(ActionEvent event) {

        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();


        // Check empty fields
        if (email.isEmpty() ||
            password.isEmpty() ||
            confirmPassword.isEmpty()) {

            statusLabel.setText("Please fill in all fields.");
            return;
        }


        // Check email format
        if (!email.contains("@") || !email.contains(".")) {

            statusLabel.setText("Please enter a valid email address.");
            return;
        }


        // Check password length
        if (password.length() < 6) {

            statusLabel.setText("Password must be at least 6 characters.");
            return;
        }


        // Check password match
        if (!password.equals(confirmPassword)) {

            statusLabel.setText("Passwords do not match.");
            return;
        }


        // Register user
        UserDAO userDAO = new UserDAO();

        boolean success = userDAO.register(email, password);


        if (success) {

            statusLabel.setText("Account created successfully!");

            System.out.println("New user registered: " + email);

            // Go back to Login
            SceneSwitcher.switchScene(event, "Login.fxml");

        } else {

            statusLabel.setText(
                    "This email is already registered."
            );
        }
    }


    @FXML
    private void handleBackToLogin(ActionEvent event) {

        SceneSwitcher.switchScene(event, "Login.fxml");
    }
}

