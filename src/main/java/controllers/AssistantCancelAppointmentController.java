package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Appointment;

import java.sql.*;

public class AssistantCancelAppointmentController {

    @FXML
    private TextField DoctorName;
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, String> dname;
    @FXML
    private TableColumn<Appointment, String> hospital;
    @FXML
    private TableColumn<Appointment, String> appdate;

    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        dname.setCellValueFactory(
                celldata->(celldata.getValue().getDoctorNameproperty()));
        hospital.setCellValueFactory(
                celldata -> (celldata.getValue().getHospitalproperty()));
        appdate.setCellValueFactory(
                celldata -> (celldata.getValue().getAppointmentDateproperty()));

        appointmentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        loadAppointments(null);
    }

    private void loadAppointments(String doctorFilter) {
        appointments.clear();


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
    }

    private int getLoggedInPatientId() {
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
