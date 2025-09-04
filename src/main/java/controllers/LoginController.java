package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import db.DBConnection;

import javax.lang.model.type.NullType;
import java.io.IOException;
import java.sql.*;

public class LoginController {

    @FXML private TextField userIdField;
    @FXML private PasswordField passwordField;
    //private static final String url="jdbc:mysql://127.0.0.1:3306/mediconnect";
    //private static final String username="root";
    //private static final String password="backend#8";

    private String role;

    public void setRole(String role) {
        this.role = role;
        System.out.println("Role set to: " + role);
    }

    @FXML
    private void handleLogin() {
        String userId = userIdField.getText().trim();
        String password1 = passwordField.getText().trim();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        if(role.equals("patient"))
        {
            try {
                Connection connection = DBConnection.getConnection();// DriverManager.getConnection(url, username, password);
                String query = "SELECT password FROM Patient WHERE patient_id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, Integer.parseInt(userId));
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String str = resultSet.getString("password");
                    if (str.equals(password1)) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/patient_dashboard.fxml"));
                        Scene scene = new Scene(loader.load());
                        DashboardPatientController dashboardPatientController=loader.getController();
                        dashboardPatientController.setUserID(Integer.parseInt(userId));
                        Stage stage = (Stage) userIdField.getScene().getWindow();
                        stage.setScene(scene);
                    } else {
                        showAlert("Incorrect Password!");
                    }
                } else {
                    showAlert("User id not found!");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(role.equals("admin"))
        {
            try {
                Connection conncection=DBConnection.getConnection();
                String query="SELECT password FROM Admin WHERE admin_id=?";
                PreparedStatement preparedStatement=conncection.prepareStatement(query);
                preparedStatement.setInt(1,Integer.parseInt(userId));
                ResultSet resultSet=preparedStatement.executeQuery();
                if(resultSet.next())
                {
                    String str=resultSet.getString("password");
                    if(str.equals(password1)) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin_dashboard.fxml"));
                        Scene scene = new Scene(loader.load());
                        DashboardAdminController dashboardAdminController=loader.getController();
                        dashboardAdminController.setUserID(Integer.parseInt(userId));
                        Stage stage=(Stage) userIdField.getScene().getWindow();
                        stage.setScene(scene);
                    }
                    else
                    {
                        showAlert("Incorrect Password!");
                        return;
                    }
                }
                else
                {
                    showAlert("User id not found!");
                    return;
                }
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else if(role.equals("doctor"))
        {
                try {
                    Connection connection1 = DBConnection.getConnection();
                    String query = "SELECT password FROM Doctor WHERE doctor_id=?";
                    PreparedStatement preparedStatement1 = connection1.prepareStatement(query);
                    preparedStatement1.setInt(1, Integer.parseInt(userId));
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                    if (resultSet1.next()) {
                        String str = resultSet1.getString("password");
                        if (str.equals(password1)) {
                            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/fxml/Doctors_dashboard.fxml"));
                            Scene scene = new Scene(loader1.load());
                            Doctors_Dashboard_controller dashboardDoctorController=loader1.getController();
                            dashboardDoctorController.setUserID(Integer.parseInt(userId));
                            Stage stage1 = (Stage) userIdField.getScene().getWindow();
                            stage1.setScene(scene);
                        } else {
                            showAlert("Incorrect Password!");
                        }
                    } else {
                        showAlert("User id not found!");
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        else if(role.equals("assistant"))
        {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AssistantDashboard.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage=(Stage) userIdField.getScene().getWindow();
                stage.setScene(scene);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("Attempted login for role: " + role);
        System.out.println("User ID: " + userId);
        System.out.println("Password: " + password1);
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
