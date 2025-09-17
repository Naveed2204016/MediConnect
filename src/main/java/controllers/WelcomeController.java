package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.Objects;
public class WelcomeController {

    public ImageView logoImageView;
    public VBox centerVBox;
    @FXML private RadioButton patientRadio;
    @FXML private RadioButton doctorRadio;
    @FXML private RadioButton assistantRadio;
    @FXML private RadioButton adminRadio;
    @FXML private Button loginButton;
    @FXML private ToggleGroup roleToggleGroup;
    @FXML private Button signupButton;
    public void initialize() {
        // Load logo image from resources/images folder
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/1583625.png")).toExternalForm());
        logoImageView.setImage(image);
    }
    @FXML
    private void onLoginButtonClick() throws IOException {
        if (patientRadio.isSelected()) {
            loadLoginScene("patient", "/fxml/login_patient.fxml");
        } else if (doctorRadio.isSelected()) {
            loadLoginScene("doctor", "/fxml/login_doctor.fxml");
        } else if (assistantRadio.isSelected()) {
            loadLoginScene("assistant", "/fxml/login_assistant.fxml");
        } else if (adminRadio.isSelected()) {
            loadLoginScene("admin", "/fxml/login_admin.fxml");
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

    private void loadLoginScene(String role, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        LoginController loginController = loader.getController();
        loginController.setRole(role);

        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(scene);
    }

    private void loadScene(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(scene);
    }
}
