package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class DoctorSignupController {

    @FXML private TextField nameField;
    @FXML private TextField hospitalField;
    @FXML private TextField cityField;
    @FXML private TextField startTimeField;
    @FXML private TextField endTimeField;
    @FXML private TextField specializationField;
    @FXML private TextField qualificationField;
    @FXML private TextField feesField;
    @FXML private TextField contactField;
    @FXML private TextField emailField;
    @FXML private TextField capacityField;
    @FXML private TextField emergencySlotsField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button backButton;


    @FXML
    private void handleSignup() {
        String name = nameField.getText().trim();
        String hospital = hospitalField.getText().trim();
        String city = cityField.getText().trim();
        String startTime = startTimeField.getText().trim();
        String endTime = endTimeField.getText().trim();
        String specialization = specializationField.getText().trim();
        String qualification = qualificationField.getText().trim();
        String fees = feesField.getText().trim();
        String contact = contactField.getText().trim();
        String email = emailField.getText().trim();
        String capacity = capacityField.getText().trim();
        String emergencySlots = emergencySlotsField.getText().trim();
        String password = passwordField.getText().trim();

        // TEMP: Print to console
        System.out.println("Doctor Signup Info:");
        System.out.println("Name: " + name);
        System.out.println("Hospital: " + hospital);
        System.out.println("City: " + city);
        System.out.println("Time: " + startTime + " to " + endTime);
        System.out.println("Specialization: " + specialization);
        System.out.println("Qualification: " + qualification);
        System.out.println("Fees: " + fees);
        System.out.println("Contact: " + contact);
        System.out.println("Email: " + email);
        System.out.println("Capacity/day: " + capacity);
        System.out.println("Emergency Slots/day: " + emergencySlots);
        System.out.println("Password: " + password);

        // TODO: Validate + insert into database
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
