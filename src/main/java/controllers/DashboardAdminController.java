package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardAdminController {

    @FXML private StackPane contentArea; // This must match the fx:id in FXML

    @FXML
    public void initialize() {
        try {
            loadtests(); // Load default page
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error appropriately
        }
    }

    @FXML

    private void loadtests() throws IOException
    {
        loadPage("/fxml/admin_load_tests.fxml");
    }
    @FXML
    private void loadUpdateInfo() throws IOException {
        loadPage("/fxml/admin_update_info.fxml");
    }

    @FXML
    private void loadhandledoctors() throws IOException{
        loadPage("/fxml/admin_handle_doctors.fxml");
    }

    private void loadPage(String path) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource(path));
        contentArea.getChildren().setAll(pane);
    }

    @FXML
    private void logout()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login_admin.fxml"));
            Scene scene = new Scene(loader.load());
            LoginController loginController=loader.getController();
            loginController.setRole("admin");
            Stage stage=(Stage) contentArea.getScene().getWindow();
            stage.setScene(scene);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}