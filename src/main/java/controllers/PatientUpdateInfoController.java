package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Patient;
import java.time.LocalDate;
import java.sql.*;

public class PatientUpdateInfoController {

    @FXML private TextField nameField;
    @FXML private DatePicker dobPicker;
    @FXML private TextField contactField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label statusLabel;
    private int userId;
    private Patient currentPatient;
    private static final String url="jdbc:mysql://127.0.0.1:3306/mediconnect";
    private static final String username="root";
    private static final String password="backend#8";


    public void setUserId(int userId) {
        this.userId = userId;
        loaddata();
    }
    @FXML
    public void loaddata() {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try
        {
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM Patient WHERE patient_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,userId);
            System.out.println("Fetching patient info for user ID: " + userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                currentPatient = new Patient();
                currentPatient.setName(resultSet.getString("name"));
                currentPatient.setDob(resultSet.getDate("date_of_birth").toLocalDate());
                currentPatient.setContactNumber(resultSet.getString("contact_number"));
                currentPatient.setEmail(resultSet.getString("email"));
                currentPatient.setPassword(resultSet.getString("password"));
            } else {
                statusLabel.setText("User not found");
                return;
            }
        } catch (SQLException e) {
            statusLabel.setText("Database error: " + e.getMessage());
            return;
        }
        nameField.setText(currentPatient.getName());
        dobPicker.setValue(currentPatient.getDob());
        contactField.setText(currentPatient.getContactNumber());
        emailField.setText(currentPatient.getEmail());
    }


    @FXML
    private void handleSave() {
        if (nameField.getText().isEmpty() ||
                emailField.getText().isEmpty() ||
                contactField.getText().isEmpty()) {
            statusLabel.setText("Please fill all required fields");
            return;
        }
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            statusLabel.setText("Passwords don't match");
            return;
        }

        currentPatient.setName(nameField.getText());
        currentPatient.setDob(dobPicker.getValue());
        currentPatient.setContactNumber(contactField.getText());
        currentPatient.setEmail(emailField.getText());

        if (!passwordField.getText().isEmpty()) {
            currentPatient.setPassword(passwordField.getText());
        }

        statusLabel.setText("Information updated successfully!");

    }

    @FXML
    private void handleCancel() {
        // Reset form to original values
        nameField.setText(currentPatient.getName());
        dobPicker.setValue(currentPatient.getDob());
        contactField.setText(currentPatient.getContactNumber());
        emailField.setText(currentPatient.getEmail());
        passwordField.clear();
        confirmPasswordField.clear();
        statusLabel.setText("");
    }
}