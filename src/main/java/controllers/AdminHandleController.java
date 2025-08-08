package controllers;

//import com.sun.jdi.connect.spi.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Doctor;
import models.Test;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class AdminHandleController {


    @FXML private TableView<Doctor> tableView;
    @FXML private TableColumn<Doctor, String> colName;
    @FXML private TableColumn<Doctor, String> colSpecialization;
    @FXML private TableColumn<Doctor, String> colqualification;
    @FXML private TableColumn<Doctor, String> colhospital;
    @FXML private TableColumn<Doctor, String> colcity;
    @FXML private TableColumn<Doctor, Double> colfee;
    @FXML private TableColumn<Doctor, String> colcontactnumber;
    @FXML private TableColumn<Doctor, String> colemailad;
    @FXML private TextField doctornamefield;
    @FXML private TextField specializationfield;
    @FXML private TextField hospitalfield;
    @FXML private TextField cityfield;

    private ObservableList<Doctor> sampleDoctors = FXCollections.observableArrayList(
            new Doctor("Dr. Smith", "Cardiology", "mbbs","United", "chittagong",1500.0, "01617793505","john@example.com"),
            new Doctor("Dr. Johnson", "Neurology", "mbbs", "cmoshmc","dhaka",2000.0, "01845022838","mahi@bh.com")
    );

    @FXML
    public void initialize() {
        // Set up table columns
        colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colSpecialization.setCellValueFactory(cellData -> cellData.getValue().specializationProperty());
        colqualification.setCellValueFactory(cellData -> cellData.getValue().qualificationproperty());
        colhospital.setCellValueFactory(cellData -> cellData.getValue().cityproperty());
        colcity.setCellValueFactory(cellData -> cellData.getValue().availabilityProperty());
        colfee.setCellValueFactory(cellData -> cellData.getValue().feeProperty().asObject());
        colcontactnumber.setCellValueFactory(cellData -> cellData.getValue().contactnoproperty());
        colemailad.setCellValueFactory(cellData -> cellData.getValue().emailadproperty());
        // Load sample data (remove when using real database)
        tableView.setItems(sampleDoctors);
    }
    @FXML
    private void handlesearch()
    {

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






}
