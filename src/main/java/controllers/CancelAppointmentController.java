package controllers;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Appointment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.*;

public class CancelAppointmentController {

    @FXML private TextField DoctorName;
    @FXML private TextField DoctorContactNumber;
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, String> dname;
    @FXML private TableColumn<Appointment, String> contactNumber;
    @FXML private TableColumn<Appointment, String> appdate;
    private int userId;
    //private static final String url="jdbc:mysql://127.0.0.1:3306/mediconnect";
   // private static final String username="root";
   // private static final String password="backend#8";

    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        dname.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getDoctorName()));

        contactNumber.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getContactNumber()));

        appdate.setCellValueFactory(data -> {
            LocalDate date = data.getValue().getAppointmentDate().toLocalDate();
            String formattedDate = (date != null) ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
            return new javafx.beans.property.SimpleStringProperty(formattedDate);
        });
        appointmentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }

    public void setUserId(int userId) {
        this.userId = userId;
        loadAppointments();
    }

    private void loadAppointments() {
        appointments.clear();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection= DBConnection.getConnection(); //DriverManager.getConnection(url,username,password);
            String query="SELECT a.appointment_id, d.name AS doctor_name, d.contact_number, a.appointment_date " +
                         "FROM Appointment a JOIN Doctor d ON a.d_id = d.doctor_id " +
                         "WHERE a.p_id = ? AND a.Status=? "+
                         "ORDER BY a.appointment_date";
            PreparedStatement pmt=connection.prepareStatement(query);
            pmt.setInt(1,userId);
            pmt.setString(2,"confirmed");
            ResultSet resultSet=pmt.executeQuery();
            while(resultSet.next())
            {
                int appointmentId = resultSet.getInt("appointment_id");
                String doctorName = resultSet.getString("doctor_name");
                String contactNumber = resultSet.getString("contact_number");
                Date appointmentDate = resultSet.getDate("appointment_date");


                Appointment appointment = new Appointment(appointmentId, doctorName, contactNumber, appointmentDate);
                appointments.add(appointment);
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
        appointmentTable.setItems(appointments);
    }

    @FXML
    private void handleSearch() {
        String doctorName = DoctorName.getText();
        String doctorContact = DoctorContactNumber.getText();
        appointments.clear();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DBConnection.getConnection(); //DriverManager.getConnection(url,username,password);
            String query="SELECT a.appointment_id, d.name AS doctor_name, d.contact_number, a.appointment_date " +
                    "FROM Appointment a JOIN Doctor d ON a.d_id = d.doctor_id " +
                    "WHERE a.p_id = ? AND a.Status=? AND d.name=? AND d.contact_number=?"+
                    "ORDER BY a.appointment_date desc";
            PreparedStatement pmt=connection.prepareStatement(query);
            pmt.setInt(1,userId);
            pmt.setString(2,"confirmed");
            pmt.setString(3, doctorName);
            pmt.setString(4, doctorContact);
            ResultSet resultSet=pmt.executeQuery();
            while(resultSet.next())
            {
                int appointmentId = resultSet.getInt("appointment_id");
                String doctorName1 = resultSet.getString("doctor_name");
                String contactNumber = resultSet.getString("contact_number");
                Date appointmentDate = resultSet.getDate("appointment_date");


                Appointment appointment = new Appointment(appointmentId, doctorName1, contactNumber, appointmentDate);
                appointments.add(appointment);
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
        appointmentTable.setItems(appointments);

    }

    @FXML
    private void handleCancelAppointment() {
        ObservableList<Appointment> selectedItems = appointmentTable.getSelectionModel().getSelectedItems();

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

    private int getLoggedInPatientId() {
        // TODO: Replace with actual logic to get logged-in patient ID
        return 1;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

