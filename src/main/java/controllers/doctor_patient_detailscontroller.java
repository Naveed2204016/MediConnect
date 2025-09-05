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

public class doctor_patient_detailscontroller {

    public TextField patd;
    public TextField patient_name;
    public TextField Pname;
    public TextField Pid;
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

        String query = "SELECT r.record_id, r.p_id, r.d_id, p.name AS patient_name, " +
                "r.visit_date, r.diagnosis, r.treatment, r.notes, r.prescribed_test " +
                "FROM record r JOIN patient p ON r.p_id = p.patient_id " +
                "WHERE r.d_id = ? " +
                "ORDER BY r.visit_date";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pmt = connection.prepareStatement(query)) {

            pmt.setInt(1, userId);
            ResultSet rs = pmt.executeQuery();

            while (rs.next()) {
                int recordId = rs.getInt("record_id");
                int pId = rs.getInt("p_id");
                int dId = rs.getInt("d_id");
                String name = rs.getString("patient_name");
                LocalDate visit = rs.getDate("visit_date").toLocalDate();
                String diag = rs.getString("diagnosis");
                String treat = rs.getString("treatment");
                String note = rs.getString("notes");
                String test = rs.getString("prescribed_test");

                // Use full constructor
                record1 r = new record1(recordId, pId, dId, visit, diag, treat, note, test, name);
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

        String query = "UPDATE record SET " + column + "=? WHERE p_id=? AND record_id=?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (record1 P : selectedItems) {
                if (isDate) {
                    preparedStatement.setDate(1, (Date) newValue);
                } else {
                    preparedStatement.setString(1, newValue.toString());
                }
                preparedStatement.setInt(2, P.getPId());
                preparedStatement.setInt(3, P.getRecordId());
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

    // ------------------ Add New Record ------------------
    public void addnew(ActionEvent actionEvent) {
        String patientId = Pid.getText();
        String patientName = Pname.getText();

        if (patientId.isEmpty() || patientName.isEmpty()) {
            showAlert("Input Error", "Please enter both Patient ID and Name.");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DBConnection.getConnection();

            String query = "INSERT INTO record(p_id, d_id, visit_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, Integer.parseInt(patientId));
            preparedStatement.setInt(2, doctorId);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                showAlert("Success", "New record added successfully.");
                loadpatientData(doctorId);
            }

            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            showAlert("Error", "Database driver not found: " + e.getMessage());
        } catch (SQLException e) {
            showAlert("Database Error", e.getMessage());
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Patient ID must be a number.");
        }
    }

    // ------------------ Search ------------------
    public void searchd(ActionEvent actionEvent) {
        String patientname = patient_name.getText();
        int pid = Integer.parseInt(patd.getText());
        record2.clear();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DBConnection.getConnection();

            String query = "SELECT r.record_id, r.d_id, p.name, r.p_id, r.visit_date, r.diagnosis, " +
                    "r.treatment, r.notes, r.prescribed_test " +
                    "FROM patient AS p, record AS r " +
                    "WHERE p.patient_id = r.p_id AND p.name = ? AND r.p_id = ? AND r.d_id = ?";

            PreparedStatement pmt = connection.prepareStatement(query);
            pmt.setString(1, patientname);
            pmt.setInt(2, pid);
            pmt.setInt(3, doctorId);

            ResultSet rs = pmt.executeQuery();
            while (rs.next()) {
                int recordId = rs.getInt("record_id");
                int dId = rs.getInt("d_id");
                String name = rs.getString("name");
                LocalDate visitDate = rs.getDate("visit_date").toLocalDate();
                String diag = rs.getString("diagnosis");
                String treatment = rs.getString("treatment");
                String notes = rs.getString("notes");
                String prescribed_test = rs.getString("prescribed_test");

                record1 re = new record1(recordId, pid, dId, visitDate, diag, treatment, notes, prescribed_test, name);
                record2.add(re);
            }

            patientc.setItems(record2);
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
