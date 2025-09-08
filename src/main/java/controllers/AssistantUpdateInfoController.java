package controllers;

import db.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    private int assistantId;

    public void setAssistantId(int assistantId) {
        this.assistantId = assistantId;
    }

    @FXML
    private void handleSaveChanges() {
        String fullName = fullNameField.getText();
        String contactNo = contactNoField.getText();
        String email = emailField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!newPassword.isEmpty() || !confirmPassword.isEmpty()) {
            if (!newPassword.equals(confirmPassword)) {
                showAlert("Error", "Passwords do not match.", "Please make sure your new passwords are the same.");
                return;
            }
        }

        try (Connection connection = DBConnection.getConnection()) {
            String sql;
            PreparedStatement stmt;

            if (newPassword.isEmpty()) {
                sql = "UPDATE assistant SET name = ?, contact_number = ?, email = ? WHERE assistant_id = ?";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, fullName);
                stmt.setString(2, contactNo);
                stmt.setString(3, email);
                stmt.setInt(4, assistantId);
            } else {
                sql = "UPDATE assistant SET name = ?, contact_number = ?, email = ?, password = ? WHERE assistant_id = ?";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, fullName);
                stmt.setString(2, contactNo);
                stmt.setString(3, email);
                stmt.setString(4, newPassword);
                stmt.setInt(5, assistantId);
            }

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                showAlert("Success", "Changes Saved", "Your information has been updated successfully.");
            } else {
                showAlert("Error", "Update Failed", "No assistant found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Could not update information", e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
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
