package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Doctors_Dashboard_controller {

    @FXML private StackPane contentArea; // This must match the fx:id in FXML
    private int userID;

    public void setUserID(int userID)
    {
        this.userID = userID;
        try {
            dr_patient_details();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void dr_search_patients() throws IOException {
        try {
            loadPage("/fxml/Doctors_patient_details.fxml");
        }
        catch(IOException e)
        {
            e.printStackTrace();
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

    @FXML
    private void loadcheckup() throws IOException{
        try {
            loadPage("/fxml/doctorprimarycheckup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }}


    private void loadPage(String path) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource(path));
        Pane pane = loader.load();
        if (path.equals("/fxml/Doctors_update_info.fxml")) {
            Doctors_update_infoController controller = loader.getController();
            controller.setUserId(userID);
        }
    else if (path.equals("/fxml/Doctors_patient_details.fxml")) {
           doctor_patient_detailscontroller controller = loader.getController();
            controller.setUserId(userID);}
      else if (path.equals("/fxml/Cancel_appointment.fxml")) {
        Cancel_DR_CONTROLLER controller = loader.getController();
            controller.setUserId(userID);}
//         else if (path.equals("/fxml/Emergency_Request_Doctor.fxml")) {
//           emergency_req_dr_controller controller = loader.getController();
//            controller.setUserId(userID);
//        } else if (path.equals("/fxml/doctorprimarycheckup.fxml")) {
//           primary_checkup_dr_controller controller = loader.getController();
//            controller.setUserId(userID);
//        }
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