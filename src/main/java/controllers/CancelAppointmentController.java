package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Appointment;

import java.sql.*;

public class CancelAppointmentController {

    @FXML private TextField DoctorName;
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, String> dname;
    @FXML private TableColumn<Appointment, String> hospital;
    @FXML private TableColumn<Appointment, String> appdate;

    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        dname.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDoctorName()));
        hospital.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getHospital()));
        appdate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAppointmentDate()));

        appointmentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        loadAppointments(null);
    }

    private void loadAppointments(String doctorFilter) {
        appointments.clear();

        // TODO: Add your SQL query to fetch appointments here
        // Use doctorFilter (optional) and patient ID
        // Example placeholder:
        /*
        String query = "...";

        try (Connection conn = ...;
             PreparedStatement stmt = ...) {
            ...
            appointmentTable.setItems(appointments);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to load appointments.");
        }
        */
    }

    @FXML
    private void handleSearch() {
        String doctorName = DoctorName.getText();
        loadAppointments(doctorName);
    }

    @FXML
    private void handleCancelAppointment() {
        ObservableList<Appointment> selectedItems = appointmentTable.getSelectionModel().getSelectedItems();

        if (selectedItems.isEmpty()) {
            showAlert("No Selection", "Please select one or more appointments to cancel.");
            return;
        }

        // TODO: Add your SQL query to cancel appointments here
        // Loop through selectedItems and update appointment status
        /*
        String query = "...";

        try (Connection conn = ...;
             PreparedStatement stmt = ...) {
            for (Appointment appt : selectedItems) {
                stmt.setInt(1, appt.getAppointmentId());
                stmt.executeUpdate();
            }
            showAlert("Success", "Selected appointments have been cancelled.");
            loadAppointments(null);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Could not cancel appointments.");
        }
        */
    }

    private int getLoggedInPatientId() {
        // TODO: Replace with actual logic to get logged-in patient ID
        return 1;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

