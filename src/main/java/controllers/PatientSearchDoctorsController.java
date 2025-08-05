package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PatientSearchDoctorsController {

    @FXML private TextField specializationField;
    @FXML private TextField locationField;
    @FXML private TableView<?> doctorsTable;

    @FXML private void handleSearch() {
        // TODO: Add MySQL query to fetch doctors
    }

    @FXML private void handleBook() {
        // TODO: Insert into appointment table
    }

    @FXML private void handleEmergency() {
        // TODO: Insert into emergency request table
    }
}
