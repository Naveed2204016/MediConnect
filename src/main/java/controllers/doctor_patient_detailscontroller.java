package controllers;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.record1;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class doctor_patient_detailscontroller {

    @FXML
    private TableView<record1> patientc;
    @FXML
    private TableColumn<record1, String> patname;
    @FXML
    private TableColumn<record1, LocalDate> visit_date;
    @FXML
    private TableColumn<record1, String> diagnosis;
    @FXML
    private TableColumn<record1, String> treatment;
    @FXML
    private TableColumn<record1, String> notes;
    @FXML
    private TableColumn<record1, String> prescribed_test;

    @FXML
    private TextField details; // input field for updates

    private ObservableList<record1> record2 = FXCollections.observableArrayList();

    private int doctorId; // set this from login/session
    public void setUserId(int userId) {
        this.doctorId = userId;
        loadpatientData(userId); // load patient records for this doctor
    }
    public void initialize() {
        // Bind columns to record1 properties
        patname.setCellValueFactory(data -> data.getValue().patientnameProperty());
        visit_date.setCellValueFactory(data -> data.getValue().visitDateProperty());
        diagnosis.setCellValueFactory(data -> data.getValue().diagnosisProperty());
        treatment.setCellValueFactory(data -> data.getValue().treatmentProperty());
        notes.setCellValueFactory(data -> data.getValue().notesProperty());
        prescribed_test.setCellValueFactory(data -> data.getValue().prescribedTestProperty());

        // Enable multiple row selection
        patientc.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Set the observable list
        patientc.setItems(record2);
    }
    // ------------------ Load Patient Data ------------------
    public void loadpatientData(int userId) {
        this.doctorId = userId;
        record2.clear();

        String query = "SELECT r.p_id, p.name AS patient_name, visit_date, diagnosis, treatment, notes, prescribed_test " +
                "FROM record r JOIN patient p ON r.p_id = p.patient_id " +
                "WHERE r.d_id = ? " +
                "ORDER BY r.visit_date";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pmt = connection.prepareStatement(query)) {

            pmt.setInt(1, userId);
            ResultSet rs = pmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("p_id");
                String name = rs.getString("patient_name");
                LocalDate visit = rs.getDate("visit_date").toLocalDate();
                String diag = rs.getString("diagnosis");
                String treat = rs.getString("treatment");
                String note = rs.getString("notes");
                String test = rs.getString("prescribed_test");

                record1 r = new record1(id, visit, diag, treat, note, test, name);
                record2.add(r);
            }

            patientc.setItems(record2);

        } catch (SQLException e) {
            showAlert("Database Error", e.getMessage());
        }
    }

    // ------------------ Update Methods ------------------
    @FXML
    private void updateName(ActionEvent e) {
        updateField("patient", "name", details.getText(), false);
    }

    @FXML
    private void updateDate(ActionEvent e) {
        LocalDate visitDate;
        try {
            visitDate = LocalDate.parse(details.getText()); // yyyy-MM-dd
        } catch (DateTimeParseException ex) {
            showAlert("Invalid Input", "Enter date as yyyy-MM-dd.");
            return;
        }
        updateField("record", "visit_date", java.sql.Date.valueOf(visitDate), true);
    }

    @FXML
    private void updateDiagnosis(ActionEvent e) {
        updateField("record", "diagnosis", details.getText(), false);
    }

    @FXML
    private void updateTreatment(ActionEvent e) {
        updateField("record", "treatment", details.getText(), false);
    }

    @FXML
    private void updateNotes(ActionEvent e) {
        updateField("record", "notes", details.getText(), false);
    }

    @FXML
    private void updateTest(ActionEvent e) {
        updateField("record", "prescribed_test", details.getText(), false);
    }

    // ------------------ Generic Update Helper ------------------
    private void updateField(String table, String column, Object newValue, boolean isDate) {
        ObservableList<record1> selectedItems = patientc.getSelectionModel().getSelectedItems();
        if (selectedItems.isEmpty()) {
            showAlert("No Selection", "Please select a patient first.");
            return;
        }

        String query = "UPDATE " + table + " SET " + column + "=? WHERE patient_id=?";

        // special case → record table uses p_id instead of patient_id
        if (table.equals("record")) {
            query = "UPDATE record SET " + column + "=? WHERE p_id=?";
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (record1 P : selectedItems) {
                if (isDate) {
                    preparedStatement.setDate(1, (Date) newValue);
                } else {
                    preparedStatement.setString(1, newValue.toString());
                }
                preparedStatement.setInt(2, P.getPId());
                preparedStatement.executeUpdate();
            }

            showAlert("Success", "Updated " + column + " successfully.");
            loadpatientData(doctorId); // refresh table

        } catch (SQLException e) {
            showAlert("Database Error", e.getMessage());
        }
    }

    // ------------------ Alert Helper ------------------
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
