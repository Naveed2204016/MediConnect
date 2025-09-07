package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import db.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssistantSignupController {

    @FXML private TextField nameField;
    @FXML private TextField contactField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField doctorIdField;
    @FXML private Label errorLabel;
    @FXML private Button backButton;

    @FXML
    private void handleSignup() {
        String name = nameField.getText().trim();
        String contact = contactField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String doctorIdStr = doctorIdField.getText().trim();

        if(name.isEmpty() || contact.isEmpty() || email.isEmpty() || password.isEmpty() || doctorIdStr.isEmpty()) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        try {
            int doctorId = Integer.parseInt(doctorIdStr);
            Connection conn = DBConnection.getConnection();

            // Check if doctor exists
            String checkDoctorQuery = "SELECT * FROM doctor WHERE doctor_id = ?";
            PreparedStatement psCheck = conn.prepareStatement(checkDoctorQuery);
            psCheck.setInt(1, doctorId);
            ResultSet rs = psCheck.executeQuery();

            if(!rs.next()) {
                errorLabel.setText("Doctor ID does not exist.");
                return;
            }

            // Insert assistant
            String insertQuery = "INSERT INTO assistant (d_id, name, contact_number, email, password) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(insertQuery);
            psInsert.setInt(1, doctorId);
            psInsert.setString(2, name);
            psInsert.setString(3, contact);
            psInsert.setString(4, email);
            psInsert.setString(5, password);

            int result = psInsert.executeUpdate();
            if(result > 0) {
                errorLabel.setText("Assistant registered successfully!");
                System.out.println("Assistant registered successfully!");
            } else {
                errorLabel.setText("Sign up failed. Try again.");
            }

        } catch (NumberFormatException e) {
            errorLabel.setText("Doctor ID must be a number.");
        } catch (SQLException e) {
            e.printStackTrace();
            errorLabel.setText("Database error: " + e.getMessage());
        }
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
