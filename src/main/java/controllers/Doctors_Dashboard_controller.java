package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Doctors_Dashboard_controller {

    @FXML private StackPane contentArea; // This must match the fx:id in FXML

    @FXML
    public void initialize() {
        try {
            dr_patient_details(); // Load default page
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error appropriately
        }
    }

    @FXML

    private void dr_patient_details() throws IOException
    {
        loadPage("/fxml/Doctors_patient_details.fxml");
    }
    @FXML
    private void loadUpdateInfo() throws IOException {
        loadPage("/fxml/Doctors_update_info.fxml");
    }

    @FXML
    private void emergency_request() throws IOException{
        loadPage("/fxml/Emergency_Request_Doctor.fxml");
    }
    @FXML
    private void cancel_appointment() throws IOException{
        loadPage("/fxml/Cancel_appointment.fxml");
    }

    private void loadPage(String path) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource(path));
        contentArea.getChildren().setAll(pane);
    }

    @FXML
    private void logout()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login_doctor.fxml"));
            Scene scene = new Scene(loader.load());
            LoginController loginController=loader.getController();
            loginController.setRole("doctor");
            Stage stage=(Stage) contentArea.getScene().getWindow();
            stage.setScene(scene);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}