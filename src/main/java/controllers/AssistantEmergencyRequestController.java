package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import models.EmergencyRequest;

public class AssistantEmergencyRequestController {

    @FXML
    private TableView<EmergencyRequest> emergencyTable;

    @FXML
    private TableColumn<EmergencyRequest, String> patientIdColumn;

    @FXML
    private TableColumn<EmergencyRequest, String> detailsColumn;

    @FXML
    private TableColumn<EmergencyRequest, String> contactColumn;

    // You can also add controllers for the buttons if needed, but methods are
    // enough
    // for this simple case.

    // An ObservableList to hold the data for the TableView
    private ObservableList<EmergencyRequest> emergencyRequests = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize the table columns
        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        detailsColumn.setCellValueFactory(new PropertyValueFactory<>("details"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        // Load some dummy data for demonstration
//        emergencyRequests.add(new EmergencyRequest("P001", "Chest Pain", "123-456-7890"));
//        emergencyRequests.add(new EmergencyRequest("P002", "Severe Headache", "987-654-3210"));
//        emergencyRequests.add(new EmergencyRequest("P003", "Accident Injury", "555-123-4567"));

        // Set the data to the TableView
        emergencyTable.setItems(emergencyRequests);
    }

    @FXML
    private void handleAccept() {
        EmergencyRequest selectedRequest = emergencyTable.getSelectionModel().getSelectedItem();
        if (selectedRequest != null) {
            System.out.println("Emergency request accepted for Patient ID: " + selectedRequest.getPatientId());
            // Remove the request from the table after accepting
            emergencyRequests.remove(selectedRequest);

            // Add your business logic here to process the accepted request.
            showAlert("Success", "Request Accepted",
                    "Emergency request for Patient ID " + selectedRequest.getPatientId() + " has been accepted.");
        } else {
            showAlert("No Selection", "No Request Selected",
                    "Please select an emergency request from the table to accept.");
        }
    }

    @FXML
    private void handleReject() {
        EmergencyRequest selectedRequest = emergencyTable.getSelectionModel().getSelectedItem();
        if (selectedRequest != null) {
            System.out.println("Emergency request rejected for Patient ID: " + selectedRequest.getPatientId());
            // Remove the request from the table after rejecting
            emergencyRequests.remove(selectedRequest);

            // Add your business logic here to process the rejected request.
            showAlert("Success", "Request Rejected",
                    "Emergency request for Patient ID " + selectedRequest.getPatientId() + " has been rejected.");
        } else {
            showAlert("No Selection", "No Request Selected",
                    "Please select an emergency request from the table to reject.");
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}