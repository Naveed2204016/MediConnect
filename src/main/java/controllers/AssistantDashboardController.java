package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class AssistantDashboardController {

    @FXML
    private StackPane contentArea;

    @FXML
    private Label statusMessage;

    @FXML
    public void initialize() {
        try {
            Parent primaryCheckupView = FXMLLoader.load(getClass().getResource("/fxml/AssistantPrimaryCheckup.fxml"));
            contentArea.getChildren().setAll(primaryCheckupView);
            //statusMessage.setText("Primary Checkup loaded.");
        } catch (IOException e) {
            e.printStackTrace();
            statusMessage.setText("Error loading view.");
        }
    }

    @FXML
    private void loadPrimaryCheckup(ActionEvent event) {
        try {
            Parent primaryCheckupView = FXMLLoader.load(getClass().getResource("/fxml/AssistantPrimaryCheckup.fxml"));
            contentArea.getChildren().setAll(primaryCheckupView);
            //statusMessage.setText("Primary Checkup loaded.");
        } catch (IOException e) {
            e.printStackTrace();
            statusMessage.setText("Error loading view.");
        }
    }

    @FXML
    private void loadUpdateInfo(ActionEvent event) {
        try {
            Parent updateInfoView = FXMLLoader.load(getClass().getResource("/fxml/AssistantUpdateInfo.fxml"));
            contentArea.getChildren().setAll(updateInfoView);
            //statusMessage.setText("Update Information loaded.");
        } catch (IOException e) {
            e.printStackTrace();
            statusMessage.setText("Error loading view.");
        }
    }

    @FXML
    private void emergencyRequest(ActionEvent event) {
        try {
            Parent emergencyRequestView = FXMLLoader
                    .load(getClass().getResource("/fxml/AssistantEmergencyRequest.fxml"));
            contentArea.getChildren().setAll(emergencyRequestView);
            //statusMessage.setText("Emergency Requests displayed.");
        } catch (IOException e) {
            e.printStackTrace();
            statusMessage.setText("Error loading view.");
        }
    }

    @FXML
    private void cancelAppointment(ActionEvent event) {
        try {
            Parent cancelAppointmentView = FXMLLoader
                    .load(getClass().getResource("/fxml/AssistantCancelAppointment.fxml"));
            contentArea.getChildren().setAll(cancelAppointmentView);
            //statusMessage.setText("Cancel Appointments loaded.");
        } catch (IOException e) {
            e.printStackTrace();
            statusMessage.setText("Error loading view.");
        }
    }

    @FXML
    private void logout()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login_assistant.fxml"));
            Scene scene = new Scene(loader.load());
            LoginController loginController=loader.getController();
            loginController.setRole("assistant");
            Stage stage=(Stage) contentArea.getScene().getWindow();
            stage.setScene(scene);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}