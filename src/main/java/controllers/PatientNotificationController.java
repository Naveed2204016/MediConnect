package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.NotificationItem;
import models.RequestItem;

public class PatientNotificationController {

    // First Table
    @FXML
    private TableView<NotificationItem> notificationTable;
    @FXML
    private TableColumn<NotificationItem, String> notificationColumn;

    // Second Table
    @FXML
    private TableView<RequestItem> RequestTable;
    @FXML
    private TableColumn<RequestItem, String> RequestColumn;
    @FXML
    private TableColumn<RequestItem, String> StatusColumn;
    private int userId;

    // Sample data
    private ObservableList<NotificationItem> sampleNotifications =
            FXCollections.observableArrayList(
                    new NotificationItem("Your emergency appointment request with Dr. X has been approved. " +
                            "Tentative date for the appointment is 20/08/2025.")
            );

    private ObservableList<RequestItem> sampleRequests =
            FXCollections.observableArrayList(
                    new RequestItem("Requested for emergency appointment to Dr. X on 10/08/2025", "Pending")
            );

    @FXML
    public void initialize() {
        // Bind first table
        notificationColumn.setCellValueFactory(cellData -> cellData.getValue().messageProperty());

        notificationTable.setItems(sampleNotifications);

        // Bind second table
        RequestColumn.setCellValueFactory(cellData -> cellData.getValue().requestProperty());
        StatusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        RequestTable.setItems(sampleRequests);
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    @FXML
    private void handleCancel() {
        System.out.println("Cancel clicked");
        // later: DB update logic
    }
}
