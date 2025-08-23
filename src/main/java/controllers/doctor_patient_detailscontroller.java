package controllers;

import db.DBConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Appointment;
import models.Patient;

import java.sql.*;

public class doctor_patient_detailscontroller {

    public TextField details;
    public Button adddetails;
    @FXML
    private TableView<Patient> patientc;

    @FXML
    private TableColumn<Patient, String> patname;

    @FXML
    private TableColumn<Patient, String> patemail;

    @FXML
    private TableColumn<Patient, String> patcontact;
    @FXML
    private TableColumn<Patient, String> pastvisit;
    @FXML
    private TextField patient_id;

    @FXML
    private Button searchd;

    private int userId;

    private ObservableList<Patient> patient2 = FXCollections.observableArrayList();

    public void setUserId(int userID) {
        this.userId = userID;
        loadpatientData();
    }

    @FXML
    public void initialize() {
        // Set up TableView columns

        patname.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        patemail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        patcontact.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getContactNumber()));
pastvisit.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPastvisit()));
        // Load data

    }

    public void loadpatientData() {
        patient2.clear();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DBConnection.getConnection();

            String query = "SELECT a.appointment_id, p.name AS patient_name, p.contact_number, p.email " +
                    "FROM appointment a JOIN patient p ON a.p_id = p.patient_id " +
                    "WHERE a.d_id = ? " +
                    "ORDER BY a.appointment_date";

            PreparedStatement pmt = connection.prepareStatement(query);
            pmt.setInt(1, userId);
            ResultSet rs = pmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("patient_name");
                String email = rs.getString("email");
                String contact = rs.getString("contact_number");
                String past = rs.getString("details");
                Patient p = new Patient();
                p.setName(name);
                p.setEmail(email);
                p.setContactNumber(contact);
                p.setPastvisit(past);
                patient2.add(p);
            }

            patientc.setItems(patient2);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error loading patients: " + e.getMessage());
        }
    }

    public void searchp(ActionEvent actionEvent) {
        String searchText = patient_id.getText().trim();
        if (searchText.isEmpty()) {
            loadpatientData();
            return;
        }

        try {
           String pid = (searchText);

            ObservableList<Patient> filteredList = FXCollections.observableArrayList();

            Connection connection = DBConnection.getConnection();
            String query = "SELECT a.appointment_id, p.patient_id, p.name AS patient_name, p.contact_number, p.email " +
                    "FROM appointment a JOIN patient p ON a.p_id = p.patient_id " +
                    "WHERE a.d_id = ? AND p.name= ? " +
                    "ORDER BY a.appointment_date";

            PreparedStatement pmt = connection.prepareStatement(query);
            pmt.setInt(1, userId);
            pmt.setString(2, pid);
            ResultSet rs = pmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("patient_name");
                String email = rs.getString("email");
                String contact = rs.getString("contact_number");
                String details = rs.getString("details");

                Patient p = new Patient();
                p.setName(name);
                p.setEmail(email);
                p.setContactNumber(contact);

                filteredList.add(p);
            }

            patientc.setItems(filteredList);

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid patient ID.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adddetails(ActionEvent actionEvent) {
        ObservableList<Patient> selectedItems = patientc.getSelectionModel().getSelectedItems();
        int y=selectedItems.getFirst().getpid();
        if (selectedItems.isEmpty()) {
            showAlert("No Selection", "Please select one or more appointments to cancel.");
            return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DBConnection.getConnection(); //DriverManager.getConnection(url, username, password);
            String query = "UPDATE patient SET details=? WHERE patient_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (Patient  P : selectedItems) {
                preparedStatement.setString(1, details.getText());
                preparedStatement.setInt(2, y);
                preparedStatement.executeUpdate();
            }
            connection.close();
            showAlert("Success", "Selected appointments have been cancelled successfully.");
            loadpatientData(); // Refresh the appointment list
        } catch (ClassNotFoundException e) {
            showAlert("Error", "Database driver not found: " + e.getMessage());
        } catch (SQLException e) {
            showAlert("Error", "Database error: " + e.getMessage());
        }

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}

