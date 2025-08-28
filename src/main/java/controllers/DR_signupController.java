package controllers;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DR_signupController {
    public TextField nameField;
    public TextField hospitalField;
    public TextField cityField;
    public TextField startTimeField;
    public TextField endTimeField;
    public TextField specializationField;
    public TextField qualificationField;
    public TextField feesField;
    public TextField contactField;
    public TextField emailField;
    public TextField capacityField;
    public TextField emergencySlotsField;
    public PasswordField passwordField;
    public Button backButton;
    public Label errorLabel;

    // Handle "Back" button click
    public void handleBack2(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Failed to load Welcome page.");
        }
    }

    // Handle "Signup" button click
    public void handleSignup2(ActionEvent actionEvent) {
        String name1 = nameField.getText().trim();
        String hospital = hospitalField.getText().trim();
        String city = cityField.getText().trim();
        String startTime = startTimeField.getText().trim();
        String endTime = endTimeField.getText().trim();
        String specialization = specializationField.getText().trim();
        String qualification = qualificationField.getText().trim();
        String fees = feesField.getText().trim();
        String contact1 = contactField.getText().trim();
        String email1 = emailField.getText().trim();
        String capacity = capacityField.getText().trim();
        String emergencySlots = emergencySlotsField.getText().trim();
        String password2 = passwordField.getText().trim();

        if(name1.isEmpty() || email1.isEmpty() || password2.isEmpty()) {
            errorLabel.setText("Name, Email, and Password cannot be empty!");
            return;
        }

        try {
            Connection connection = DBConnection.getConnection();

            // 1️⃣ Insert Doctor
            String doctorQuery = "INSERT INTO doctor(name,specialization,qualification,fees,contact_number,email,capacity_per_day,emergency_slots_per_day,password) " +
                    "VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement psDoctor = connection.prepareStatement(doctorQuery, Statement.RETURN_GENERATED_KEYS);
            psDoctor.setString(1, name1);
            psDoctor.setString(2, specialization);
            psDoctor.setString(3, qualification);
            psDoctor.setString(4, fees);
            psDoctor.setString(5, contact1);
            psDoctor.setString(6, email1);
            psDoctor.setString(7, capacity);
            psDoctor.setString(8, emergencySlots);
            psDoctor.setString(9, password2);

            int cnt = psDoctor.executeUpdate();
            if (cnt == 0) {
                errorLabel.setText("Error: Could not insert doctor!");
                return;
            }

            // 2️⃣ Get last inserted doctor_id
            int doctorId = 0;
            ResultSet rs = psDoctor.getGeneratedKeys();
            if (rs.next()) {
                doctorId = rs.getInt(1);
            }

            // 3️⃣ Insert Location
            String locationQuery = "INSERT INTO location(hospital,city,start_time,End_time,d_id) VALUES(?,?,?,?,?)";
            PreparedStatement psLocation = connection.prepareStatement(locationQuery);
            psLocation.setString(1, hospital);
            psLocation.setString(2, city);
            psLocation.setString(3, startTime);
            psLocation.setString(4, endTime);
            psLocation.setInt(5, doctorId);
            psLocation.executeUpdate();

            // 4️⃣ Load Doctor Dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Doctors_dashboard_dashboard.fxml"));
            Scene scene = new Scene(loader.load());
            Doctors_Dashboard_controller dashboardController = loader.getController();
            dashboardController.setUserID(doctorId);

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);

        } catch (SQLException e) {
            e.printStackTrace();
            errorLabel.setText("Database Error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("UI Error: " + e.getMessage());
        }
    }
}
