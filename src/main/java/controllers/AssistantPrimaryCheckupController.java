package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AssistantPrimaryCheckupController {

    @FXML
    private TextField patientNameField;
    @FXML
    private TextField contactNoField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField bloodPressureField;

    @FXML
    private void handleAddPatient(ActionEvent event) {
        String patientName = patientNameField.getText();
        String contactNo = contactNoField.getText();
        String weight = weightField.getText();
        String age = ageField.getText();
        String bloodPressure = bloodPressureField.getText();

        System.out.println("Adding patient data:");
        System.out.println("Patient Name: " + patientName);
        System.out.println("Contact No: " + contactNo);
        System.out.println("Weight: " + weight);
        System.out.println("Age: " + age);
        System.out.println("Blood Pressure: " + bloodPressure);

        // Add logic to save data to a database or other storage.
        System.out.println("Patient " + patientName + " added successfully!");
    }
}