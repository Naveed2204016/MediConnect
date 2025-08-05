package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class DashboardPatientController {

    @FXML private StackPane contentArea;

    public void initialize() throws IOException {
        loadSearchDoctors(); // Load default page
    }

    @FXML private void loadSearchDoctors() throws IOException {
        loadPage("/fxml/patient_search_doctors.fxml");
    }

    @FXML private void loadUpdateInfo() throws IOException {
        loadPage("/fxml/patient_update_info.fxml");
    }

    @FXML private void loadCancelAppointment() throws IOException {
        loadPage("/fxml/patient_cancel_appointment.fxml");
    }

    @FXML private void loadBookTest() throws IOException {
        loadPage("/fxml/patient_book_test.fxml");
    }

    @FXML private void loadNotifications() throws IOException {
        loadPage("/fxml/patient_notifications.fxml");
    }

    private void loadPage(String path) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource(path));
        contentArea.getChildren().setAll(pane);
    }
}
