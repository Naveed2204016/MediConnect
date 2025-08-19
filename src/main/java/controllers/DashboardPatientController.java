package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardPatientController {

    @FXML private StackPane contentArea;
    private int userID;

    @FXML
    /*public void initialize() {
        try {
            loadSearchDoctors();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void setUserID(int userID)
    {
        this.userID = userID;
        try {
            loadSearchDoctors();
        } catch (IOException e) {
            e.printStackTrace();
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
        FXMLLoader loader=new FXMLLoader(getClass().getResource(path));
        Pane pane = loader.load();
        if (path.equals("/fxml/patient_search_doctors.fxml")) {
            PatientSearchDoctorsController controller = loader.getController();
            controller.setUserId(userID);
        } else if (path.equals("/fxml/patient_update_info.fxml")) {
            PatientUpdateInfoController controller = loader.getController();
            System.out.println("ki hoise re bhai");
            controller.setUserId(userID);
        } else if (path.equals("/fxml/patient_cancel_appointment.fxml")) {
            CancelAppointmentController controller = loader.getController();
            controller.setUserId(userID);
        } else if (path.equals("/fxml/patient_book_test.fxml")) {
            BookDiagnosticTestController controller = loader.getController();
            controller.setUserId(userID);
        } else if (path.equals("/fxml/patient_notifications.fxml")) {
            PatientNotificationController controller = loader.getController();
            controller.setUserId(userID);
        }
        contentArea.getChildren().setAll(pane);
    }

    @FXML
    private void logout()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login_patient.fxml"));
            Scene scene = new Scene(loader.load());
            LoginController loginController=loader.getController();
            loginController.setRole("patient");
            Stage stage=(Stage) contentArea.getScene().getWindow();
            stage.setScene(scene);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}