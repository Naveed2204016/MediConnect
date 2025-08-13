package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeController {

    @FXML private RadioButton patientRadio;
    @FXML private RadioButton doctorRadio;
    @FXML private RadioButton assistantRadio;
    @FXML private RadioButton adminRadio;
    @FXML private Button loginButton;
    @FXML private ToggleGroup roleToggleGroup;
    @FXML private Button signupButton;

    // Reference to the VBox in <center> where we will insert the image
    @FXML private VBox centerVBox;

   /* @FXML
    public void initialize() {
        // Load image
        Image bannerImage = new Image(getClass().getResourceAsStream("/images/PersianArt.jpg")); // change path as needed
        ImageView bannerImageView = new ImageView(bannerImage);

        // Make image stretch horizontally but keep aspect ratio
        bannerImageView.setPreserveRatio(true);
        bannerImageView.setFitWidth(750); // almost the width of your center area

        // Insert image at the top of VBox
        centerVBox.getChildren().add(0, bannerImageView);
    }*/



    @FXML
    private void onLoginButtonClick() throws IOException {
        if (patientRadio.isSelected()) {
            loadloginsScene("patient","/fxml/login_patient.fxml");
        } else if (doctorRadio.isSelected()) {
            loadloginsScene("doctor","/fxml/login_doctor.fxml");
        } else if (assistantRadio.isSelected()) {
            loadloginsScene("assistant","/fxml/login_assistant.fxml");
        } else if (adminRadio.isSelected()) {
            loadloginsScene("admin","/fxml/login_admin.fxml");
        }
    }

    @FXML
    private void onSignupButtonClick() throws IOException {
        if (patientRadio.isSelected()) {
            loadScene("/fxml/signup_patient.fxml");
        } else if (doctorRadio.isSelected()) {
            loadScene("/fxml/signup_doctor.fxml");
        } else if (assistantRadio.isSelected()) {
            loadScene("/fxml/signup_assistant.fxml");
        } else if (adminRadio.isSelected()) {
            loadScene("/fxml/signup_admin.fxml");
        }
    }

    private void loadloginsScene(String role,String fxmlPath) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene=new Scene(loader.load());
        LoginController loginController=loader.getController();
        loginController.setRole(role);
        Stage stage=(Stage) loginButton.getScene().getWindow();
        stage.setScene(scene);
    }

    private void loadScene(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(scene);
    }
}
