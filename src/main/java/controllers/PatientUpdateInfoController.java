package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Patient;
import java.time.LocalDate;
import java.sql.*;
import db.DBConnection;

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
            Connection connection = DBConnection.getConnection(); //DriverManager.getConnection(url, username, password);
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
                contactField.getText().isEmpty() || dobPicker.getValue()==null) {
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
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection =DBConnection.getConnection(); // DriverManager.getConnection(url, username, password);
            String query = "UPDATE Patient SET name=?, date_of_birth=?, contact_number=?, email=?, password=? WHERE patient_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, currentPatient.getName());
            preparedStatement.setDate(2, Date.valueOf(currentPatient.getDob()));
            preparedStatement.setString(3, currentPatient.getContactNumber());
            preparedStatement.setString(4, currentPatient.getEmail());
            preparedStatement.setString(5, currentPatient.getPassword());
            preparedStatement.setInt(6, userId);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert("Information updated successfully");
               // statusLabel.setText("Information updated successfully");
            } else {
                showAlert("Update failed");
               // statusLabel.setText("Update failed");
            }
        } catch (SQLException e) {
            statusLabel.setText("Database error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            statusLabel.setText("Driver not found: " + e.getMessage());
        }

    }

    @FXML
    private void handleCancel() {

        nameField.setText(currentPatient.getName());
        dobPicker.setValue(currentPatient.getDob());
        contactField.setText(currentPatient.getContactNumber());
        emailField.setText(currentPatient.getEmail());
        passwordField.clear();
        confirmPasswordField.clear();

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // Use WARNING type for yellow/red icon
        alert.setHeaderText("Update Status");
        alert.setContentText(message);

        // Optional: Style alert text (can also style with CSS file
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-font-size: 14px; -fx-font-family: 'Segoe UI';");

        alert.showAndWait();
    }
}