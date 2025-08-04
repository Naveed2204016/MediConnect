package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.lang.model.type.NullType;
import java.io.IOException;
import java.sql.*;

public class LoginController {

    @FXML private TextField userIdField;
    @FXML private PasswordField passwordField;
    private static final String url="jdbc:mysql://127.0.0.1:3306/mediconnect";
    private static final String username="root";
    private static final String password="backend#8";

    private String role;

    public void setRole(String role) {
        this.role = role;
        System.out.println("Role set to: " + role);
    }

    @FXML
    private void handleLogin() {
        String userId = userIdField.getText().trim();
        String password1 = passwordField.getText().trim();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try
        {
            Connection connection= DriverManager.getConnection(url,username,password);
            String query="";
            if(role.equals("patient"))
            {
                query="SELECT password FROM patient WHERE patient_id=?";
            }
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(userId));
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()) {
                String str = resultSet.getString("password");
                System.out.println(str);
                if (str.equals(password1)) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/patient_dashboard.fxml"));
                        Scene scene = new Scene(loader.load());
                        Stage stage=(Stage) userIdField.getScene().getWindow();
                        stage.setScene(scene);
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                } else {
                    showAlert("Incorrect Password!");
                }
            }
            else
            {
                showAlert("User id not found!");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        // Placeholder logic for now
        System.out.println("Attempted login for role: " + role);
        System.out.println("User ID: " + userId);
        System.out.println("Password: " + password);

        // Later you'll query the database based on the role
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING); // Use WARNING type for yellow/red icon
        alert.setTitle("Login Error");
        alert.setHeaderText("Authentication Failed");
        alert.setContentText(message);

        // Optional: Set a red icon manually (use your own image)
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/warning.png")));

        // Optional: Style alert text (can also style with CSS file)
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-font-size: 14px; -fx-font-family: 'Segoe UI';");

        alert.showAndWait();
    }



    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) userIdField.getScene().getWindow();  // userIdField is any field from the login scene
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
