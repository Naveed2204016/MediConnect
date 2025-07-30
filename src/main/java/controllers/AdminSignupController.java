package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminSignupController {

    @FXML private TextField nameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button backButton;

    @FXML
    private void handleSignup() {
        String name = nameField.getText().trim();
        String password = passwordField.getText().trim();

        // Temporary: show in console (replace with DB insert later)
        System.out.println("Admin Sign Up:");
        System.out.println("Name: " + name);
        System.out.println("Password: " + password);

        // TODO: validate input and insert into DB (Admins table)
        // Example validations (optional for now):
        // if (name.isEmpty() || password.isEmpty()) { errorLabel.setText("All fields are required."); return; }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
