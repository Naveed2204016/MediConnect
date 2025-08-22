package controllers;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Appointment;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Cancel_DR_CONTROLLER {
    public TextField DoctorName;
    public TableColumn<Appointment,String> pname;
    public TableColumn<Appointment,String> hospital;
    public TableColumn<Appointment,String> adate;
    public TableColumn<Appointment,String> contact;
    public TextField patName;
    public TableView<Appointment> appointmentTable1;

    private int userId;

    public void setUserId(int userID) {
        this.userId = userID;
        loadAppointments();
    }
    private ObservableList<Appointment> appointments1 = FXCollections.observableArrayList();
    @FXML
    public void initialize() {
        pname.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getPatientName()));

        contact.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getContactNumber()));

        adate.setCellValueFactory(data -> {
            LocalDate date = data.getValue().getAppointmentDate().toLocalDate();
            String formattedDate = (date != null) ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
            return new javafx.beans.property.SimpleStringProperty(formattedDate);
        });
        appointmentTable1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }

    private void loadAppointments() {
        appointments1.clear();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection= DBConnection.getConnection(); //DriverManager.getConnection(url,username,password);
            String query= "SELECT a.appointment_id, p.name AS patient_name, p.contact_number, a.appointment_date " +
                "FROM Appointment a " +
                "JOIN patient p ON a.p_id = p.patient_id " +
                "JOIN doctor d ON a.d_id = d.doctor_id " +
                "WHERE a.d_id = ? AND a.Status = ? " +
                "ORDER BY a.appointment_date";


            PreparedStatement pmt=connection.prepareStatement(query);
            pmt.setInt(1,userId);
            pmt.setString(2,"confirmed");
            ResultSet resultSet=pmt.executeQuery();
            while(resultSet.next())
            {
                int appointmentId = resultSet.getInt("appointment_id");
                String patientName = resultSet.getString("patient_name");
                String contactNumber = resultSet.getString("contact_number");
                Date appointmentDate = resultSet.getDate("appointment_date");

                Appointment appointment = new Appointment(patientName,appointmentId, contactNumber, appointmentDate);
                appointments1.add(appointment);
            }
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        appointmentTable1.setItems(appointments1);
    }
    public void handlesearch1(ActionEvent actionEvent) {
        String patientname = patName.getText();

        appointments1.clear();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DBConnection.getConnection(); //DriverManager.getConnection(url,username,password);
            String query = "SELECT a.appointment_id, p.name AS patient_name, p.contact_number, a.appointment_date " +
                    "FROM Appointment a JOIN patient p ON a.p_id = p.patient_id " +
                    "WHERE a.d_id = ? AND a.Status=? AND p.name=? " +
                    "ORDER BY a.appointment_date";

            PreparedStatement pmt=connection.prepareStatement(query);
            pmt.setInt(1,userId);
            pmt.setString(2,"confirmed");
            pmt.setString(3, patientname);
            ResultSet resultSet=pmt.executeQuery();
            while(resultSet.next())
            {
                int appointmentId = resultSet.getInt("appointment_id");
                String patientName1 = resultSet.getString("patient_name");
                String contactNumber = resultSet.getString("contact_number");
                Date appointmentDate = resultSet.getDate("appointment_date");


                Appointment appointment = new Appointment(patientName1,appointmentId, contactNumber, appointmentDate);
                appointments1.add(appointment);
            }
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        appointmentTable1.setItems(appointments1);
    }

    public void cnclbtn(ActionEvent actionEvent) {
        ObservableList<Appointment> selectedItems = appointmentTable1.getSelectionModel().getSelectedItems();

        if (selectedItems.isEmpty()) {
            showAlert("No Selection", "Please select one or more appointments to cancel.");
            return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DBConnection.getConnection(); //DriverManager.getConnection(url, username, password);
            String query = "UPDATE Appointment SET Status=? WHERE appointment_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (Appointment appointment : selectedItems) {
                preparedStatement.setString(1, "cancelled");
                preparedStatement.setInt(2, appointment.getAppointmentId());
                preparedStatement.executeUpdate();
            }
            connection.close();
            showAlert("Success", "Selected appointments have been cancelled successfully.");
            loadAppointments(); // Refresh the appointment list
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
