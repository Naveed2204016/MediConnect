package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardAdminController {

    @FXML private StackPane contentArea;
    private int userID;// This must match the fx:id in FXML

    public void setUserID(int userId) {
        this.userID = userId;
        try {
            loadtests(); // Load default page
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error appropriately
        }
    }
    /*@FXML
     public void initialize() {
        try {
            loadtests(); // Load default page
        } catch (IOException e) {
            e.printStackTrace();
            // Handle error appropriately
        }
    }*/

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
        FXMLLoader loader= new FXMLLoader(getClass().getResource(path));
        Pane pane= loader.load();

        if (path.equals("/fxml/admin_load_tests.fxml")) {
            AdminLoadTestController controller = loader.getController();
            controller.setUserId(userID);
        } else if (path.equals("/fxml/admin_update_info.fxml")) {
            AdminUpdateInfoController controller = loader.getController();
            controller.setUserId(userID);
        } else if (path.equals("/fxml/admin_handle_doctors.fxml")) {
            AdminHandleController controller = loader.getController();
            controller.setUserId(userID);
        }
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