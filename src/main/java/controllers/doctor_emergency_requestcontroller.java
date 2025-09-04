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
import java.time.LocalDateTime;

public class doctor_emergency_requestcontroller {

    @FXML
    private TableView<EmergencyRequest> emergency_req;

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
    private int doctorId;

    // Set doctor ID from previous controller
    public void setUserId(int doctorId) {
        this.doctorId = doctorId;
        loadPatientData();
    }

    @FXML
    public void initialize() {
        // Map table columns to EmergencyRequest getters
        patient_id.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPatientId()));
        details.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDetails()));
        contact.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getContact()));
        status.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
        request_date.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getRequestDate()));
        tentative_date.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getTentativeDate()));

        emergency_req.setItems(emergencyRequests1);
    }

    private void loadPatientData() {
        emergencyRequests1.clear();

        String query = "SELECT e.p_id, e.symptoms, e.request_date, e.tentative_date, e.status, p.contact_number " +
                "FROM emergency_request AS e " +
                "JOIN patient AS p ON e.p_id = p.patient_id " +
                "WHERE e.d_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String patientId = rs.getString("p_id");
                String symptoms = rs.getString("symptoms");
                String contactNum = rs.getString("contact_number");

                // Use getTimestamp to match LocalDateTime column type
                Timestamp requestTs = rs.getTimestamp("request_date");
                Timestamp tentativeTs = rs.getTimestamp("tentative_date");

                LocalDateTime requestDate = requestTs != null ? requestTs.toLocalDateTime() : null;
                LocalDateTime tentativeDate = tentativeTs != null ? tentativeTs.toLocalDateTime() : null;

                String statusVal = rs.getString("status");

                EmergencyRequest request = new EmergencyRequest(
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
            String query = "UPDATE emergency_request SET status=? WHERE p_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (EmergencyRequest r : selectedItems) {
                preparedStatement.setString(1, "Cancelled");
                preparedStatement.setInt(2, Integer.parseInt(r.getPatientId())); // convert patientId to int
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

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DBConnection.getConnection();

            // Prepare statements
            String updateQuery = "UPDATE emergency_request SET status=?, tentative_date=? WHERE p_id=?";
            PreparedStatement updateStmt = connection.prepareStatement(updateQuery);

            String countQuery = "SELECT COUNT(*) AS total_requests FROM emergency_request WHERE d_id=? AND status='ACCEPTED'";
            PreparedStatement countStmt = connection.prepareStatement(countQuery);

            String slotQuery = "SELECT emergency_slots_per_day FROM doctor WHERE doctor_id=?";
            PreparedStatement slotStmt = connection.prepareStatement(slotQuery);
/* ami selected item er tentive date ar status change korbo tar jnno select korlam and req number and emrgncy slot er basis e tatitve date ber korbo
   er jnnp count statemnt ta run kre emrgncy reqst and slot gulo ber kore tetative date ber krsi then add kore dsi slcted
   item e
   */
            for (EmergencyRequest r : selectedItems) {
                int doctorId = this.doctorId;
                countStmt.setInt(1, doctorId);
                ResultSet rsCount = countStmt.executeQuery();
                int totalRequests = 0;
                if (rsCount.next()) totalRequests = rsCount.getInt("total_requests");


                slotStmt.setInt(1, doctorId);
                ResultSet rsSlot = slotStmt.executeQuery();
                int slotsPerDay = 1;
                if (rsSlot.next()) slotsPerDay = rsSlot.getInt("emergency_slots_per_day");

                int daysToAdd = totalRequests / slotsPerDay;
                LocalDateTime tentativeDate = r.getRequestDate().plusDays(daysToAdd);


                updateStmt.setString(1, "ACCEPTED");
                updateStmt.setDate(2, java.sql.Date.valueOf(tentativeDate.toLocalDate()));
                updateStmt.setInt(3, Integer.parseInt(r.getPatientId()));
                updateStmt.executeUpdate();


                r.setStatus("ACCEPTED");
                r.setTentativeDate(tentativeDate);
            }

            connection.close();
            emergency_req.refresh();
            showAlert("Success", "Selected requests have been accepted.");
        } catch (ClassNotFoundException e) {
            showAlert("Error", "Database driver not found: " + e.getMessage());
        } catch (SQLException e) {
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }


}
