package controllers;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Doctor;

import java.sql.*;
import java.time.LocalTime;

public class Doctors_update_infoController {

    public Button addLocationBtn;
    public TextField hospitalField;
    public TextField startTimeField;
    public TextField endTimeField;
    public TextField hospital2Field;
    public TextField cityField;
    @FXML private TextField nameField, feeField, contactField, emailField, qualificationField, specializationField;
    @FXML private PasswordField passwordField, confirmPasswordField;
    @FXML private TextField patientSlotField, emergencySlotField;
    @FXML private TableView<Doctor.DoctorLocation> locationTable;

    @FXML private TableColumn<Doctor.DoctorLocation, String> hospitalCol;
    @FXML private TableColumn<Doctor.DoctorLocation, String> cityCol;
    @FXML private TableColumn<Doctor.DoctorLocation, String> stCol;
    @FXML private TableColumn<Doctor.DoctorLocation, String> edCol;
    @FXML private Label statusLabel;

    private Doctor currentDoctor;
    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
        loadData();
    }

    public int getUserId() { return userId; }
    ObservableList<Doctor.DoctorLocation> locations = FXCollections.observableArrayList();
    private void loadData() {
        try {
            Connection connection = DBConnection.getConnection();

            String doctorQuery = "SELECT * FROM doctor WHERE doctor_id=?";
            PreparedStatement psDoctor = connection.prepareStatement(doctorQuery);
            psDoctor.setInt(1, userId);
            ResultSet rsDoctor = psDoctor.executeQuery();

            String locationQuery = "select * from location where d_id=?";
            PreparedStatement psLoc = connection.prepareStatement(locationQuery);
            psLoc.setInt(1, userId);
            ResultSet rsLoc = psLoc.executeQuery();

            currentDoctor = new Doctor();

            if (rsDoctor.next()) {
                currentDoctor.setName(rsDoctor.getString("name"));
                currentDoctor.setFee(rsDoctor.getDouble("fees"));
                currentDoctor.setQualification(rsDoctor.getString("qualification"));
                currentDoctor.setSpecialization(rsDoctor.getString("specialization"));
                currentDoctor.setPatientslot(rsDoctor.getInt("capacity_per_day"));
                currentDoctor.setEmergencyslot(rsDoctor.getInt("emergency_slots_per_day"));
                currentDoctor.setContactNumber(rsDoctor.getString("contact_number"));
                currentDoctor.setEmail(rsDoctor.getString("email"));
                currentDoctor.setPassword(rsDoctor.getString("password"));
            }

            while (rsLoc.next()) {
                locations.add(new Doctor.DoctorLocation(
                        rsLoc.getString("city"),
                        rsLoc.getTime("start_time").toLocalTime().toString(),
                        rsLoc.getTime("End_time").toLocalTime().toString(),
                        rsLoc.getString("hospital")


                ));
            }

            locationTable.setItems(locations);
            cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
            stCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            edCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            hospitalCol.setCellValueFactory(new PropertyValueFactory<>("hospital"));

            // Set doctor fields
            nameField.setText(currentDoctor.getName());
            contactField.setText(currentDoctor.getContactNumber());
            emailField.setText(currentDoctor.getEmail());
            passwordField.setText(currentDoctor.getPassword());
            feeField.setText(currentDoctor.getFee() + "");
            qualificationField.setText(currentDoctor.getQualification());
            specializationField.setText(currentDoctor.getSpecialization());
            patientSlotField.setText(currentDoctor.getPatientslot() + "");
            emergencySlotField.setText(currentDoctor.getEmergencyslot() + "");

        } catch (SQLException e) {
            statusLabel.setText("Database error: " + e.getMessage());
        }
    }

    @FXML
    private void handleSaveDoctor() {
        try {
            currentDoctor.setName(nameField.getText());
            currentDoctor.setEmail(emailField.getText());
            currentDoctor.setContactNumber(contactField.getText());
            currentDoctor.setQualification(qualificationField.getText());
            currentDoctor.setSpecialization(specializationField.getText());
            currentDoctor.setPatientslot(Integer.parseInt(patientSlotField.getText()));
            currentDoctor.setEmergencyslot(Integer.parseInt(emergencySlotField.getText()));
            currentDoctor.setFee(Double.parseDouble(feeField.getText().replaceAll("[^0-9.]", "")));

            if (!passwordField.getText().isEmpty() && passwordField.getText().equals(confirmPasswordField.getText())) {
                currentDoctor.setPassword(passwordField.getText());
            }

            Connection connection = DBConnection.getConnection();
            String updateQuery = "UPDATE doctor SET name=?, email=?, contact_number=?, specialization=?, qualification=?, fees=?, capacity_per_day=?, emergency_slots_per_day=?, password=? WHERE doctor_id=?";
            PreparedStatement ps = connection.prepareStatement(updateQuery);
            ps.setString(1, currentDoctor.getName());
            ps.setString(2, currentDoctor.getEmail());
            ps.setString(3, currentDoctor.getContactNumber());
            ps.setString(4, currentDoctor.getSpecialization());
            ps.setString(5, currentDoctor.getQualification());
            ps.setDouble(6, currentDoctor.getFee());
            ps.setInt(7, currentDoctor.getPatientslot());
            ps.setInt(8, currentDoctor.getEmergencyslot());
            ps.setString(9, currentDoctor.getPassword());
            ps.setInt(10, getUserId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                showAlert("Information updated successfully!");
            } else {
                showAlert("Update failed!");
            }

        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancelDoctor() {
        loadData(); // reload original data
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Update Status");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void addlocation(ActionEvent actionEvent) throws SQLException {
        String hospital = hospital2Field.getText();
        String city = cityField.getText();
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();

        // Create new DoctorLocation
        Doctor.DoctorLocation newLocation = new Doctor.DoctorLocation(city, startTime, endTime, hospital);

        // Add to ObservableList (updates TableView automatically)
        locations.add(newLocation);

        // Insert into database
        Connection connection = DBConnection.getConnection();
        String insertQuery = "INSERT INTO location (d_id, hospital, city, start_time, End_time) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(insertQuery);

        ps.setInt(1, getUserId()); // Doctor ID
        ps.setString(2, hospital);
        ps.setString(3, city);
        ps.setTime(4, java.sql.Time.valueOf(startTime));
        ps.setTime(5, java.sql.Time.valueOf(endTime));

        int rows = ps.executeUpdate();

        if (rows > 0) {
            showAlert("Location added successfully!");
        } else {
            showAlert("Failed to add location!");
        }

        // Optional: clear input fields after adding
        hospitalField.clear();
        cityField.clear();
        startTimeField.clear();
        endTimeField.clear();
    }

    public void removelocation(ActionEvent actionEvent) {

        Doctor.DoctorLocation selectedLocation = locationTable.getSelectionModel().getSelectedItem();

        if (selectedLocation == null) {
            showAlert("Please select a location to remove!");
            return;
        }

        try {
            Connection connection = DBConnection.getConnection();
            String deleteQuery = "DELETE FROM location WHERE d_id=? AND hospital=? AND city=? AND start_time=? AND End_time=?";
            PreparedStatement ps = connection.prepareStatement(deleteQuery);

            ps.setInt(1, getUserId()); // Doctor ID
            ps.setString(2, selectedLocation.getHospital());
            ps.setString(3, selectedLocation.getCity());
            LocalTime start = LocalTime.parse(selectedLocation.getStartTime());
            LocalTime end = LocalTime.parse(selectedLocation.getEndTime());

            ps.setTime(4, java.sql.Time.valueOf(start));
            ps.setTime(5, java.sql.Time.valueOf(end));

            int rows = ps.executeUpdate();

            if (rows > 0) {
                locations.remove(selectedLocation); // remove from ObservableList
                showAlert("Location removed successfully!");
            } else {
                showAlert("Failed to remove location!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error: " + e.getMessage());
        }
    }

}
