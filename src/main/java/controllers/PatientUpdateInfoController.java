package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Patient;
import java.time.LocalDate;

public class PatientUpdateInfoController {

    @FXML private TextField nameField;
    @FXML private DatePicker dobPicker;
    @FXML private TextField contactField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label statusLabel;

    private Patient currentPatient;

    @FXML
    public void initialize() {
        // In a real app, you would load this from database
        currentPatient = new Patient();
        currentPatient.setName("John Doe");
        currentPatient.setDob(LocalDate.of(1990, 5, 15));
        currentPatient.setContactNumber("1234567890");
        currentPatient.setEmail("john@example.com");


        nameField.setText(currentPatient.getName());
        dobPicker.setValue(currentPatient.getDob());
        contactField.setText(currentPatient.getContactNumber());
        emailField.setText(currentPatient.getEmail());
    }

    @FXML
    private void handleSave() {
        // Simple validation
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

        // Update patient object
        currentPatient.setName(nameField.getText());
        currentPatient.setDob(dobPicker.getValue());
        currentPatient.setContactNumber(contactField.getText());
        currentPatient.setEmail(emailField.getText());

        if (!passwordField.getText().isEmpty()) {
            currentPatient.setPassword(passwordField.getText());
        }

        statusLabel.setText("Information updated successfully!");
        // In real app: Save to database here
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