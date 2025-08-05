package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardPatientController {

    @FXML private StackPane contentArea; // This must match the fx:id in FXML

    @FXML
    public void initialize() {
        try {
            loadSearchDoctors(); // Load default page
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error appropriately
        }
    }

    @FXML
    private void loadSearchDoctors() throws IOException {
        loadPage("/fxml/patient_search_doctors.fxml");
    }

    @FXML
    private void loadUpdateInfo() throws IOException {
        loadPage("/fxml/patient_update_info.fxml");
    }

    @FXML
    private void loadCancelAppointment() throws IOException {
        loadPage("/fxml/patient_cancel_appointment.fxml");
    }

    @FXML
    private void loadBookTest() throws IOException {
        loadPage("/fxml/patient_book_test.fxml");
    }

    @FXML
    private void loadNotifications() throws IOException {
        loadPage("/fxml/patient_notifications.fxml");
    }

    private void loadPage(String path) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource(path));
        contentArea.getChildren().setAll(pane);
    }

    @FXML
    private void logout()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login_patient.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage=(Stage) contentArea.getScene().getWindow();
            stage.setScene(scene);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}