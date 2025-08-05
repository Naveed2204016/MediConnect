package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import models.Doctor; // Make sure you have this model class

public class PatientSearchDoctorsController {

    @FXML private TextField specializationField;
    @FXML private TextField locationField;
    @FXML private TableView<Doctor> doctorsTable;
    @FXML private TableColumn<Doctor, String> nameCol;
    @FXML private TableColumn<Doctor, String> specializationCol;
    @FXML private TableColumn<Doctor, String> locationCol;
    @FXML private TableColumn<Doctor, Double> feeCol;
    @FXML private TableColumn<Doctor, String> availabilityCol;

    // Sample data - remove this when you connect to real database
    private ObservableList<Doctor> sampleDoctors = FXCollections.observableArrayList(
            new Doctor("Dr. Smith", "Cardiology", "Dhanmondi", 1500.0, "Mon-Fri 9AM-5PM"),
            new Doctor("Dr. Johnson", "Neurology", "Gulshan", 2000.0, "Tue-Sat 10AM-6PM")
    );

    @FXML
    public void initialize() {
        // Set up table columns
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        specializationCol.setCellValueFactory(cellData -> cellData.getValue().specializationProperty());
        locationCol.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        feeCol.setCellValueFactory(cellData -> cellData.getValue().feeProperty().asObject());
        availabilityCol.setCellValueFactory(cellData -> cellData.getValue().availabilityProperty());

        // Load sample data (remove when using real database)
        doctorsTable.setItems(sampleDoctors);
    }

    @FXML
    private void handleSearch() {
        // Placeholder for search functionality
        System.out.println("Search clicked - Specialization: " + specializationField.getText()
                + ", Location: " + locationField.getText());
    }

    @FXML
    private void handleBook() {
        Doctor selectedDoctor = doctorsTable.getSelectionModel().getSelectedItem();
        if (selectedDoctor != null) {
            System.out.println("Booking appointment with: " + selectedDoctor.getName());
        } else {
            showAlert("No Selection", "Please select a doctor first.");
        }
    }

    @FXML
    private void handleEmergency() {
        System.out.println("Emergency request initiated");
        showAlert("Emergency Request", "Emergency request has been sent to the nearest available doctor.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}