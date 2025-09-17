package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import models.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PatientNotificationController {

    int patientId;
    public void setUserId(int patientId) {
        this.patientId=patientId;
        loadproposedRequests();
        initialize2();
        loadhistory();
    }

    @FXML
    private TableView<emergency_request> proposedTable;
    @FXML
    private TableColumn<emergency_request, String> doctorNameCol;
    @FXML
    private TableColumn<emergency_request, String> tentativeDateCol;

    public ObservableList<emergency_request> proposedRequests = FXCollections.observableArrayList();
    public ObservableList<emergency_request> history = FXCollections.observableArrayList();

    @FXML
    private TableView<emergency_request> historyTable;

    @FXML
    private TableColumn<emergency_request, String> doctorNameHistoryCol;


    @FXML
    private TableColumn<emergency_request, String> requestDateCol;

    @FXML
    private TableColumn<emergency_request, String> statusCol;



    @FXML
    public void initialize() {

        doctorNameCol.setCellValueFactory(Data -> new javafx.beans.property.SimpleStringProperty(Data.getValue().getDoctor_name()));
        tentativeDateCol.setCellValueFactory(data -> {
            LocalDate date = data.getValue().getTentative_date().toLocalDate();
            String formattedDate = (date != null) ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
            return new javafx.beans.property.SimpleStringProperty(formattedDate);
        });


        proposedTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);



        TableColumn<emergency_request, Void> actionCol = new TableColumn<>("Confirm");
        actionCol.setPrefWidth(120);
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button bookButton = new Button("Confirm");

            {
                bookButton.setOnAction(event -> {
                    emergency_request request= getTableView().getItems().get(getIndex());
                    handleconfirm(request);
                });
                bookButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
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

        //proposedTable.getColumns().add(actionCol);

        TableColumn<emergency_request, Void> actionCol1 = new TableColumn<>("Cancel");
        actionCol1.setPrefWidth(120);
        actionCol1.setCellFactory(param -> new TableCell<>() {
            private final Button bookButton = new Button("Cancel");

            {
                bookButton.setOnAction(event -> {
                    emergency_request request= getTableView().getItems().get(getIndex());
                    handlecancel(request);
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
        proposedTable.getColumns().add(actionCol);
        proposedTable.getColumns().add(actionCol1);
    }

    public void initialize2() {

        doctorNameHistoryCol.setCellValueFactory(Data -> new javafx.beans.property.SimpleStringProperty(Data.getValue().getDoctor_name()));
        requestDateCol.setCellValueFactory(data -> {
            LocalDate date = data.getValue().getRequest_date().toLocalDate();
            String formattedDate = (date != null) ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
            return new javafx.beans.property.SimpleStringProperty(formattedDate);
        });
        statusCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));
    }

    public void loadhistory()
    {
        history.clear();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DBConnection.getConnection();
            String query="SELECT e.request_id,d.name,e.p_id,e.d_id,e.a_id,e.request_date,e.tentative_date,e.symptoms,e.status,e.response_seen " +
                    "FROM emergency_request AS e " +
                    "JOIN doctor AS d ON e.d_id = d.doctor_id " +
                    "WHERE e.p_id = ? and status='pending' or status='rejected' or status='confirmed'";
            PreparedStatement pmt=connection.prepareStatement(query);
            pmt.setInt(1,patientId);
            ResultSet resultSet=pmt.executeQuery();
            while(resultSet.next())
            {
                int request_id = resultSet.getInt("request_id");
                String doctor_name = resultSet.getString("name");
                int p_id = resultSet.getInt("p_id");
                int d_id = resultSet.getInt("d_id");
                int a_id = resultSet.getInt("a_id");
                Date request_date= resultSet.getDate("request_date");
                Date tentative_date = resultSet.getDate("tentative_date");
                String symptoms = resultSet.getString("symptoms");
                String status = resultSet.getString("status");
                String response_seen=resultSet.getString("response_seen");
                emergency_request r = new emergency_request(request_id,doctor_name,p_id,a_id,d_id,symptoms,request_date,tentative_date,status,response_seen);
                history.add(r);
            }
            historyTable.setItems(history);
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void loadproposedRequests()
    {
        proposedRequests.clear();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DBConnection.getConnection();
            String query="SELECT e.request_id,d.name,e.p_id,e.d_id,e.a_id,e.request_date,e.tentative_date,e.symptoms,e.status,e.response_seen " +
                    "FROM emergency_request AS e " +
                    "JOIN doctor AS d ON e.d_id = d.doctor_id " +
                    "WHERE e.p_id = ? AND e.status='proposed' and e.tentative_date > ?";
            PreparedStatement pmt=connection.prepareStatement(query);
            pmt.setInt(1,patientId);
            pmt.setDate(2, Date.valueOf(LocalDate.now()));
            ResultSet resultSet=pmt.executeQuery();
            while(resultSet.next())
            {
                int request_id = resultSet.getInt("request_id");
                String doctor_name = resultSet.getString("name");
                int p_id = resultSet.getInt("p_id");
                int d_id = resultSet.getInt("d_id");
                int a_id = resultSet.getInt("a_id");
                Date request_date= resultSet.getDate("request_date");
                Date tentative_date = resultSet.getDate("tentative_date");
                String symptoms = resultSet.getString("symptoms");
                String status = resultSet.getString("status");
                String response_seen=resultSet.getString("response_seen");
                emergency_request r = new emergency_request(request_id,doctor_name,p_id,a_id,d_id,symptoms,request_date,tentative_date,status,response_seen);
                proposedRequests.add(r);
            }
            proposedTable.setItems(proposedRequests);
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void handleconfirm(emergency_request request)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DBConnection.getConnection();
            String query="UPDATE emergency_request SET status=?,response_seen=? WHERE request_id=?";
            PreparedStatement pmt=connection.prepareStatement(query);
            pmt.setString(1,"confirmed");
            pmt.setString(2,"seen");
            pmt.setInt(3,request.getReqest_id());
            int rowsAffected = pmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Request confirmed successfully.");
                proposedRequests.remove(request);
                proposedTable.refresh();
                historyTable.refresh();
            } else {
                System.out.println("No request found with the given ID.");
            }
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void handlecancel(emergency_request request)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection=DBConnection.getConnection();
            String query="UPDATE emergency_request SET status=?,response_seen=? WHERE request_id=?";
            PreparedStatement pmt=connection.prepareStatement(query);
            pmt.setString(1,"cancelled");
            pmt.setString(2,"seen");
            pmt.setInt(3,request.getReqest_id());
            int rowsAffected = pmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Request cancelled successfully.");
                proposedRequests.remove(request);
                proposedTable.refresh();
            } else {
                System.out.println("No request found with the given ID.");
            }
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }


}