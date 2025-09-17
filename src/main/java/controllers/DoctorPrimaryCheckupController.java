package controllers;

import db.DBConnection;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.primary_checkup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorPrimaryCheckupController {

    @FXML
    public TableView<primary_checkup> primarycheckuptable;

    @FXML
    public TextField pid;
    @FXML
    public TextField pname;
    @FXML
    public TextField pcontactno;

    @FXML
    public TableColumn<primary_checkup, String> p_name;
    @FXML
    public TableColumn<primary_checkup, Integer> page;
    @FXML
    public TableColumn<primary_checkup, Integer> pweight;
    @FXML
    public TableColumn<primary_checkup, String> pbloodpressure;

    private ObservableList<primary_checkup> pc = FXCollections.observableArrayList();
    private int doctorId;

    // Set doctor ID from previous controller
    public void setUserId(int doctorId) {
        this.doctorId = doctorId;
        loadcheckupdata();
    }

    @FXML
    public void initialize() {
        p_name.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        page.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getAge()));
        pweight.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getWeight()));
        pbloodpressure.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBlood_pressure()));

        primarycheckuptable.setItems(pc);
    }

    private void loadcheckupdata() {
        pc.clear();

        String query = "SELECT p.name, doctor_id, weight, age, blood_pressure " +
                "FROM primary_checkup pc " +
                "JOIN patient p ON pc.patient_id = p.patient_id " +
                "WHERE doctor_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int age = rs.getInt("age");
                int weight = rs.getInt("weight");
                String blood_pressure = rs.getString("blood_pressure"); // FIXED
                String name = rs.getString("name");

                primary_checkup p = new primary_checkup(name, blood_pressure, age, weight);
                pc.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void handleSearch1(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(pid.getText().trim());  // ✅ Correct way
            String name = pname.getText().trim();

            String query = "SELECT p.name, doctor_id, weight, age, blood_pressure " +
                    "FROM primary_checkup pc " +
                    "JOIN patient p ON pc.patient_id = p.patient_id " +
                    "WHERE doctor_id = ? AND p.name = ? AND p.patient_id = ?";

            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(query)) {

                pc.clear();

                stmt.setInt(1, doctorId);
                stmt.setString(2, name);
                stmt.setInt(3, id);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int age = rs.getInt("age");
                    int weight = rs.getInt("weight");
                    String blood_pressure = rs.getString("blood_pressure");
                    String name2 = rs.getString("name");

                    primary_checkup p = new primary_checkup(name2, blood_pressure, age, weight);
                    pc.add(p);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Patient ID: " + pid.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   }
