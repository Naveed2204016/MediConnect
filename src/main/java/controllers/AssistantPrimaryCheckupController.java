package controllers;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssistantPrimaryCheckupController {

    @FXML private TextField patientIdField;
    @FXML private TextField patientNameField;
    @FXML private TextField contactNoField;
    @FXML private TextField weightField;
    @FXML private TextField ageField;
    @FXML private TextField bloodPressureField;

    private int assistantId;

    public void setAssistantId(int assistantId) {
        this.assistantId = assistantId;
        System.out.println("Assistant ID set to: " + assistantId);
    }

    @FXML
    private void handleAddPatient(ActionEvent event) {
        String patientIdText = patientIdField.getText();
        String weight = weightField.getText();
        String age = ageField.getText();
        String bloodPressure = bloodPressureField.getText();

        if (patientIdText.isEmpty() || weight.isEmpty() || age.isEmpty() || bloodPressure.isEmpty()) {
            showAlert("Error", "Please fill in all required fields (Patient ID, Weight, Age, Blood Pressure)!");
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {
            int patientId = Integer.parseInt(patientIdText);

            // fetch doctor_id dynamically using assistant_id
            int doctorId = getDoctorIdForAssistant(assistantId, connection);

            String query = "INSERT INTO primary_checkup (patient_id, doctor_id, assistant_id, weight, age, blood_pressure) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedstatement = connection.prepareStatement(query);
            preparedstatement.setInt(1, patientId);
            preparedstatement.setInt(2, doctorId);
            preparedstatement.setInt(3, assistantId);
            preparedstatement.setDouble(4, Double.parseDouble(weight));
            preparedstatement.setInt(5, Integer.parseInt(age));
            preparedstatement.setString(6, bloodPressure);

            preparedstatement.executeUpdate();

            showAlert("Success", "Primary checkup added successfully for Patient ID: " + patientId);
            handleCancel(null);

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Patient ID, Weight, and Age must be numeric values.");
            e.printStackTrace();
        } catch (SQLException e) {
            showAlert("Database Error", "Could not insert data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int getDoctorIdForAssistant(int assistantId, Connection connection) throws SQLException {
        String query = "SELECT d_id FROM assistant WHERE assistant_id = ?";
        PreparedStatement preparedstatement = connection.prepareStatement(query);
        preparedstatement.setInt(1, assistantId);
        ResultSet rs = preparedstatement.executeQuery();
        if (rs.next()) {
            return rs.getInt("d_id");
        }
        throw new SQLException("Doctor not found for assistant: " + assistantId);
    }

    @FXML
    private void handleCancel(ActionEvent actionEvent) {
        patientIdField.clear();
        patientNameField.clear();
        contactNoField.clear();
        weightField.clear();
        ageField.clear();
        bloodPressureField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}