package controllers;

//import com.sun.jdi.connect.spi.Connection;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Doctor;
import models.Test;
import java.io.IOException;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.doctor1;

public class AdminHandleController {


    @FXML private TableView<doctor1> doctorsTable;
    @FXML private TableColumn<doctor1, String> nameCol;
    @FXML private TableColumn<doctor1, String> specializationCol;
    @FXML private TableColumn<doctor1, String> qualificationCol;
    @FXML private TableColumn<doctor1, String> hospitalCol;
    @FXML private TableColumn<doctor1, String> cityCol;
    @FXML private TableColumn<doctor1, String> stCol;
    @FXML private TableColumn<doctor1, String> edCol;
    @FXML private TableColumn<doctor1, String> contactCol;
    @FXML private TableColumn<doctor1, String> emailCol;
    @FXML private TableColumn<doctor1, Double> feeCol;

    private int userID;
    public void setUserId(int userID)
    {
        this.userID = userID;
        loadDoctorsData();
    }


    ObservableList<doctor1> doctors= FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        nameCol.setCellValueFactory(Data -> new javafx.beans.property.SimpleStringProperty(Data.getValue().getName()));
        specializationCol.setCellValueFactory(Data->new javafx.beans.property.SimpleStringProperty(Data.getValue().getSpecialization()));
        qualificationCol.setCellValueFactory(Data->new javafx.beans.property.SimpleStringProperty(Data.getValue().getQualification()));
        hospitalCol.setCellValueFactory(Data->new javafx.beans.property.SimpleStringProperty(Data.getValue().getHospital()));
        cityCol.setCellValueFactory(Data->new javafx.beans.property.SimpleStringProperty(Data.getValue().getCity()));
        stCol.setCellValueFactory(data -> {
            java.sql.Time time = data.getValue().getStartTime(); // assuming returns TIME
            String formattedTime = (time != null)
                    ? time.toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"))
                    : "";
            return new javafx.beans.property.SimpleStringProperty(formattedTime);
        });
        edCol.setCellValueFactory(data-> {
            java.sql.Time time = data.getValue().getEndTime(); // assuming returns TIME
            String formattedTime = (time != null)
                    ? time.toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"))
                    : "";
            return new javafx.beans.property.SimpleStringProperty(formattedTime);
        });
        contactCol.setCellValueFactory(Data->new javafx.beans.property.SimpleStringProperty(Data.getValue().getContact_number()));
        emailCol.setCellValueFactory(Data->new javafx.beans.property.SimpleStringProperty(Data.getValue().getEmail()));
        feeCol.setCellValueFactory(Data->new javafx.beans.property.SimpleDoubleProperty(Data.getValue().getFees()).asObject());


        TableColumn<doctor1, Void> actionCol = new TableColumn<>("Action");
        actionCol.setPrefWidth(120);
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button bookButton = new Button("Remove");

            {
                bookButton.setOnAction(event -> {
                    doctor1 doctor = getTableView().getItems().get(getIndex());
                    removedoctor(doctor);
                });
                bookButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(bookButton);
                }
            }
        });
        doctorsTable.getColumns().add(actionCol);
    }


    public void loadDoctorsData(){
        doctors.clear();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DBConnection.getConnection();
            String Query="SELECT * FROM (Doctor as d,Location as l) WHERE d.doctor_id=l.d_id";
            PreparedStatement pmt= connection.prepareStatement(Query);
            ResultSet rs = pmt.executeQuery();
            while(rs.next()) {
                int doctorId = rs.getInt("doctor_id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                String qualification = rs.getString("qualification");
                String hospital = rs.getString("hospital");
                String city = rs.getString("city");
                Time startTime = rs.getTime("start_time");
                Time endTime = rs.getTime("End_time");
                double fees = rs.getDouble("fees");
                String contact_number = rs.getString("contact_number");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int capaity_per_day = rs.getInt("capacity_per_day");
                int emergency_slots_per_day = rs.getInt("emergency_slots_per_day");
                doctor1 doc = new doctor1(doctorId, name, specialization, qualification, hospital, city, startTime, endTime, fees, contact_number, email, capaity_per_day, emergency_slots_per_day, password);
                doctors.add(doc);
            }

            doctorsTable.setItems(doctors);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handlesearch()
    {

    }


    private void removedoctor(doctor1 doctor)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete?");
        alert.setContentText("Delete \"" + doctor.getName() + "\" from database?");

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, cancelButton);

        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    Connection connection = DBConnection.getConnection();
                    String query = "DELETE FROM doctor WHERE doctor_id = ?";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, doctor.getDoctorId());
                    int affected = ps.executeUpdate();

                    if (affected > 0) {
                        doctors.remove(doctor);
                        System.out.println("Doctor removed successfully.");
                    } else {
                        showalert("Could not delete doctor from database.");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    showalert("Error: " + e.getMessage());
                }
            }
        });
    }
    @FXML
    private void onRemoveSelected()
    {

    }

    @FXML
    private void handlesearchassistant()
    {

    }

    @FXML
    private void onRemoveSelectedassistant()
    {

    }

    public void showalert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
