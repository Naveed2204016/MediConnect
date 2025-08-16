package controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.Doctor;

import java.time.LocalTime;

public class PatientSearchDoctorsController {

    @FXML private TextField specializationField;
    @FXML private TextField locationField;
    @FXML private TableView<Doctor> doctorsTable;
    @FXML private TableColumn<Doctor, String> nameCol;
    @FXML private TableColumn<Doctor, String> specializationCol;
    @FXML private TableColumn<Doctor, String> qualificationCol;
    @FXML private TableColumn<Doctor, String> hospitalCol;
    @FXML private TableColumn<Doctor, String> cityCol;
    @FXML private TableColumn<Doctor, LocalTime> stCol;
    @FXML private TableColumn<Doctor, LocalTime> edCol;
    @FXML private TableColumn<Doctor, String> contactCol;
    @FXML private TableColumn<Doctor, String> emailCol;
    @FXML private TableColumn<Doctor, String> locationCol;
    @FXML private TableColumn<Doctor, Double> feeCol;
    @FXML private TableColumn<Doctor, String> availabilityCol;
    @FXML
    private VBox appointmentBox;
    @FXML
    private VBox emergencyBox;
    @FXML
    private Button normalAppointmentBtn;
    @FXML
    private Button emergencyRequestBtn;
    @FXML
    private TextField symptomsField;
    @FXML
    private Button submitEmergencyBtn;

    @FXML private Button closeAppointmentBtn;
    @FXML private Button closeEmergencyBtn;

    // In PatientSearchDoctorsController.java

    @FXML private Pane popupLayer;
    private int userId;

    private double dragOffsetX, dragOffsetY;

    private void makeDraggable(Node node) {
        node.setOnMousePressed(event -> {
            dragOffsetX = event.getSceneX() - node.getLayoutX();
            dragOffsetY = event.getSceneY() - node.getLayoutY();
        });
        node.setOnMouseDragged(event -> {
            node.setLayoutX(event.getSceneX() - dragOffsetX);
            node.setLayoutY(event.getSceneY() - dragOffsetY);
        });
    }

    private void centerPopup(VBox popup) {
        popupLayer.layoutBoundsProperty().addListener((obs, oldVal, newVal) -> {
            popup.setLayoutX((newVal.getWidth() - popup.getPrefWidth()) / 2);
            popup.setLayoutY((newVal.getHeight() - popup.getHeight()) / 2);
        });
    }


    // Sample data - remove this when you connect to real database
    private ObservableList<Doctor> sampleDoctors = FXCollections.observableArrayList(
            new Doctor("Dr. Smith", "Cardiology", "mbbs","United", 1500.0, "Mon-Fri 9AM-5PM"),
            new Doctor("Dr. Johnson", "Neurology", "mbbs", "cmoshmc",2000.0, "Tue-Sat 10AM-6PM")
    );


    private ObservableList<Doctor> sampleDoctors1= FXCollections.observableArrayList(
            new Doctor("Dr. Smith", "Cardiology", "mbbs","United", "chittagong", LocalTime.NOON,LocalTime.MIDNIGHT,1500.0, "01617793505","john@example.com"),
            new Doctor("Dr. Johnson", "Neurology", "mbbs", "cmoshmc","dhaka",LocalTime.now(),LocalTime.NOON,2000.0, "01845022838","mahi@bh.com")
    );

    public void setUserId(int userId) {
        this.userId = userId;
    }
    @FXML
    public void initialize() {
        // Set up table columns
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        specializationCol.setCellValueFactory(cellData -> cellData.getValue().specializationProperty());
        qualificationCol.setCellValueFactory(cellData -> cellData.getValue().qualificationproperty());
        hospitalCol.setCellValueFactory(cellData -> cellData.getValue().hospitalproperty());
        cityCol.setCellValueFactory(cellData -> cellData.getValue().cityproperty());
        stCol.setCellValueFactory(cellData -> cellData.getValue().stproperty());
        edCol.setCellValueFactory(cellData -> cellData.getValue().edproperty());
        contactCol.setCellValueFactory(cellData -> cellData.getValue().contactnoproperty());
        emailCol.setCellValueFactory(cellData -> cellData.getValue().emailadproperty());
        feeCol.setCellValueFactory(cellData -> cellData.getValue().feeProperty().asObject());


        // Load sample data (remove when using real database)
        doctorsTable.setItems(sampleDoctors1);
        makeDraggable(appointmentBox);
        makeDraggable(emergencyBox);
        centerPopup(appointmentBox);
        centerPopup(emergencyBox);
        doctorsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                appointmentBox.setVisible(true);
                appointmentBox.setManaged(true);
                emergencyBox.setVisible(false);
                emergencyBox.setManaged(false);
            } else {
                appointmentBox.setVisible(false);
                appointmentBox.setManaged(false);
                emergencyBox.setVisible(false);
                emergencyBox.setManaged(false);
            }
        });

        emergencyRequestBtn.setOnAction(e -> {
            emergencyBox.setVisible(true);
            emergencyBox.setManaged(true);
        });

        submitEmergencyBtn.setOnAction(e -> {
            // Handle emergency submission logic here
            emergencyBox.setVisible(false);
            emergencyBox.setManaged(false);
            appointmentBox.setVisible(false);
            appointmentBox.setManaged(false);
            symptomsField.clear();
        });

        normalAppointmentBtn.setOnAction(e -> {
            // Handle normal appointment logic here
            appointmentBox.setVisible(false);
            appointmentBox.setManaged(false);
        });

        closeAppointmentBtn.setOnAction(e -> {
            appointmentBox.setVisible(false);
            appointmentBox.setManaged(false);
            doctorsTable.getSelectionModel().clearSelection();
        });
        closeEmergencyBtn.setOnAction(e -> {
            emergencyBox.setVisible(false);
            emergencyBox.setManaged(false);
        });


    }

    @FXML
    private void handleSearch() {
        // Placeholder for search functionality
        System.out.println("Search clicked - Specialization: " + specializationField.getText()
                + ", Location: " + locationField.getText());
    }

    @FXML
    private void handleBook() {
        Doctor selectedDoctor = doctorsTable.getSelectionModel().getSelectedItem();
        if (selectedDoctor != null) {
            System.out.println("Booking appointment with: " + selectedDoctor.getName());
        } else {
            showAlert("No Selection", "Please select a doctor first.");
        }
    }

    @FXML
    private void handleEmergency() {
        System.out.println("Emergency request initiated");
        showAlert("Emergency Request", "Emergency request has been sent to the nearest available doctor.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}