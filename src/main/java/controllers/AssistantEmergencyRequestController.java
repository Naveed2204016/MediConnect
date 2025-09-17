package controllers;

import db.DBConnection;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Appointment;
import models.EmergencyRequest;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AssistantEmergencyRequestController {


    @FXML
    private TableView<EmergencyRequest> emergency_req;
    @FXML
    public TableColumn <EmergencyRequest,String> request_id;


    @FXML
    private TableColumn<EmergencyRequest, String> patient_id;

    @FXML
    private TableColumn<EmergencyRequest, String> details;

    @FXML
    private TableColumn<EmergencyRequest, String> contact;

    @FXML
    private TableColumn<EmergencyRequest, LocalDateTime> request_date;

    @FXML
    private TableColumn<EmergencyRequest, LocalDateTime> tentative_date;

    @FXML
    private TableColumn<EmergencyRequest, String> status;

    private ObservableList<EmergencyRequest> emergencyRequests1 = FXCollections.observableArrayList();
    private int assistantId;

    // Set assistant ID from previous controller
    public void setAssistantId(int assistantId) {
        this.assistantId = assistantId;
        loadPatientData();
    }

    @FXML
    public void initialize() {

        patient_id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPatientId()));
        details.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDetails()));
        contact.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getContact()));
        status.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        request_date.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getRequestDate()));
        tentative_date.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getTentativeDate()));
        request_id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRequest_id()));

        emergency_req.setItems(emergencyRequests1);
    }

    private void loadPatientData() {
        emergencyRequests1.clear();

        String query = "SELECT e.request_id,e.p_id, e.symptoms, e.request_date, e.tentative_date, e.status, p.contact_number " +
                "FROM emergency_request AS e " +
                "JOIN patient AS p ON e.p_id = p.patient_id " +
                "WHERE e.a_id = ? and e.status='pending'";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, assistantId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String request_id = rs.getString("request_id");
                String patientId = rs.getString("p_id");
                String symptoms = rs.getString("symptoms");
                String contactNum = rs.getString("contact_number");
                Timestamp requestTs = rs.getTimestamp("request_date");
                Timestamp tentativeTs = rs.getTimestamp("tentative_date");

                LocalDateTime requestDate = requestTs != null ? requestTs.toLocalDateTime() : null;
                LocalDateTime tentativeDate = tentativeTs != null ? tentativeTs.toLocalDateTime() : null;

                String statusVal = rs.getString("status");

                EmergencyRequest request = new EmergencyRequest(
                        request_id,
                        patientId, symptoms, contactNum,
                        requestDate, tentativeDate, statusVal
                );

                emergencyRequests1.add(request);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void reject_req(ActionEvent event) {
        ObservableList<EmergencyRequest> selectedItems = emergency_req.getSelectionModel().getSelectedItems();

        if (selectedItems.isEmpty()) {
            showAlert("No Selection", "Please select one or more appointments to cancel.");
            return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DBConnection.getConnection();
            String query = "UPDATE emergency_request SET status=?,response_seen=?, WHERE request_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (EmergencyRequest r : selectedItems) {
                preparedStatement.setString(1, "rejected");
                preparedStatement.setString(2,"seen");
                preparedStatement.setInt(3, Integer.parseInt(r.getRequest_id()));
                preparedStatement.executeUpdate();
            }

            connection.close();
            showAlert("Success", "Selected appointments have been cancelled successfully.");
            loadPatientData(); // Refresh the table
        } catch (ClassNotFoundException e) {
            showAlert("Error", "Database driver not found: " + e.getMessage());
        } catch (SQLException e) {
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }




    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void accept_req(ActionEvent actionEvent) {
        ObservableList<EmergencyRequest> selectedItems = emergency_req.getSelectionModel().getSelectedItems();

        if (selectedItems.isEmpty()) {
            showAlert("No Selection", "Please select one or more emergency requests to accept.");
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {

            String updateQuery = "UPDATE emergency_request SET status=?,response_seen=?, tentative_date=? WHERE request_id=?";
            PreparedStatement updateStmt = connection.prepareStatement(updateQuery);

            String countQuery = "SELECT COUNT(*) AS total_requests FROM emergency_request WHERE a_id=? AND status='confirmed' AND tentative_date>?";
            PreparedStatement countStmt = connection.prepareStatement(countQuery);

            String slotQuery = "SELECT emergency_slots_per_day FROM doctor WHERE doctor_id=?";
            PreparedStatement slotStmt = connection.prepareStatement(slotQuery);

            for (EmergencyRequest r : selectedItems) {

                countStmt.setInt(1,assistantId);
                countStmt.setDate(2, Date.valueOf(LocalDate.now()));
                ResultSet rsCount = countStmt.executeQuery();
                int requestcount = 0;
                if (rsCount.next()) {
                    requestcount = rsCount.getInt("total_requests");
                }


                slotStmt.setInt(1, assistantId);
                ResultSet rsSlot = slotStmt.executeQuery();
                int slotsPerDay = 1;
                if (rsSlot.next()) {
                    slotsPerDay = rsSlot.getInt("emergency_slots_per_day");
                }


                int daysToAdd = (requestcount / slotsPerDay);
                LocalDateTime tentativeDate = LocalDateTime.now().plusDays(daysToAdd+1);


                updateStmt.setString(1, "proposed");
                updateStmt.setString(2,"seen");
                updateStmt.setTimestamp(3, Timestamp.valueOf(tentativeDate));

                updateStmt.setInt(4, Integer.parseInt(r.getRequest_id()));
                updateStmt.executeUpdate();

                r.setStatus("proposed");
                r.setTentativeDate(tentativeDate);
            }

            emergency_req.refresh();
            showAlert("Success", "Selected requests have been accepted.");

        } catch (SQLException e) {
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }
}