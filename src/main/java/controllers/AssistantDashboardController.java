package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class AssistantDashboardController {

    @FXML private StackPane contentArea;
    @FXML private Label statusMessage;

    private int userID;
    private int assistantId; // dynamic assistant id

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setAssistantId(int assistantId) {
        this.assistantId = assistantId;
        loadPrimaryCheckup(); // Load primary checkup AFTER assistantId is set
    }

    private void loadPrimaryCheckup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AssistantPrimaryCheckup.fxml"));
            Parent primaryCheckupView = loader.load();

            // Pass dynamic assistantId
            AssistantPrimaryCheckupController checkupController = loader.getController();
            checkupController.setAssistantId(this.assistantId);

            contentArea.getChildren().setAll(primaryCheckupView);
        } catch (IOException e) {
            e.printStackTrace();
            statusMessage.setText("Error loading Primary Checkup.");
        }
    }

    @FXML
    private void loadPrimaryCheckup(ActionEvent event) {
        loadPrimaryCheckup();
    }

    @FXML
    private void loadUpdateInfo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AssistantUpdateInfo.fxml"));
            Parent updateInfoView = loader.load();
            AssistantUpdateInfoController updateController = loader.getController();
            updateController.setAssistantId(this.assistantId);
            contentArea.getChildren().setAll(updateInfoView);
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
        } catch (IOException e) {
            e.printStackTrace();
            statusMessage.setText("Error loading view.");
        }
    }

    @FXML
    private void logout(ActionEvent event) {
        System.out.println("Assistant is logging out.");
        statusMessage.setText("Logged out successfully.");
    }
}
