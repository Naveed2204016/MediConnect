package controllers;

import db.DBConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.Patient;

import java.sql.*;

public class doctor_patient_detailscontroller {

    @FXML
    private TableView<Patient> patientc;

    @FXML
    private TableColumn<Patient, String> patname;

    @FXML
    private TableColumn<Patient, String> patemail;

    @FXML
    private TableColumn<Patient, String> patcontact;

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

                Patient p = new Patient();
                p.setName(name);
                p.setEmail(email);
                p.setContactNumber(contact);

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
}

