package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML private TextField userIdField;
    @FXML private PasswordField passwordField;

    private String role;

    public void setRole(String role) {
        this.role = role;
        System.out.println("Role set to: " + role);
    }

    @FXML
    private void handleLogin() {
        String userId = userIdField.getText().trim();
        String password = passwordField.getText().trim();

        // Placeholder logic for now
        System.out.println("Attempted login for role: " + role);
        System.out.println("User ID: " + userId);
        System.out.println("Password: " + password);

        // Later you'll query the database based on the role
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) userIdField.getScene().getWindow();  // userIdField is any field from the login scene
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
