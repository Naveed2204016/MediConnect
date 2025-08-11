package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AssistantUpdateInfoController {

    @FXML
    private TextField fullNameField;
    @FXML
    private TextField contactNoField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void handleSaveChanges() {
        String fullName = fullNameField.getText();
        String contactNo = contactNoField.getText();
        String email = emailField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Basic validation (e.g., check if passwords match)
        if (!newPassword.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match.", "Please make sure your new passwords are the same.");
            return;
        }

        // Dummy logic to simulate saving data
        System.out.println("Saving changes:");
        System.out.println("Full Name: " + fullName);
        System.out.println("Contact No: " + contactNo);
        System.out.println("Email: " + email);
        System.out.println("New Password: " + newPassword);

        showAlert("Success", "Changes Saved", "Your information has been updated successfully.");

        // Here you would add your actual logic to save the data to a database or other
        // storage.
        // For example: UserService.updateUser(fullName, contactNo, email, newPassword);
    }

    @FXML
    private void handleCancel() {
        // Clear all fields
        fullNameField.clear();
        contactNoField.clear();
        emailField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();

        showAlert("Cancelled", "Action Cancelled", "The update operation has been cancelled.");
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}