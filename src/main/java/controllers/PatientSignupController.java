package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientSignupController {

    @FXML private TextField nameField;
    @FXML private DatePicker dobPicker;
    @FXML private TextField contactField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button backButton;

    @FXML
    private void handleSignup() {
        String name = nameField.getText().trim();
        String dob = (dobPicker.getValue() != null) ? dobPicker.getValue().toString() : "";
        String contact = contactField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        // TEMP: For now, just print the values
        System.out.println("Sign Up Info:");
        System.out.println("Name: " + name);
        System.out.println("DOB: " + dob);
        System.out.println("Contact: " + contact);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        // TODO: Validate and insert into database
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